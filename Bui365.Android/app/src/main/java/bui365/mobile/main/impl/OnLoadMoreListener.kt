package bui365.mobile.main.impl

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

/**
 * Created by ASUS on 2/28/2018.
 */

abstract class OnLoadMoreListener(internal var layoutManager: LinearLayoutManager) : RecyclerView.OnScrollListener() {
    private val visibleThreshold = 2

    abstract val isLastPage: Boolean

    abstract val isLoading: Boolean

    override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
        super.onScrolled(recyclerView, dx, dy)

        val visibleItem = layoutManager.childCount
        val totalItemCount = layoutManager.itemCount
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()

        if (dy > 0 && !isLoading && !isLastPage
                && totalItemCount + visibleItem <= firstVisibleItemPosition + visibleThreshold) {
            loadMoreItem()
        }
    }

    protected abstract fun loadMoreItem()
}
