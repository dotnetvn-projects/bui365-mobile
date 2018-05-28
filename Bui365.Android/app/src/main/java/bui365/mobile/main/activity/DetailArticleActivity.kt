package bui365.mobile.main.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bui365.mobile.main.R
import bui365.mobile.main.fragment.DetailArticleFragment
import bui365.mobile.main.presenter.DetailArticlePresenterImpl
import bui365.mobile.main.util.addFragmentToActivity
import kotlinx.android.synthetic.main.activity_blog_detail_article.*

class DetailArticleActivity : AppCompatActivity() {

    private lateinit var detailArticlePresenterImpl: DetailArticlePresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog_detail_article)

        setSupportActionBar(toolbar)
        title = ""
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val articleId = intent.getStringExtra("articleId")
        val handbookDetailArticleFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as DetailArticleFragment?
                ?: DetailArticleFragment.newInstance(articleId).also {
                    addFragmentToActivity(it, R.id.contentFrame)
                }
        detailArticlePresenterImpl = DetailArticlePresenterImpl(handbookDetailArticleFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
