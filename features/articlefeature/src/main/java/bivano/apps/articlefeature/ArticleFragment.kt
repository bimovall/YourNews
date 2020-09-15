package bivano.apps.articlefeature

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
import androidx.recyclerview.widget.LinearLayoutManager
import bivano.apps.articlefeature.di.DaggerArticleComponent
import bivano.apps.common.Result
import bivano.apps.common.adapter.ArticlePagedListAdapter
import bivano.apps.common.extension.debouncingText
import bivano.apps.common.factory.ViewModelFactory
import bivano.apps.yournews.di.DynamicModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ArticleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val articleViewModel by viewModels<ArticleViewModel> { viewModelFactory }

    private lateinit var articleAdapter: ArticlePagedListAdapter

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
        initSearchView()
        initRecyclerView()
        initChipFilter()
        observeData()
    }

    private fun initChipFilter() {
        chip_group.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.chip_popularity -> {
                    lastSort = "popularity"
                    articleViewModel.loadArticle(lastQuery, lastSort)
                }
                R.id.chip_latest -> {
                    lastSort = "publishedAt"
                    articleViewModel.loadArticle(lastQuery, "publishedAt")
                }
                R.id.chip_relevance -> {
                    lastSort = "relevancy"
                    articleViewModel.loadArticle(lastQuery, "relevancy")
                }
            }
        }
    }

    private fun initRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        articleAdapter = ArticlePagedListAdapter()
        articleAdapter.apply {
            recyclerview.adapter = this
            onItemClick = {
                val action = ArticleFragmentDirections.actionActionArticleToDetailFragment(it.url)
                findNavController().navigate(action)
            }
        }
    }

    private fun observeData() {
        articleViewModel.networkStateData.observe(viewLifecycleOwner, Observer {
            articleAdapter.setNetworkState(it)
            when (it) {
                is Result.ResponseError -> {
                    showErrorMessage(it.failure.message!!)
                }
                is Result.GeneralError -> {
                    showErrorMessage("There's something wrong")
                }
            }
        })

        articleViewModel.initialNetworkStateData.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Loading -> {

                }
                is Result.Success -> {
                    if (it.data.isEmpty()) {
                        container_empty.visibility = View.VISIBLE
                        recyclerview.visibility = View.GONE
                    } else {
                        container_empty.visibility = View.GONE
                        recyclerview.visibility = View.VISIBLE
                    }
                }
                is Result.ResponseError -> {
                    showErrorMessage(it.failure.message!!)
                }
                is Result.GeneralError -> {

                }
            }
        })

        articleViewModel.articlePagedData.observe(viewLifecycleOwner, Observer {
            articleAdapter.submitList(it)
        })

    }

    private fun showErrorMessage(message: String) {
        //Snackbar.make(root, message, Snackbar.LENGTH_SHORT).show()
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

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