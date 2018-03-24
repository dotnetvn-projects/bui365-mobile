package bui365.mobile.main.presenter.impl


import android.net.Uri
import bui365.mobile.main.business.BlogBusiness
import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.presenter.BlogPresenter
import bui365.mobile.main.request.BlogArticleRequest
import bui365.mobile.main.view.BlogView
import com.facebook.share.model.ShareHashtag
import com.facebook.share.model.ShareLinkContent
import com.facebook.share.widget.ShareDialog
import com.google.common.base.Preconditions.checkNotNull

class BlogPresenterImpl(blogView: BlogView) : BlogPresenter {
    private val mBlogView: BlogView = checkNotNull(blogView, "blogView cannot be null")
    private val mBlogBusiness: BlogBusiness = BlogBusiness()

    private var mFirstLoad = true

    init {
        mBlogView.presenter = this
    }

    override fun start() {
        loadTask(false, 0)
    }

    override fun loadTask(forceUpdate: Boolean, index: Int) {
        loadTask(forceUpdate || mFirstLoad, true, index)
        mFirstLoad = false
    }

    override fun shareArticle(url: String) {
        if (ShareDialog.canShow(ShareLinkContent::class.java)) {
            val content = ShareLinkContent.Builder()
                    .setContentUrl(Uri.parse(url))
                    .setShareHashtag(ShareHashtag.Builder().setHashtag("#Bui365").build())
                    .build()
            mBlogView.showShareArticle(content)
        }
    }

    override fun loadComment(url: String) {
        mBlogView.showComment(url)
    }

    override fun loadDetailArticle(id: String) {
        mBlogView.showDetailArticle(id)
    }

    /**
     * @param forceUpdate   Pass in true to refresh data
     * @param showLoadingUi Pass in true to display loading icon
     * @param index         Pass index to load more item
     */
    private fun loadTask(forceUpdate: Boolean, showLoadingUi: Boolean, index: Int) {
        if (showLoadingUi) {
            mBlogView.showLoading()
        }
        if (forceUpdate) {
            //refresh method
        }

        BlogArticleRequest(object : AsyncTaskListener<String> {
            override fun onTaskPreExecute() {
                mBlogView.showLoading()
            }

            override fun onTaskComplete(result: Any) {
                mBlogView.hideLoading()
                if (mBlogBusiness.handleData(result)) {
                    mBlogView.hideError()
                    mBlogView.showResult(result)
                } else {
                    mBlogView.showError()
                }
            }

        }, index).execute()

    }
}
