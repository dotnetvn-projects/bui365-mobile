package bui365.mobile.main.graphic

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

class GridSpacingItemDecoration(val spanCount: Int, private val spacing: Int, private val includeEdge: Boolean) : RecyclerView.ItemDecoration() {
    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        val position = parent!!.getChildAdapterPosition(view) //item position
        val column = position % spanCount // item column

        if (includeEdge) {
            outRect!!.left = spacing - column * spacing / spanCount
            outRect.right = (column + 1) * spacing / spanCount

            if (position < spanCount) { //top edge
                outRect.top = spacing
            }
            outRect.bottom = spacing //item bottom
        } else {
            outRect!!.left = column * spacing / spanCount
            outRect.right = spacing - (column + 1) * spacing / spanCount
            if (position >= spanCount) {
                outRect.top = spacing //item top
            }
        }
    }
}