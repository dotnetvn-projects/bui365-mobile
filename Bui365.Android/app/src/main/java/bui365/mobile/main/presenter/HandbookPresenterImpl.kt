package bui365.mobile.main.presenter

import android.util.Log
import bui365.mobile.main.business.HandbookBusiness
import bui365.mobile.main.contract.HandbookContract
import com.google.common.base.Preconditions.checkNotNull
import io.reactivex.disposables.CompositeDisposable

class HandbookPresenterImpl(handbookView: HandbookContract.View) : HandbookContract.Presenter {
    private val handbookView: HandbookContract.View = checkNotNull(handbookView, "blogView cannot be null")
    private val handbookBusiness = HandbookBusiness()
    private val compositeDisposable: CompositeDisposable
    private var isFirstLoad = false

    init {
        this.handbookView.presenter = this
        compositeDisposable = CompositeDisposable()
    }

    override fun getHandbookArticles(forceUpdate: Boolean, start: Int) {
        loadHandbookArticles(forceUpdate || isFirstLoad, true, start)
        isFirstLoad = false
    }

    /**
     * @param forceUpdate   Pass in true to refresh data
     * @param showLoadingUi Pass in true to display loading icon
     * @param index         Pass index to load more item
     */
    private fun loadHandbookArticles(forceUpdate: Boolean, showLoadingUi: Boolean, start: Int) {
        if (showLoadingUi) {
            handbookView.showLoading()
        }
        if (forceUpdate) {
            //refresh method
        }

        compositeDisposable.clear()
        val disposable = handbookBusiness.getHandbookArticles(start)
                .subscribe({ articles ->
                    var loadEnd = false
                    if (articles.isEmpty()) {
                        loadEnd = true
                    }
                    handbookView.hideLoading()
                    handbookView.hideError()
                    handbookView.showHandbookArticle(articles, loadEnd)
                }, { throwable ->
                    Log.e("handbookView", "error: " + throwable.message)
                    handbookView.hideLoading()
                    handbookView.showError()
                })
        compositeDisposable.add(disposable)
    }

    override fun subscribe() {
        getHandbookArticles(false, 0)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}