package bivano.apps.yournews.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import bivano.apps.yournews.R
import bivano.apps.yournews.ui.dialog.DownloadDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener,
    SplitInstallStateUpdatedListener {

    private lateinit var navController: NavController
    private lateinit var splitManager: SplitInstallManager
    private lateinit var splitRequest: SplitInstallRequest
    private lateinit var downloadDialog: DownloadDialog

    private var achievedFeatureInstalled = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initCheckFeature()
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        navController = navHost.navController
        NavigationUI.setupWithNavController(bottom_navigation, navController)

        navHost.navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when (destination.id) {
                R.id.detailFragment, R.id.listHeadlineFragment -> {
                    showNavigation(false)
                }
                else -> {
                    showNavigation(true)
                }
            }
        }

        bottom_navigation.setOnNavigationItemSelectedListener(this)
    }

    private fun initCheckFeature() {
        splitManager = SplitInstallManagerFactory.create(applicationContext)
    }


    private fun showNavigation(isShow: Boolean) {
        bottom_navigation.visibility = if (isShow) View.VISIBLE else View.GONE
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.action_achieved) {
            if (achievedFeatureInstalled) {
                onNavDestinationSelected(item)
            } else {
                initDialog()
                requestAchievedFeature()
            }
            achievedFeatureInstalled
        } else {
            onNavDestinationSelected(item)
            true
        }
    }

    private fun initDialog() {
        downloadDialog = DownloadDialog()
        downloadDialog.show(supportFragmentManager, "")
    }

    private fun requestAchievedFeature() {
        splitRequest = SplitInstallRequest.newBuilder()
            .addModule("achievedfeature")
            .build()

        splitManager.registerListener(this)

        splitManager.startInstall(splitRequest)
            .addOnFailureListener {
                when ((it as SplitInstallException).errorCode) {
                    SplitInstallErrorCode.NETWORK_ERROR -> {
                        Toast.makeText(
                            applicationContext,
                            "No Internet Connection",
                            Toast.LENGTH_SHORT
                        ).show()
                        downloadDialog.dialog?.run {
                            if (isShowing) dismiss()
                        }
                    }
                    SplitInstallErrorCode.ACTIVE_SESSIONS_LIMIT_EXCEEDED -> {

                    }
                }
                it.printStackTrace()
            }
    }

    private fun onNavDestinationSelected(item: MenuItem): Boolean {
        val builder = NavOptions.Builder()
            .setLaunchSingleTop(true)
            .setEnterAnim(R.anim.nav_default_enter_anim)
            .setExitAnim(R.anim.nav_default_exit_anim)
            .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
            .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
        if (item.order and Menu.CATEGORY_SECONDARY == 0) {
            builder.setPopUpTo(
                findStartDestination(navController.graph)!!.id, false
            )
        }
        val options = builder.build()
        return try {
            navController.navigate(item.itemId, null, options)
            true
        } catch (e: IllegalArgumentException) {
            false
        }
    }

    private fun findStartDestination(graph: NavGraph): NavDestination? {
        var startDestination: NavDestination? = graph
        while (startDestination is NavGraph) {
            val parent = startDestination
            startDestination = parent.findNode(parent.startDestination)
        }
        return startDestination
    }

    override fun onStateUpdate(state: SplitInstallSessionState) {
        when (state.status()) {
            SplitInstallSessionStatus.DOWNLOADING -> {
                val totalBytes = state.totalBytesToDownload()
                val progress = state.bytesDownloaded()
                val download = (100 * progress.toDouble() / totalBytes)
                downloadDialog.setDownloadingDialog(download.toInt())
            }
            SplitInstallSessionStatus.INSTALLING -> {
                downloadDialog.setInstallingDialog()
            }
            SplitInstallSessionStatus.INSTALLED -> {
                achievedFeatureInstalled = true
                downloadDialog.setSuccessInstallDialog()
            }
            SplitInstallSessionStatus.FAILED -> {
                downloadDialog.setFailedDialog("Failed Installing Feature")

            }
            SplitInstallSessionStatus.PENDING -> {
                downloadDialog.setPreparingDialog()
            }
        }
    }


    override fun onStart() {
        super.onStart()
        achievedFeatureInstalled = splitManager.installedModules.contains("achievedfeature")
    }

    override fun onStop() {
        super.onStop()
        splitManager.unregisterListener(this)
    }
}