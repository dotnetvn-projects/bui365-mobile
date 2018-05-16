package bui365.mobile.main.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.app.AppCompatDelegate
import bui365.mobile.main.R
import bui365.mobile.main.fragment.MainFragment
import bui365.mobile.main.presenter.impl.MainPresenterImpl
import bui365.mobile.main.util.addFragmentToActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    init {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    }

    private lateinit var mainPresenterImpl: MainPresenterImpl

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        title = ""
        val mainActivityFragment = supportFragmentManager.findFragmentById(R.id.contentFrame)
                as MainFragment? ?: MainFragment.newInstance().also {
            addFragmentToActivity(it, R.id.contentFrame)
        }

        mainPresenterImpl = MainPresenterImpl(mainActivityFragment)

    }
}
