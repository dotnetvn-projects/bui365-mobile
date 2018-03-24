package bui365.mobile.main.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import bui365.mobile.main.R
import bui365.mobile.main.presenter.impl.BlogPresenterImpl
import bui365.mobile.main.fragment.BlogFragment
import bui365.mobile.main.util.addFragmentToActivity

class BlogActivity : AppCompatActivity() {

    private lateinit var mBlogPresenterImpl: BlogPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_handbook)

        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        title = getString(R.string.title_handbook)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val handbookFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as BlogFragment? ?: BlogFragment.newInstance().also {
            addFragmentToActivity(it, R.id.contentFrame)
        }

        mBlogPresenterImpl = BlogPresenterImpl(handbookFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
