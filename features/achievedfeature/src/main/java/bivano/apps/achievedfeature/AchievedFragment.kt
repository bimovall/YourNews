package bivano.apps.achievedfeature

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import bivano.apps.achievedfeature.di.DaggerAchievedComponent
import bivano.apps.common.adapter.ArticlePagedListAdapter
import bivano.apps.common.factory.ViewModelFactory
import bivano.apps.common.model.Article
import bivano.apps.yournews.di.DynamicModuleDependencies
import com.google.android.play.core.splitcompat.SplitCompat
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.fragment_achieved.*
import javax.inject.Inject

class AchievedFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var adapter: ArticlePagedListAdapter

    private val achievedViewModel by viewModels<AchievedViewModel> { viewModelFactory }

    override fun onCreate(savedInstanceState: Bundle?) {
        initDependencyInjector()
        super.onCreate(savedInstanceState)
    }

    private fun initDependencyInjector() {
        val module = EntryPointAccessors.fromApplication(
            requireActivity().applicationContext,
            DynamicModuleDependencies::class.java
        )
        DaggerAchievedComponent.factory().create(module).inject(this)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        SplitCompat.install(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_achieved, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initSearchView()
        observeData()
        initRecyclerView()
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

    private fun initRecyclerView() {
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        adapter = ArticlePagedListAdapter()
        recyclerview.adapter = adapter
        adapter.onItemClick = {
            val action = AchievedFragmentDirections.actionActionAchievedToDetailFragment(it.url)
            findNavController().navigate(action)
        }

        adapter.onItemLongClick = {
            showDialog(it)
        }
    }

    private fun showDialog(article: Article) {
        AlertDialog.Builder(context)
            .setTitle("Confirmation")
            .setMessage("Do you want to delete this news?")
            .setPositiveButton("Yes") { dialogInterface, i ->
                achievedViewModel.deleteArticle(article)
                Toast.makeText(context, "Successfully deleted the news", Toast.LENGTH_SHORT).show()
                dialogInterface.dismiss()
            }
            .setNegativeButton("No") { dialogInterface, i ->
                dialogInterface.dismiss()
            }
            .show()
    }

    private fun initSearchView() {
        edit_search.doOnTextChanged { text, start, before, count ->
            achievedViewModel.search(text.toString())
        }
    }

    private fun observeData() {
        achievedViewModel.resultData.observe(viewLifecycleOwner, Observer {
            adapter.submitList(it)
        })
    }
}