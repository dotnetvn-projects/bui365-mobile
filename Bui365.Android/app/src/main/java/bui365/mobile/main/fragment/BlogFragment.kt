package bui365.mobile.main.fragment


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.Toast
import bui365.mobile.main.R
import bui365.mobile.main.activity.CommentsActivity
import bui365.mobile.main.activity.DetailArticleActivity
import bui365.mobile.main.activity.SearchArticleActivity
import bui365.mobile.main.adapter.BlogArticleAdapter
import bui365.mobile.main.contract.BlogContract
import bui365.mobile.main.graphic.DividerItemDecorator
import bui365.mobile.main.impl.HandbookArticleItemListener
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.model.pojo.EmptyArticle
import bui365.mobile.main.model.pojo.FacebookPOJO
import bui365.mobile.main.util.showSnackBarAction
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import kotlinx.android.synthetic.main.fragment_blog.*
import kotlinx.android.synthetic.main.layout_error_loading.*
import kotlinx.android.synthetic.main.layout_progress_loading.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException


class BlogFragment : Fragment(), BlogContract.View, HandbookArticleItemListener {

    override lateinit var presenter: BlogContract.Presenter
    private lateinit var layoutManager: LinearLayoutManager
    private lateinit var callbackManager: CallbackManager
    private lateinit var shareDialog: ShareDialog
    private lateinit var articleAdapter: BlogArticleAdapter
    private var articleList: ArrayList<Article> = ArrayList()

    private var start = 0
    private var loading = false
    private var loadEnd = false
    private val visibleThreshold = 2
    private var visibleItemCount: Int = 0
    private var firstVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private lateinit var handler: Handler
    private val exception: Exception? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
        shareDialog = ShareDialog(this)
        shareDialog.registerCallback(callbackManager, object : FacebookCallback<Sharer.Result> {
            override fun onSuccess(result: Sharer.Result?) {
                Toast.makeText(activity, getString(R.string.txtShareSuccess), Toast.LENGTH_SHORT).show()
            }

            override fun onCancel() {

            }

            override fun onError(error: FacebookException?) {
                error!!.printStackTrace()
            }

        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_blog, container, false)
        articleList = ArrayList()
        articleAdapter = BlogArticleAdapter(activity!!, articleList, this)
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        setHasOptionsMenu(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rcvBlogArticle.layoutManager = layoutManager
        rcvBlogArticle.isNestedScrollingEnabled = false

        val divider = DividerItemDecorator(ContextCompat.getDrawable(activity!!.applicationContext, R.drawable.divider)!!)
        rcvBlogArticle.addItemDecoration(divider)
        rcvBlogArticle.adapter = articleAdapter

        swipeRefresh.setOnRefreshListener {
            swipeRefresh!!.isRefreshing = true
            articleList.clear()
            articleAdapter.notifyDataSetChanged()
            start = 0
            loadEnd = false
            loading = false
            presenter.subscribe()
        }

        rcvBlogArticle!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager.childCount
                totalItemCount = layoutManager.itemCount
                firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                /** load more items if user scrolls down
                 * loadEnd: return true if there is no more item to display
                 * firstVisibleItem + visibleThreshold: ready to load more items if user is in the position
                 * start: the index to display the next items
                 */
                if (dy > 0 && !loading && !loadEnd
                        && (totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold)) {
                    loading = true
                    val emptyArticle = EmptyArticle()
                    articleList.add(emptyArticle)
                    articleAdapter.notifyItemInserted(articleList.size - 1)
                    handler = Handler()
                    handler.postDelayed({
                        articleList.removeAt(articleList.size - 1)
                        articleAdapter.notifyItemRemoved(articleList.size)
                        start++
                        presenter.loadTask(false, start)
                    }, 1000)
                }
            }
        })

        presenter.subscribe()
    }

    override fun showResult(articles: List<Article>, loadEnd: Boolean) {
        swipeRefresh!!.isRefreshing = false
        for (article in articles) {
            articleList.add(article)
            presenter.loadFacebookSdk(article.url!!)
        }
        articleAdapter.notifyDataSetChanged()
        loading = false
        this.loadEnd = loadEnd
    }

    //display numbers of view, comment, share from Facebook SDK
    override fun showFacebookSdk(facebookPOJO: FacebookPOJO) {
        for (article in articleList) {
            if (article.url == facebookPOJO.id) {
                article.facebookPOJO = facebookPOJO
                break
            }
        }
        articleAdapter.notifyDataSetChanged()
    }

    private fun showError(exception: Exception?) {
        if (activity != null) {
            if (exception != null)
                Log.e("showError", exception.toString())
            when (exception) {
                is UnknownHostException -> {
                    Toast.makeText(activity, activity!!.getString(R.string.internet_failed), Toast.LENGTH_SHORT).show()
                }
                is SocketTimeoutException -> {
                    Toast.makeText(activity, activity!!.getString(R.string.network_timeout), Toast.LENGTH_SHORT).show()
                }
                else -> {
                    Toast.makeText(activity, activity!!.getString(R.string.connection_failed), Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun showError() {
        showError(exception)
        txtErrorLoading.visibility = View.VISIBLE
        view!!.showSnackBarAction(getString(R.string.connection_failed), Snackbar.LENGTH_INDEFINITE) {
            setAction(getString(R.string.retry)) {
                hideError()
                presenter.subscribe()
            }
        }
    }

    override fun hideError() {
        txtErrorLoading.visibility = View.GONE
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showShareArticle(content: ShareLinkContent) {
        shareDialog.show(content)
    }

    override fun showComment(url: String) {
        Intent(activity, CommentsActivity::class.java).apply {
            putExtra("url", url)
        }.also { startActivity(it) }
    }

    override fun showDetailArticle(id: String) {
        Intent(activity, DetailArticleActivity::class.java).apply {
            putExtra("articleId", id)
        }.also { startActivity(it) }
    }

    override fun onImageClick(article: Article) {
        presenter.loadDetailArticle(article.id!!)
    }

    override fun onCommentClick(article: Article) {
        presenter.loadComment(article.url!!)
    }

    override fun onShareClick(article: Article) {
        presenter.shareArticle(article.url!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.handbook_activity_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.actionSearch -> {
                Intent(activity, SearchArticleActivity::class.java).also {
                    startActivity(it)
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {

        fun newInstance(): BlogFragment {
            return BlogFragment()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager.onActivityResult(requestCode, resultCode, data)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }
}// Required empty public constructor