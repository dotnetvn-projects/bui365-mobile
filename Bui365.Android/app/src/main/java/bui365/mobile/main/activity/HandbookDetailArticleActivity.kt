package bui365.mobile.main.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bui365.mobile.main.R
import bui365.mobile.main.fragment.HandbookDetailArticleFragment
import bui365.mobile.main.presenter.impl.HandbookDetailArticlePresenterImpl
import bui365.mobile.main.util.addFragmentToActivity
import kotlinx.android.synthetic.main.activity_blog_detail_article.*

class HandbookDetailArticleActivity : AppCompatActivity() {

    private lateinit var detailArticlePresenterImpl: HandbookDetailArticlePresenterImpl

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
                as HandbookDetailArticleFragment?
                ?: HandbookDetailArticleFragment.newInstance(articleId).also {
                    addFragmentToActivity(it, R.id.contentFrame)
                }
        detailArticlePresenterImpl = HandbookDetailArticlePresenterImpl(handbookDetailArticleFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
