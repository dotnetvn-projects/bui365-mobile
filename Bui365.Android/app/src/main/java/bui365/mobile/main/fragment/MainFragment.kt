package bui365.mobile.main.fragment


import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.util.DisplayMetrics
import android.util.Log
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import bui365.mobile.main.R
import bui365.mobile.main.activity.BlogActivity
import bui365.mobile.main.activity.DetailArticleActivity
import bui365.mobile.main.adapter.MainSlidingAdapter
import bui365.mobile.main.contract.MainContract
import bui365.mobile.main.databinding.FragmentMainActivityBinding
import bui365.mobile.main.impl.MainItemListener
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.util.showSnackBarAction
import kotlinx.android.synthetic.main.fragment_main_activity.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.util.*
import kotlin.collections.ArrayList


class MainFragment : Fragment(), MainContract.View {

    override lateinit var presenter: MainContract.Presenter
    private lateinit var viewPager: ViewPager
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
            val detailArticle = Intent(activity, DetailArticleActivity::class.java)
            detailArticle.putExtra("articleId", article.id)
            startActivity(detailArticle)
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

//    override fun onPause() {
//        super.onPause()
//        //call presenter.unsubscribe to avoid leak context
//    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding: FragmentMainActivityBinding =
                DataBindingUtil.inflate(inflater, R.layout.fragment_main_activity, container, false)
        val root = binding.root
//        val root = inflater.inflate(R.layout.fragment_main_activity, container, false)
        with(root) {
            metrics = resources.displayMetrics
            if (activity != null) {
                viewPager = activity!!.findViewById(R.id.viewPager)
                txtErrorLoading = activity!!.findViewById(R.id.txtErrorLoading)
                progressBar = activity!!.findViewById(R.id.progressBar)
            }
        }
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnHandbook.setOnClickListener { presenter.openTaskDetails(it.id) }
        btnBlog.setOnClickListener { presenter.openTaskDetails(it.id) }
        btnFavorite.setOnClickListener { }

        viewPager.layoutParams.width = metrics.widthPixels
        viewPager.layoutParams.height = metrics.heightPixels / 2

        slidingAdapter = MainSlidingAdapter(activity!!, articles, mainItemListener)
        viewPager.adapter = slidingAdapter

        imgLogo.layoutParams.width = metrics.widthPixels / 2

        val handler = Handler()
        val update = Runnable {
            if (currentPage == articles.size) {
                currentPage = 0
            }
            viewPager.setCurrentItem(currentPage++, true)
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
            R.id.btnBlog -> {
                val intent = Intent(activity, BlogActivity::class.java)
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
                        setAction(getString(R.string.retry)) {
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
