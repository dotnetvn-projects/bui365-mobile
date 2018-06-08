package bui365.mobile.main.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import bui365.mobile.main.R
import bui365.mobile.main.contract.HandbookContract
import bui365.mobile.main.model.pojo.Article


class HandbookFragment : Fragment(), HandbookContract.View {

    override lateinit var presenter: HandbookContract.Presenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_handbook, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.subscribe()
    }

    override fun showHandbookArticle(articles: List<Article>, loadEnd: Boolean) {
    }

    override fun showError() {
    }

    override fun hideError() {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    companion object {

        fun newInstance(): HandbookFragment {
            return HandbookFragment()
        }

    }
}
