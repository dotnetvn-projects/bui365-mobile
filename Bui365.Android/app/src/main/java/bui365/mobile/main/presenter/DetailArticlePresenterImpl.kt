package bui365.mobile.main.presenter


import android.util.Log
import bui365.mobile.main.business.DetailArticleBusiness
import bui365.mobile.main.contract.DetailArticleContract
import com.google.common.base.Preconditions.checkNotNull
import io.reactivex.disposables.CompositeDisposable

class DetailArticlePresenterImpl(detailArticleView: DetailArticleContract.View) :
        DetailArticleContract.Presenter {
    private val detailArticleView: DetailArticleContract.View =
            checkNotNull(detailArticleView, "Detail Article View cannot null")
    private val detailArticleBusiness: DetailArticleBusiness = DetailArticleBusiness()
    private val compositeDisposable: CompositeDisposable

    init {
        this.detailArticleView.presenter = this
        compositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {

    }

    /**
     * @param articleId pass Article Id to call Detail Request
     */
    override fun loadDetailArticle(articleId: String) {

        detailArticleView.showLoading()

        //using retrofit and rx java 2 to get detail article
        compositeDisposable.clear()
        val disposable = detailArticleBusiness.getDetailArticle(articleId)
                .subscribe({ article ->
                    Log.e("kyo", "subscribe: $article")
                    detailArticleView.hideLoading()
                    detailArticleView.hideError()
                    detailArticleView.showResult(article)
                }, { throwable ->
                    Log.e("kyo", "throwable: " + throwable.message)
                    detailArticleView.hideLoading()
                    detailArticleView.showError()
                })

        compositeDisposable.add(disposable)
    }


    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}
