package bui365.mobile.main.fragment


import android.annotation.SuppressLint
import android.net.Uri
import android.net.http.SslError
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.annotation.RequiresApi
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import bui365.mobile.main.R
import bui365.mobile.main.presenter.CommentsPresenter
import bui365.mobile.main.view.CommentsView
import kotlinx.android.synthetic.main.fragment_comments.*
import kotlinx.android.synthetic.main.layout_progress_loading.*


class CommentsFragment : Fragment(), CommentsView {

    override lateinit var presenter: CommentsPresenter
    private lateinit var url: String
    private lateinit var webViewPop: WebView

    companion object {

        private const val ARTICLE_URL = "url"
        private const val NUMBER_OF_COMMENTS = 5

        fun newInstance(url: String) = CommentsFragment().apply {
            arguments = Bundle().apply {
                putString(ARTICLE_URL, url)
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            url = arguments!!.getString(ARTICLE_URL)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_comments, container, false)
        return root
    }

    override fun showError() {
    }

    override fun hideError() {
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun loadComments() {
        webComment.webViewClient = UriWebViewClient()
        webComment.webChromeClient = UriChromeClient()
        webComment.settings.javaScriptEnabled = true
        webComment.settings.setAppCacheEnabled(true)
        webComment.settings.domStorageEnabled = true
        webComment.settings.javaScriptCanOpenWindowsAutomatically = true
        webComment.settings.setSupportMultipleWindows(true)
        webComment.settings.setSupportZoom(false)
        webComment.settings.builtInZoomControls = false
        CookieManager.getInstance().setAcceptCookie(true)
        if (Build.VERSION.SDK_INT >= 21) {
            webComment.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            CookieManager.getInstance().setAcceptThirdPartyCookies(webComment, true)
        }

        // facebook comment widget including the article url
        val html = "<!doctype html> <html lang=\"en\"> <head></head> <body> " +
                "<div id=\"fb-root\"></div> <script>(function(d, s, id) " +
                "{ var js, fjs = d.getElementsByTagName(s)[0]; if (d.getElementById(id)) return; js = d.createElement(s); " +
                "js.id = id; js.src = \"//connect.facebook.net/en_US/sdk.js#xfbml=1&version=v2.6\"; " +
                "fjs.parentNode.insertBefore(js, fjs); }" + "(document, 'script', 'facebook-jssdk'));</script> " +
                "<div class=\"fb-comments\" data-href=\"" + url + "\" " +
                "data-numposts=\"" + NUMBER_OF_COMMENTS + "\" data-order-by=\"reverse_time\">" +
                "</div> </body> </html>"

        webComment.loadDataWithBaseURL("http://bui365.com/", html, "text/html", "UTF-8", null)
        webComment.minimumHeight = 200
    }

    private inner class UriWebViewClient : WebViewClient() {
        @Suppress("OverridingDeprecatedMember")
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            val host = Uri.parse(url).host
            return host != "m.facebook.com"
        }

        @RequiresApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            val url = request!!.url.toString()
            val host = Uri.parse(url).host
            return host != "m.facebook.com"
        }

        override fun onPageFinished(view: WebView, url: String) {
            super.onPageFinished(view, url)
            Uri.parse(url).host
            hideLoading()
            if (url.contains("/plugins/close_popup.php?reload")) {
                val handler = Handler()
                handler.postDelayed({
                    mContainer.removeView(webViewPop)
                    loadComments()
                }, 600)
            }
        }

        override fun onReceivedSslError(view: WebView, handler: SslErrorHandler, error: SslError) {
            hideLoading()
        }
    }

    internal inner class UriChromeClient : WebChromeClient() {

        @SuppressLint("SetJavaScriptEnabled")
        override fun onCreateWindow(view: WebView, isDialog: Boolean, isUserGesture: Boolean, resultMsg: Message): Boolean {
            webViewPop = WebView(activity!!.applicationContext)
            webViewPop.isVerticalScrollBarEnabled = false
            webViewPop.isHorizontalScrollBarEnabled = false
            webViewPop.webViewClient = UriWebViewClient()
            webViewPop.webChromeClient = this
            webViewPop.settings.javaScriptEnabled = true
            webViewPop.settings.domStorageEnabled = true
            webViewPop.settings.setSupportZoom(false)
            webViewPop.settings.builtInZoomControls = false
            webViewPop.settings.setSupportMultipleWindows(true)
            webViewPop.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT)
            mContainer.addView(webViewPop)
            val transport = resultMsg.obj as WebView.WebViewTransport
            transport.webView = webViewPop
            resultMsg.sendToTarget()

            return true
        }

        override fun onConsoleMessage(cm: ConsoleMessage): Boolean {
            Log.i("kyo", "onConsoleMessage: " + cm.message())
            return true
        }

        override fun onCloseWindow(window: WebView) {}
    }

}// Required empty public constructor
