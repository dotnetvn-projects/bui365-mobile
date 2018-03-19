package bui365.mobile.main.fragment


import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.*
import bui365.mobile.main.R
import bui365.mobile.main.activity.HandbookActivity
import bui365.mobile.main.activity.HandbookDetailArticleActivity
import bui365.mobile.main.adapter.MainSlidingAdapter
import bui365.mobile.main.impl.MainItemListener
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.presenter.MainActivityPresenter
import bui365.mobile.main.util.showSnackBarAction
import bui365.mobile.main.view.MainActivityView
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment(), MainActivityView {

    override lateinit var presenter: MainActivityPresenter
    private lateinit var btnHandbook: Button
    private lateinit var btnBlog: Button
    private lateinit var btnFavorite: Button
    private lateinit var imgLogo: ImageView
    private lateinit var mPager: ViewPager
    private lateinit var txtErrorLoading: TextView
    private lateinit var progressBar: ProgressBar
    private var articles: ArrayList<Article> = ArrayList()
    private var currentPage = 0
    private lateinit var metrics: DisplayMetrics
    private lateinit var slidingAdapter: MainSlidingAdapter
    private val exception: Exception? = null

    private val mainItemListener = object : MainItemListener {
        override fun onImageClick(position: Int) {
            val article = articles[position]
            val detailArticle = Intent(activity, HandbookDetailArticleActivity::class.java)
            detailArticle.putExtra("articleId", article.id)
            startActivity(detailArticle)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_main_activity, container, false)
        with(root) {
            metrics = resources.displayMetrics
            if (activity != null) {
                mPager = activity!!.findViewById(R.id.mPager)
                txtErrorLoading = activity!!.findViewById(R.id.txtErrorLoading)
                progressBar = activity!!.findViewById(R.id.progressBar)
            }
            btnHandbook = (root.findViewById<Button>(R.id.btnHandbook)).also {
                it.setOnClickListener { presenter.openTaskDetails(it.id) }
            }
            btnBlog = root.findViewById(R.id.btnBlog)
            btnFavorite = root.findViewById(R.id.btnFavorite)
            imgLogo = root.findViewById(R.id.imgLogo)
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mPager.layoutParams.width = metrics.widthPixels
        mPager.layoutParams.height = metrics.heightPixels / 2

        slidingAdapter = MainSlidingAdapter(activity!!, articles, mainItemListener)
        mPager.adapter = slidingAdapter

        imgLogo.layoutParams.width = metrics.widthPixels / 2

        val handler = Handler()
        val update = Runnable {
            if (currentPage == articles.size) {
                currentPage = 0
            }
            mPager.setCurrentItem(currentPage++, true)
        }
        Timer().schedule(object : TimerTask() {
            override fun run() {
                handler.post(update)
            }
        }, 2000, 5000)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.main_activity_menu, menu)
    }

    override fun showResult(articles: List<Article>) {
        for (article in articles) {
            this.articles.add(article)
        }
        slidingAdapter.notifyDataSetChanged()
    }

    override fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    override fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    override fun showTaskDetailUi(id: Int) {
        when (id) {
            R.id.btnHandbook -> {
                val intent = Intent(activity, HandbookActivity::class.java)
                startActivity(intent)
            }

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
                    view!!.showSnackBarAction(getString(R.string.connection_failed), Snackbar.LENGTH_INDEFINITE) {
                        setAction("retry") {
                            hideError()
                            presenter.start()
                        }
                    }
                }
            }
        }
    }

    override fun showError() {
        showError(exception)
        articles.clear()
        slidingAdapter.notifyDataSetChanged()
        txtErrorLoading.visibility = View.VISIBLE
    }

    override fun hideError() {
        txtErrorLoading.visibility = View.GONE
    }

    companion object {

        fun newInstance(): MainFragment {
            return MainFragment()
        }
    }
}
