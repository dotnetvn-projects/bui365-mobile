package bui365.mobile.main.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import bui365.mobile.main.R
import bui365.mobile.main.business.HandbookDetailArticleBusiness
import bui365.mobile.main.fragment.HandbookDetailArticleFragment
import bui365.mobile.main.util.addFragmentToActivity

class HandbookDetailArticleActivity : AppCompatActivity() {

    private lateinit var mDetailArticleBusiness: HandbookDetailArticleBusiness

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handbook_detail_article)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
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
        mDetailArticleBusiness = HandbookDetailArticleBusiness(handbookDetailArticleFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
