package bui365.mobile.main.presenter

import android.util.Log
import bui365.mobile.main.api.Bui365Api
import bui365.mobile.main.business.SearchArticleBusiness
import bui365.mobile.main.contract.SearchArticleContract
import com.google.common.base.Preconditions
import io.reactivex.disposables.CompositeDisposable

class SearchArticlePresenterImpl(searchView: SearchArticleContract.View) : SearchArticleContract.Presenter {

    private val searchView = Preconditions.checkNotNull(searchView, "blogView cannot be null")
    private val searchArticleBusiness = SearchArticleBusiness()
    private val compositeDisposable: CompositeDisposable

    init {
        this.searchView.presenter = this
        compositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        searchView.hideError()
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    override fun getArticles(search: String) {
        searchView.showLoading()

        //using retrofit and rx java 2 to get search result
        compositeDisposable.clear()
        val disposable = searchArticleBusiness.searchArticles(search)
                .subscribe({ articles ->
                    searchView.hideLoading()
                    searchView.hideError()
                    searchView.showArticles(articles)
                }, { throwable ->
                    searchView.hideLoading()
                    searchView.showError()
                    Log.e("kyo", "getArticles: " + throwable.message)
                })

        compositeDisposable.add(disposable)
    }
}