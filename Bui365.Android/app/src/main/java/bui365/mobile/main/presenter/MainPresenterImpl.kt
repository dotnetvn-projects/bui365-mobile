package bui365.mobile.main.presenter


import android.util.Log
import bui365.mobile.main.business.MainBusiness
import bui365.mobile.main.contract.MainContract
import com.google.common.base.Preconditions.checkNotNull
import io.reactivex.disposables.CompositeDisposable

class MainPresenterImpl(mainActivityView: MainContract.View) : MainContract.Presenter {

    private val mainActivityView: MainContract.View = checkNotNull(mainActivityView, "mainActivityView cannot be null")
    private val mainBusiness: MainBusiness = MainBusiness()
    private var isFirstLoad = true
    private val compositeDisposable: CompositeDisposable

    init {
        this.mainActivityView.presenter = this
        compositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        loadTask(false)
    }

    override fun loadTask(forceUpdate: Boolean) {
        loadTask(forceUpdate || isFirstLoad, true)
        isFirstLoad = false
    }

    /**
     * @param forceUpdate   Pass in true to refresh data
     * @param showLoadingUi Pass in true to display loading icon
     */
    private fun loadTask(forceUpdate: Boolean, showLoadingUi: Boolean) {
        if (showLoadingUi) {
            mainActivityView.showLoading()
        }
        if (forceUpdate) {
            //refresh method
        }

        //using retrofit and rx java 2 to call random image api
        compositeDisposable.clear()
        val disposable = mainBusiness.getArticles().subscribe(
                { articles ->
                    mainActivityView.hideLoading()
                    mainActivityView.hideError()
                    mainActivityView.showResult(articles)
                    Log.e("kyo", "Success:" + articles.toString())
                },
                { throwable ->
                    mainActivityView.hideLoading()
                    mainActivityView.showError()
                    Log.e("kyo", "error:" + throwable.message)
                })

        compositeDisposable.add(disposable)
    }

    override fun openTaskDetails(id: Int) {
        mainActivityView.showTaskDetailUi(id)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}
