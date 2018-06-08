package bui365.mobile.main.business

import bui365.mobile.main.api.Bui365Api
import bui365.mobile.main.model.pojo.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HandbookBusiness {
    private val bui365Api by lazy {
        Bui365Api.create()
    }

    fun getHandbookArticles(start: Int): Observable<List<Article>> {
        return bui365Api.getHandbookArticle(start, 5, "1KvVu73XIio\$").observeOn(Schedulers.io())
                .subscribeOn(AndroidSchedulers.mainThread())
    }
}