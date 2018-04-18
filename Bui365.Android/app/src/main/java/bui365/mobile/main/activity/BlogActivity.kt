package bui365.mobile.main.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import bui365.mobile.main.R
import bui365.mobile.main.fragment.BlogFragment
import bui365.mobile.main.presenter.impl.BlogPresenterImpl
import bui365.mobile.main.util.addFragmentToActivity
import kotlinx.android.synthetic.main.activity_blog.*

class BlogActivity : AppCompatActivity() {

    private lateinit var blogPresenterImpl: BlogPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blog)

        setSupportActionBar(toolbar)
        title = getString(R.string.title_blog)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val handbookFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as BlogFragment? ?: BlogFragment.newInstance().also {
            addFragmentToActivity(it, R.id.contentFrame)
        }

        blogPresenterImpl = BlogPresenterImpl(handbookFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
