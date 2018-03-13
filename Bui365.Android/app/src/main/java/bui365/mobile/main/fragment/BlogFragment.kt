package bui365.mobile.main.fragment


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bui365.mobile.main.R
import bui365.mobile.main.adapter.BlogArticleAdapter
import bui365.mobile.main.model.Article
import bui365.mobile.main.presenter.BlogPresenter
import bui365.mobile.main.view.BlogView
import java.util.*


class BlogFragment : Fragment(), BlogView {

    private var mBlogPresenter: BlogPresenter? = null
    private var rcvBlogArticle: RecyclerView? = null
    private var articleList: ArrayList<Article>? = null
    private var layoutManager: LinearLayoutManager? = null
    private var articleAdapter: BlogArticleAdapter? = null

    private val start = 0
    protected var handler: Handler? = null

    override lateinit var presenter: BlogPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_blog, container, false)
        articleList = ArrayList()
        rcvBlogArticle = root.findViewById(R.id.rcvBlogArticle)
        layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        rcvBlogArticle!!.layoutManager = layoutManager
        articleAdapter = BlogArticleAdapter(activity!!, articleList!!)
        rcvBlogArticle!!.adapter = articleAdapter
        setHasOptionsMenu(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        mBlogPresenter!!.start()
    }

    override fun showResult(result: Any) {

    }

    override fun showError() {

    }

    override fun hideError() {

    }

    override fun showEmptyText() {

    }

    override fun hideEmptyText() {

    }

    override fun showLoading() {

    }

    override fun hideLoading() {

    }

    companion object {

        fun newInstance(blogPresenter: BlogPresenter): BlogFragment {
            val blogFragment = BlogFragment()
            blogFragment.mBlogPresenter = blogPresenter
            return blogFragment
        }
    }
}// Required empty public constructor
