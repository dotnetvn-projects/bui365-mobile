package bui365.mobile.main.fragment


import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.util.Log
import android.view.*
import bui365.mobile.main.R
import bui365.mobile.main.adapter.SearchArticleAdapter
import bui365.mobile.main.contract.SearchArticleContract
import bui365.mobile.main.graphic.GridSpacingItemDecoration
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.util.Utils
import kotlinx.android.synthetic.main.fragment_search_article.*
import kotlinx.android.synthetic.main.layout_error_loading.*
import kotlinx.android.synthetic.main.layout_progress_loading.*


class SearchArticleFragment : Fragment(), SearchArticleContract.View {

    override lateinit var presenter: SearchArticleContract.Presenter
    private var articles: ArrayList<Article> = ArrayList()
    private lateinit var searchAdapter: SearchArticleAdapter
    private lateinit var layoutManager: LinearLayoutManager

    companion object {
        const val ITEM_COLUMN = 2

        fun newInstance(): SearchArticleFragment {
            return SearchArticleFragment()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_search_article, container, false)
        searchAdapter = SearchArticleAdapter(activity!!, articles)
        setHasOptionsMenu(true)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemColumns = Utils.calculateNoOfColumns(activity!!)
        val spacing = activity!!.resources.getDimensionPixelSize(R.dimen.activity_horizontal_margin)
        layoutManager = LinearLayoutManager(activity)
        rcvSearchArticle.layoutManager = layoutManager
//        rcvSearchArticle.addItemDecoration(GridSpacingItemDecoration(itemColumns, spacing, true))
        rcvSearchArticle.adapter = searchAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater!!.inflate(R.menu.menu_search, menu)

        val searchManager: SearchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView: SearchView = menu!!.findItem(R.id.actionSearch).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(activity!!.componentName))
        searchView.queryHint = getString(R.string.txtInputTitle)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query!! != "") {
                    presenter.getArticles(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!! != "") {
                    presenter.getArticles(newText)
                }
                return true
            }

        })
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.actionSearch -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun showArticles(articles: List<Article>) {
        Log.e("kyo", "showArticles: $articles")
        //clear old search result before display new result
        this.articles.clear()
        searchAdapter.notifyDataSetChanged()

        //add new result to the list
        this.articles.addAll(articles)
        searchAdapter.notifyDataSetChanged()
    }

    override fun showError() {
        txtErrorLoading.visibility = View.VISIBLE
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
}
