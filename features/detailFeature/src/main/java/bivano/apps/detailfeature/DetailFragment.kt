package bivano.apps.detailfeature

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment() {

    private val args: DetailFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        initWebView(args.url)
    }

    private fun setupToolbar() {
        toolbar.title = args.url
        toolbar.setupWithNavController(findNavController())
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView(url: String) {
        webview.settings.javaScriptEnabled = true
        webview.settings.userAgentString = "Android"
        webview.webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                //Todo Add progressbar
            }
        }
        webview.webViewClient = WebViewClient()
        webview.loadUrl(url)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webview.destroy()
    }
}