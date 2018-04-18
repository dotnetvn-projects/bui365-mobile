package bui365.mobile.main.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import bui365.mobile.main.R
import bui365.mobile.main.fragment.CommentsFragment
import bui365.mobile.main.presenter.impl.CommentsPresenterImpl
import bui365.mobile.main.util.addFragmentToActivity
import kotlinx.android.synthetic.main.activity_comments.*

class CommentsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comments)

        setSupportActionBar(toolbar)
        title = getString(R.string.txtComments)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        }

        val url = intent.getStringExtra("url")

        val commentsFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as CommentsFragment? ?: CommentsFragment.newInstance(url).also {
            addFragmentToActivity(it, R.id.contentFrame)
        }

        CommentsPresenterImpl(commentsFragment)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
