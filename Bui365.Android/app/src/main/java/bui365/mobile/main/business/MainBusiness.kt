package bui365.mobile.main.business

import bui365.mobile.main.api.Bui365Api
import bui365.mobile.main.model.pojo.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class MainBusiness {
    private val bui365Api by lazy {
        Bui365Api.create()
    }

    fun getArticles(): Observable<List<Article>> {
        return bui365Api.getBlogArticles(0, 5).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
