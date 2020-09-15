package bivano.apps.homefeature.list

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
import androidx.recyclerview.widget.LinearLayoutManager
import bivano.apps.common.Result
import bivano.apps.common.adapter.ArticlePagedListAdapter
import bivano.apps.common.factory.ViewModelFactory
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

    private val args : ListHeadlineFragmentArgs by navArgs()

    private val listHeadlineViewModel by viewModels<ListHeadlineViewModel> { viewModelFactory }

    private lateinit var adapter: ArticlePagedListAdapter

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
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun initChipFilter() {
        chip_group.forEach {
            if ((it as Chip).text == args.category) {
                chip_group.check(it.id)
            }
        }

        chip_group.setOnCheckedChangeListener { group, checkedId ->
            val label = group.findViewById<Chip>(checkedId).text.toString().toLowerCase()
            listHeadlineViewModel.load(label)
        }
    }

    private fun initRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(context)
        adapter = ArticlePagedListAdapter()
        recyclerview.adapter = adapter
        adapter.onItemClick = {
            val action = ListHeadlineFragmentDirections.actionListHeadlineFragmentToDetailFragment(it.url)
            findNavController().navigate(action)
        }
    }

    private fun observeData() {
        listHeadlineViewModel.headlinePagedData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })

        listHeadlineViewModel.initialNetworkStateData.observe(viewLifecycleOwner, Observer {
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

        listHeadlineViewModel.networkStateData.observe(viewLifecycleOwner, Observer {
            adapter.setNetworkState(it)
            when (it) {
                is Result.ResponseError -> {
                    showErrorMessage(it.failure.message!!)
                }
                is Result.GeneralError -> {
                    showErrorMessage("There's something wrong")
                }
            }
        })
    }

    private fun showErrorMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

}