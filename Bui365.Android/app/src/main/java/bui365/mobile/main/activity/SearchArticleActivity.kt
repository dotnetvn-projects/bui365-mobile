package bui365.mobile.main.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bui365.mobile.main.R
import bui365.mobile.main.fragment.SearchArticleFragment
import bui365.mobile.main.presenter.SearchArticlePresenterImpl
import bui365.mobile.main.util.addFragmentToActivity
import kotlinx.android.synthetic.main.activity_search_article.*

class SearchArticleActivity : AppCompatActivity() {

    private lateinit var searchPresenterImpl: SearchArticlePresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_article)

        setSupportActionBar(toolbar)
        setTitle(R.string.txtSearch)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        //initialize searchFragment then add it to the activity
        val searchFragment: SearchArticleFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as SearchArticleFragment? ?: SearchArticleFragment.newInstance().also {
            addFragmentToActivity(it, R.id.contentFrame)
        }

        searchPresenterImpl = SearchArticlePresenterImpl(searchFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
