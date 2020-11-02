package bivano.apps.articlefeature

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import bivano.apps.articlefeature.di.DaggerArticleComponent
import bivano.apps.common.adapter.ArticleFooterLoadStateAdapter
import bivano.apps.common.adapter.ArticlePagingDataAdapter
import bivano.apps.common.extension.debouncingText
import bivano.apps.common.factory.ViewModelFactory
import bivano.apps.common.model.Article
import bivano.apps.yournews.di.DynamicModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ArticleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val articleViewModel by viewModels<ArticleViewModel> { viewModelFactory }

    private lateinit var articleAdapter: ArticlePagingDataAdapter

    private var lastQuery = ""

    private var lastSort = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencyInjector()
        super.onCreate(savedInstanceState)
    }

    private fun initDependencyInjector() {
        val module = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            DynamicModuleDependencies::class.java
        )
        DaggerArticleComponent.factory().create(module).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_article, container, false)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initSearchView()
        initRecyclerView()
        initChipFilter()
        observeData()
    }

    private fun setupToolbar() {
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                bivano.apps.yournews.R.id.action_home, bivano.apps.yournews.R.id.action_achieved,
                bivano.apps.yournews.R.id.action_article
            )
        )
        toolbar.setupWithNavController(findNavController(), appBarConfiguration)
    }

    private fun initChipFilter() {
        chip_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.chip_popularity -> {
                    lastSort = "popularity"
                    articleViewModel.loadArticle(lastQuery, lastSort)
                    recyclerview.scrollToPosition(0)
                }
                R.id.chip_latest -> {
                    lastSort = "publishedAt"
                    articleViewModel.loadArticle(lastQuery, lastSort)
                    recyclerview.scrollToPosition(0)
                }
                R.id.chip_relevance -> {
                    lastSort = "relevancy"
                    articleViewModel.loadArticle(lastQuery, lastSort)
                    recyclerview.scrollToPosition(0)
                }
            }
        }
    }

    private fun initRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        articleAdapter = ArticlePagingDataAdapter()
        articleAdapter.apply {
            recyclerview.adapter =
                withLoadStateFooter(ArticleFooterLoadStateAdapter(articleAdapter::retry))
            onItemClick = {
                val action = ArticleFragmentDirections.actionActionArticleToDetailFragment(it.url)
                findNavController().navigate(action)
            }

            onItemLongClick = {
                showDialog(it)
            }

            addLoadStateListener {
                when {
                    it.refresh is LoadState.Error -> {
                        container_empty.visibility = View.VISIBLE
                        recyclerview.visibility = View.GONE
                    }
                    it.refresh is LoadState.NotLoading -> {
                        container_empty.visibility = View.GONE
                        recyclerview.visibility = View.VISIBLE
                    }
                    it.append is LoadState.Error -> {
                        showErrorMessage((it.append as LoadState.Error).error.localizedMessage!!)
                    }
                }
            }
        }
    }

    private fun showDialog(article: Article) {
        AlertDialog.Builder(context)
            .setTitle("Confirmation")
            .setMessage("Do you want to save this news?")
            .setPositiveButton("Yes") { dialogInterface, i ->
                articleViewModel.saveNews(article)
                Toast.makeText(context, "Successfully Added To Achieved Menu", Toast.LENGTH_SHORT)
                    .show()
                dialogInterface.dismiss()
            }
            .setNegativeButton("No") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .show()
    }

    private fun observeData() {
        articleViewModel.articlePagingData.observe(viewLifecycleOwner, Observer {
            articleAdapter.submitData(viewLifecycleOwner.lifecycle, it)
        })

    }

    private fun showErrorMessage(message: String) {
        //Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    private fun initSearchView() {
        edit_search
            .debouncingText()
            .debounce(500)
            .filter {
                it != lastQuery
            }
            .onEach {
                articleViewModel.loadArticle(it, lastSort)
                lastQuery = it
            }
            .launchIn(lifecycleScope)
    }
}