package bui365.mobile.main.business


import bui365.mobile.main.presenter.BlogPresenter
import bui365.mobile.main.view.BlogView

import com.google.common.base.Preconditions.checkNotNull

class BlogBusiness(blogView: BlogView) : BlogPresenter {
    private val mBlogView: BlogView = checkNotNull(blogView, "Blog View cannot be null")

    private val mFirstLoad = true

    init {
        mBlogView.presenter = this
    }

    override fun start() {
        loadTask(false, 0)
    }

    override fun loadTask(forceUpdate: Boolean, index: Int) {
        loadTask(forceUpdate || mFirstLoad, true, index)
    }

    /**
     * @param forceUpdate   Pass in true to refresh data
     * @param showLoadingUi Pass in true to display loading icon
     * @param index         Pass index to load more item
     */
    private fun loadTask(forceUpdate: Boolean, showLoadingUi: Boolean, index: Int) {
        if (showLoadingUi) {
            //            mBlogView.setLoadingIndicator(true);
            //set loading progress
        }
        if (forceUpdate) {
            //refresh method
        }
    }
}
