package bivano.apps.homefeature.list

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.forEach
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import bivano.apps.common.adapter.ArticleFooterLoadStateAdapter
import bivano.apps.common.adapter.ArticlePagingDataAdapter
import bivano.apps.common.factory.ViewModelFactory
import bivano.apps.common.model.Article
import bivano.apps.homefeature.R
import bivano.apps.homefeature.di.DaggerHomeComponent
import bivano.apps.yournews.di.DynamicModuleDependencies
import com.google.android.material.chip.Chip
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.fragment_list_headline.*
import javax.inject.Inject

class ListHeadlineFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val args: ListHeadlineFragmentArgs by navArgs()

    private val listHeadlineViewModel by viewModels<ListHeadlineViewModel> { viewModelFactory }

    private lateinit var articleAdapter: ArticlePagingDataAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencyInjector()
        super.onCreate(savedInstanceState)
    }

    private fun initDependencyInjector() {
        val module = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            DynamicModuleDependencies::class.java
        )
        DaggerHomeComponent.factory().create(module).inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_list_headline, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        listHeadlineViewModel.initLoad(args.category)
        setupToolbar()
        initRecyclerView()
        initChipFilter()
        observeData()
    }

    private fun setupToolbar() {
        toolbar.setupWithNavController(findNavController())
    }

    private fun initChipFilter() {
        chip_group.forEach {
            if ((it as Chip).text == args.category) {
                chip_group.check(it.id)
            }
        }

        chip_group.setOnCheckedChangeListener { group, checkedId ->
            val label = group.findViewById<Chip>(checkedId).text.toString().toLowerCase()
            listHeadlineViewModel.initLoad(label)
        }
    }

    private fun initRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        articleAdapter = ArticlePagingDataAdapter()
        recyclerview.adapter =
            articleAdapter.withLoadStateFooter(ArticleFooterLoadStateAdapter(articleAdapter::retry))
        articleAdapter.onItemClick = {
            val action =
                ListHeadlineFragmentDirections.actionListHeadlineFragmentToDetailFragment(it.url)
            findNavController().navigate(action)
        }

        articleAdapter.onItemLongClick = {
            showDialog(it)
        }

        articleAdapter.addLoadStateListener {
            when {
                it.refresh is LoadState.Error -> {
                    container_empty.visibility = View.VISIBLE
                    recyclerview.visibility = View.GONE
                    tv_error_title.text = "There's something wrong"
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

    private fun showDialog(article: Article) {
        AlertDialog.Builder(context)
            .setTitle("Confirmation")
            .setMessage("Do you want to save this news?")
            .setPositiveButton("Yes") { dialogInterface, i ->
                listHeadlineViewModel.saveNews(article)
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
        listHeadlineViewModel.headlinePaging.observe(viewLifecycleOwner, Observer {
            recyclerview.scrollToPosition(0)
            articleAdapter.submitData(lifecycle, it)
        })
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}