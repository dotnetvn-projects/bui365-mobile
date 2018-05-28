package bui365.mobile.main.presenter

import android.util.Log
import bui365.mobile.main.api.Bui365Api
import bui365.mobile.main.contract.SearchArticleContract
import com.google.common.base.Preconditions
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchArticlePresenterImpl(searchView: SearchArticleContract.View) : SearchArticleContract.Presenter {

    private val searchView = Preconditions.checkNotNull(searchView, "blogView cannot be null")
    private val bui365Api by lazy {
        Bui365Api.create()
    }

    init {
        this.searchView.presenter = this
    }

    override fun getArticles(search: String) {
        Log.e("kyo", "getArticles: $search")
        searchView.showLoading()
        bui365Api.searchArticles(search).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ articles ->
                    searchView.hideLoading()
                    searchView.hideError()
                    searchView.showArticles(articles)
                }, { throwable ->
                    searchView.hideLoading()
                    searchView.showError()
                    Log.e("kyo", "getArticles: " + throwable.message)
                })
    }

    override fun start() {
        searchView.hideError()
    }
}