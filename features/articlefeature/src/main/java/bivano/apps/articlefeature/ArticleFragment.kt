package bivano.apps.articlefeature

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import bivano.apps.articlefeature.di.DaggerArticleComponent
import bivano.apps.common.Result
import bivano.apps.common.extension.debouncingText
import bivano.apps.common.factory.ViewModelFactory
import bivano.apps.yournews.di.DynamicModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.fragment_article.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ArticleFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val articleViewModel by viewModels<ArticleViewModel> { viewModelFactory }

    private lateinit var articleAdapter: ArticleAdapter

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
        observeData()
    }

    private fun initRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        articleAdapter = ArticleAdapter()
        recyclerview.adapter = articleAdapter

    }

    private fun observeData() {
        articleViewModel.networkStateData.observe(viewLifecycleOwner, Observer {
            println("CHeck state : $it")
            //TODO set view based on state
            when (it) {
                is Result.Loading -> {

                }
                is Result.Success -> {

                }
                is Result.ResponseError -> {

                }
                is Result.GeneralError -> {

                }


            }
        })

        articleViewModel.articlePagedData.observe(viewLifecycleOwner, Observer {
            articleAdapter.submitList(it)
        })

    }

    @ExperimentalCoroutinesApi
    private fun initSearchView() {
        edit_search
            .debouncingText()
            .debounce(500)
            .onEach {
                articleViewModel.loadArticle(it, "relevancy")
            }
            .launchIn(lifecycleScope)
    }
}