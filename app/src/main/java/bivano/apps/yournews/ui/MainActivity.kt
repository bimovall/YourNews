package bivano.apps.yournews.ui

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import bivano.apps.yournews.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val homeViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        NavigationUI.setupWithNavController(bottom_navigation, navHost.navController)

        navHost.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {
                R.id.detailFragment -> {
                    showNavigation(false)
                }
                else -> {
                    showNavigation(true)
                }
            }
        }
    }

    private fun showNavigation(isShow: Boolean) {
        bottom_navigation.visibility = if (isShow) View.VISIBLE else View.GONE
    }
}