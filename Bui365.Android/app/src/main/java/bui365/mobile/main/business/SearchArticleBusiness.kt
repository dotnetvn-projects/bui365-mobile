package bui365.mobile.main.business

import bui365.mobile.main.api.Bui365Api
import bui365.mobile.main.model.pojo.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SearchArticleBusiness {
    private val bui365Api by lazy {
        Bui365Api.create()
    }

    fun searchArticles(search: String): Observable<List<Article>> {
        return bui365Api.searchArticles(search).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}