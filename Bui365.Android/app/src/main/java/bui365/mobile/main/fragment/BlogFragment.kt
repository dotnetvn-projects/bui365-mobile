package bui365.mobile.main.fragment


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import bui365.mobile.main.R
import bui365.mobile.main.activity.CommentsActivity
import bui365.mobile.main.activity.HandbookDetailArticleActivity
import bui365.mobile.main.adapter.HandbookArticleAdapter
import bui365.mobile.main.impl.HandbookArticleItemListener
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.model.pojo.EmptyArticle
import bui365.mobile.main.presenter.BlogPresenter
import bui365.mobile.main.util.showSnackBarAction
import bui365.mobile.main.view.BlogView
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.share.Sharer
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import org.json.JSONArray
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*


class BlogFragment : Fragment(), BlogView {

    override lateinit var presenter: BlogPresenter
    private var swipeRefresh: SwipeRefreshLayout? = null
    private var rcvHandbookArticle: RecyclerView? = null
    private var layoutManager: LinearLayoutManager? = null
    private var mArticleList: ArrayList<Article>? = null
    private var articleAdapter: HandbookArticleAdapter? = null
    private var txtErrorLoading: TextView? = null
    private var progressBar: ProgressBar? = null
    private var callbackManager: CallbackManager? = null
    private var shareDialog: ShareDialog? = null

    private var start = 0
    private var loading = false
    private var loadEnd = false
    private val visibleThreshold = 2
    private var visibleItemCount: Int = 0
    private var firstVisibleItem: Int = 0
    private var totalItemCount: Int = 0
    private lateinit var handler: Handler
    private val exception: Exception? = null

    private var mArticleItemListener: HandbookArticleItemListener = object : HandbookArticleItemListener {

        override fun onImageClick(position: Int) {
            presenter.loadDetailArticle(mArticleList!![position].id!!)
        }

        override fun onCommentClick(position: Int) {
            presenter.loadComment(mArticleList!![position].url!!)
        }

        override fun onShareClick(position: Int) {
            presenter.shareArticle(mArticleList!![position].url!!)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        callbackManager = CallbackManager.Factory.create()
        shareDialog = ShareDialog(this)
        shareDialog!!.registerCallback(callbackManager, object : FacebookCallback<Sharer.Result> {
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
        val root = inflater.inflate(R.layout.fragment_handbook, container, false)
        swipeRefresh = root.findViewById(R.id.swipeRefresh)
        mArticleList = ArrayList()
        rcvHandbookArticle = root.findViewById(R.id.rcvHandbookArticle)
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvHandbookArticle!!.layoutManager = layoutManager
        rcvHandbookArticle!!.isNestedScrollingEnabled = false
        articleAdapter = HandbookArticleAdapter(activity!!, mArticleList!!, mArticleItemListener)
        rcvHandbookArticle!!.adapter = articleAdapter
        txtErrorLoading = root.findViewById(R.id.txtErrorLoading)
        progressBar = root.findViewById(R.id.progressBar)
        presenter.start()
        setHasOptionsMenu(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        swipeRefresh!!.setOnRefreshListener {
            swipeRefresh!!.isRefreshing = true
            mArticleList!!.clear()
            articleAdapter!!.notifyDataSetChanged()
            start = 0
            loadEnd = false
            loading = false
            presenter.start()
        }

        rcvHandbookArticle!!.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                visibleItemCount = layoutManager!!.childCount
                totalItemCount = layoutManager!!.itemCount
                firstVisibleItem = layoutManager!!.findFirstVisibleItemPosition()
                if (dy > 0 && !loading && !loadEnd
                        && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                    loading = true
                    val emptyArticle = EmptyArticle()
                    mArticleList!!.add(emptyArticle)
                    articleAdapter!!.notifyItemInserted(mArticleList!!.size - 1)
                    handler = Handler()
                    handler.postDelayed({
                        mArticleList!!.removeAt(mArticleList!!.size - 1)
                        articleAdapter!!.notifyItemRemoved(mArticleList!!.size)
                        start++
                        presenter.loadTask(false, start)
                    }, 1000)
                }
            }
        })

    }

    override fun showResult(result: Any) {
        swipeRefresh!!.isRefreshing = false
        try {
            val jsonArray = JSONArray(result.toString())
            val length = jsonArray.length()
            for (i in 0 until length) {
                val jsonObject = jsonArray.getJSONObject(i)
                val article = Article()
                article.id = jsonObject.getString("id")
                article.categoryId = jsonObject.getString("categoryId")
                article.categoryName = jsonObject.getString("categoryName")
                article.content = jsonObject.getString("content")
                article.description = jsonObject.getString("description")
                article.image = jsonObject.getString("image")
                article.subjectId = jsonObject.getString("subjectId")
                article.subjectName = jsonObject.getString("subjectName")
                article.title = jsonObject.getString("title")
                article.updatedDate = jsonObject.getString("updatedDate")
                article.url = jsonObject.getString("url")
                mArticleList!!.add(article)
            }
            articleAdapter!!.notifyDataSetChanged()
            loading = false
            if (length == 0) {
                loadEnd = true
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

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
        txtErrorLoading!!.visibility = View.VISIBLE
        view!!.showSnackBarAction(getString(R.string.connection_failed), Snackbar.LENGTH_INDEFINITE) {
            setAction(getString(R.string.retry)) {
                hideError()
                presenter.start()
            }
        }
    }

    override fun hideError() {
        txtErrorLoading!!.visibility = View.GONE
    }

    override fun showLoading() {
        progressBar!!.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar!!.visibility = View.GONE
    }

    override fun showShareArticle(content: ShareLinkContent) {
        shareDialog!!.show(content)
    }

    override fun showComment(url: String) {
        Intent(activity, CommentsActivity::class.java).apply {
            putExtra("url", url)
        }.also { startActivity(it) }
    }

    override fun showDetailArticle(id: String) {
        Intent(activity, HandbookDetailArticleActivity::class.java).apply {
            putExtra("articleId", id)
        }.also { startActivity(it) }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.handbook_activity_menu, menu)
    }

    companion object {

        fun newInstance(): BlogFragment {
            return BlogFragment()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        callbackManager!!.onActivityResult(requestCode, resultCode, data)
    }
}// Required empty public constructor