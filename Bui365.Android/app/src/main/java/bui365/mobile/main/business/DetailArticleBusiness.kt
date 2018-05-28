package bui365.mobile.main.business

import bui365.mobile.main.api.Bui365Api
import bui365.mobile.main.model.pojo.Article
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class DetailArticleBusiness {
    private val bui365Api by lazy {
        Bui365Api.create()
    }

    fun handleData(result: Any): Boolean {
        return result != ""
    }

    fun getDetailArticle(id: String): Observable<Article> {
        return bui365Api.getDetailArticle(id).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
    }
}
