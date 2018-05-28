package bui365.mobile.main.fragment


import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.util.Log
import android.view.*
import android.widget.ImageView
import bui365.mobile.main.R
import bui365.mobile.main.contract.DetailArticleContract
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.fragment_blog_detail_article.*
import kotlinx.android.synthetic.main.layout_progress_loading.*
import org.json.JSONObject


class DetailArticleFragment : Fragment(), DetailArticleContract.View {
    private lateinit var articleId: String
    private var title: String? = null
    private var content: String? = null
    private var description: String? = null
    private var url: String? = null
    private var image: String? = null
    private var updatedDate: String? = null

    private var collapsingToolbar: CollapsingToolbarLayout? = null
    private var appBarLayout: AppBarLayout? = null
    private var imgDetail: ImageView? = null

    override lateinit var presenter: DetailArticleContract.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            articleId = arguments!!.getString(ARTICLE_ID)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_blog_detail_article, container, false)
        setHasOptionsMenu(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        webView.settings.useWideViewPort = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.defaultFontSize = 36
        webView.settings.builtInZoomControls = true
        webView.settings.setSupportZoom(true)

        if (activity != null) {
            collapsingToolbar = activity!!.findViewById(R.id.collapsingToolbar)
            appBarLayout = activity!!.findViewById(R.id.appBarLayout)
            imgDetail = activity!!.findViewById(R.id.imgDetail)
        }
        initCollapsingToolbar()
        dynamicToolbarColor()
        toolbarTextAppearance()

        presenter.loadDetailArticle(articleId)
    }

    private fun initCollapsingToolbar() {
        appBarLayout!!.setExpanded(true)
        appBarLayout!!.addOnOffsetChangedListener(object : AppBarLayout.OnOffsetChangedListener {
            internal var isShow = false
            internal var scrollRange = -1

            override fun onOffsetChanged(appBarLayout: AppBarLayout, verticalOffset: Int) {
                if (scrollRange == -1) {
                    scrollRange = appBarLayout.totalScrollRange
                    collapsingToolbar!!.title = ""
                }
                if (scrollRange + verticalOffset == 0) {
                    collapsingToolbar!!.title = ""
                    isShow = true
                } else {
                    Log.e(TAG, "initCollapsingToolbar: $title")
                    collapsingToolbar!!.title = title
                }
            }
        })
    }

    private fun dynamicToolbarColor() {
        if (activity != null) {
            collapsingToolbar!!.setContentScrimColor(ContextCompat.getColor(activity!!, R.color.colorPrimary))
            collapsingToolbar!!.setStatusBarScrimColor(ContextCompat.getColor(activity!!, R.color.colorPrimaryDark))
        }
    }

    private fun toolbarTextAppearance() {
        collapsingToolbar!!.setCollapsedTitleTextAppearance(R.style.collapsedAppBar)
        collapsingToolbar!!.setExpandedTitleTextAppearance(R.style.expandedAppBar)
    }

    override fun showResult(result: Any) {
        try {
            val jsonObject = JSONObject(result.toString())
            content = jsonObject.getString("content")
            description = jsonObject.getString("description")
            image = jsonObject.getString("image")
            title = jsonObject.getString("title")
            updatedDate = jsonObject.getString("updatedDate")
            url = jsonObject.getString("url")

            webView.loadData(content, "text/html; charset=UTF-8", "utf-8")

            if (image != null && image != "") {
                imgDetail!!.visibility = View.VISIBLE
                Glide.with(activity!!).load(image).into(imgDetail!!)
            } else {
                imgDetail!!.visibility = View.GONE
            }

            txtTitle.text = title
            txtUpdatedDate.text = updatedDate

        } catch (e: Exception) {
            e.printStackTrace()
        }

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

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.handbook_detail_article_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.favorite_menu -> {
            }
            R.id.share_menu -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        private const val ARTICLE_ID = "articleId"
        private const val TAG = "kyo"

        fun newInstance(articleId: String) = DetailArticleFragment().apply {
            arguments = Bundle().apply {
                putString(ARTICLE_ID, articleId)
            }
        }

    }
}
