package bivano.apps.homefeature

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import bivano.apps.common.factory.ViewModelFactory
import bivano.apps.common.model.Article
import bivano.apps.homefeature.di.DaggerHomeComponent
import bivano.apps.yournews.di.DynamicModuleDependencies
import com.google.android.material.chip.Chip
import dagger.hilt.android.EntryPointAccessors
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val homeViewModel by viewModels<HomeViewModel> { viewModelFactory }

    private lateinit var homeAdapter: HomeAdapter
    private lateinit var homeFeaturedAdapter: HomeFeaturedAdapter
    private lateinit var imgDots: Array<ImageView>

    private val listArticle = ArrayList<Article>()

    private val listFeaturedArticle = ArrayList<Article>()


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
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    @ExperimentalCoroutinesApi
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //toolbar.logo = ContextCompat.getDrawable(requireContext(), bivano.apps.common.R.drawable.ic_search)
        collapsing_toolbar.setupWithNavController(toolbar, findNavController())
        homeViewModel.loadData(null)
        initRecyclerView()
        initViewPager()
        initChipFilter()
        observeData()
    }

    @ExperimentalCoroutinesApi
    private fun initChipFilter() {
        chip_group.setOnCheckedChangeListener { group, checkedId ->
            homeViewModel.loadData(group.findViewById<Chip>(checkedId).text.toString())
        }
    }

    private fun initViewPager() {
        homeFeaturedAdapter = HomeFeaturedAdapter(listFeaturedArticle)
        view_pager.adapter = homeFeaturedAdapter
    }

    private fun observeData() {
        homeViewModel.articleData.observe(viewLifecycleOwner, Observer {
            listArticle.clear()
            listArticle.addAll(it)
            homeAdapter.notifyDataSetChanged()
        })

        homeViewModel.featuredData.observe(viewLifecycleOwner, Observer {
            view_pager.setCurrentItem(0, false)
            listFeaturedArticle.clear()
            listFeaturedArticle.addAll(it)
            homeFeaturedAdapter.notifyDataSetChanged()
            showDotsIndicator()
        })
    }

    private fun showDotsIndicator() {
        createIndicator()
        view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                imgDots.forEach {
                    it.setImageDrawable(setStateDotDrawable(false))
                }
                imgDots[position].setImageDrawable(setStateDotDrawable(true))
            }
        })
    }

    private fun setStateDotDrawable(selected: Boolean): Drawable? {
        return if (selected) {
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.bg_selected_dot_item
            )
        } else {
            ContextCompat.getDrawable(
                requireContext(),
                R.drawable.bg_unselected_dot_item
            )
        }
    }

    private fun createIndicator() {
        container_indicator.removeAllViews()
        imgDots = Array(listFeaturedArticle.size) {
            ImageView(context)
        }

        imgDots.forEach { img ->
            img.setImageDrawable(setStateDotDrawable(false))
            val linearLayoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            linearLayoutParams.setMargins(4, 0, 4, 0)
            container_indicator.addView(img, linearLayoutParams)
        }


        imgDots[0].setImageDrawable(setStateDotDrawable(true))
    }

    private fun initRecyclerView() {
        recyclerview.setHasFixedSize(true)
        recyclerview.layoutManager = LinearLayoutManager(requireContext())
        homeAdapter = HomeAdapter(listArticle)
        recyclerview.adapter = homeAdapter
    }
}