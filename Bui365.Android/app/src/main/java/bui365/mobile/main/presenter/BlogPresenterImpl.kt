package bui365.mobile.main.presenter


import android.net.Uri
import android.util.Log
import bui365.mobile.main.business.BlogBusiness
import bui365.mobile.main.contract.BlogContract
import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.model.pojo.Article
import bui365.mobile.main.model.pojo.FacebookPOJO
import bui365.mobile.main.request.FacebookUrlRequest
import com.facebook.share.model.ShareHashtag
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.google.common.base.Preconditions.checkNotNull
import io.reactivex.disposables.CompositeDisposable

class BlogPresenterImpl(blogView: BlogContract.View) : BlogContract.Presenter {
    private val blogView: BlogContract.View = checkNotNull(blogView, "blogView cannot be null")
    private val blogBusiness: BlogBusiness = BlogBusiness()
    private var articles: ArrayList<Article> = ArrayList()
    private var facebookPOJO: FacebookPOJO = FacebookPOJO()
    private val compositeDisposable: CompositeDisposable

    private var isFirstLoad = true

    init {
        this.blogView.presenter = this
        compositeDisposable = CompositeDisposable()
    }

    override fun subscribe() {
        loadTask(false, 0)
    }

    override fun loadTask(forceUpdate: Boolean, start: Int) {
        loadTask(forceUpdate || isFirstLoad, true, start)
        isFirstLoad = false
    }

    override fun loadFacebookSdk(url: String) {
        FacebookUrlRequest(object : AsyncTaskListener<String> {
            override fun onTaskPreExecute() {

            }

            override fun onTaskComplete(result: Any) {
                Log.e("kyo", "onTaskComplete: $result")
                if (result != "") {
                    facebookPOJO = blogBusiness.handleFacebookSdk(result)
                    blogView.showFacebookSdk(facebookPOJO)
                }
            }

        }, url).execute()
    }

    override fun shareArticle(url: String) {
        if (ShareDialog.canShow(ShareLinkContent::class.java)) {
            val content = ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(url))
                    .setShareHashtag(ShareHashtag.Builder().setHashtag("#Bui365").build())
                    .build()
            blogView.showShareArticle(content)
        }
    }

    override fun loadComment(url: String) {
        blogView.showComment(url)
    }

    override fun loadDetailArticle(id: String) {
        blogView.showDetailArticle(id)
    }

    /**
     * @param forceUpdate   Pass in true to refresh data
     * @param showLoadingUi Pass in true to display loading icon
     * @param index         Pass index to load more item
     */
    private fun loadTask(forceUpdate: Boolean, showLoadingUi: Boolean, index: Int) {
        if (showLoadingUi) {
            blogView.showLoading()
        }
        if (forceUpdate) {
            //refresh method
        }

        //using retrofit with rx java 2 to get list of article
        compositeDisposable.clear()
        val disposable = blogBusiness.getArticles(index)
                .subscribe({ articles ->
                    Log.e("blog", "subscribe: $articles")
                    var loadEnd = false
                    if (articles.isEmpty()) {
                        loadEnd = true
                    }
                    blogView.hideLoading()
                    blogView.hideError()
                    blogView.showResult(articles, loadEnd)
                }, { throwable ->
                    Log.e("blog", "error: " + throwable.message)
                    blogView.hideLoading()
                    blogView.showError()
                })

        compositeDisposable.add(disposable)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }
}
