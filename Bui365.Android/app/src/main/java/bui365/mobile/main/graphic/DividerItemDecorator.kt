package bui365.mobile.main.graphic

import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v7.widget.RecyclerView
import android.view.View

public class DividerItemDecorator(val divider: Drawable) : RecyclerView.ItemDecoration() {
    override fun onDraw(c: Canvas?, parent: RecyclerView?, state: RecyclerView.State?) {
        val dividerLeft = parent!!.paddingLeft
        val dividerRight = parent.width - parent.paddingRight

        val childCount = parent.childCount
        for (i in 0 until childCount - 1) {
            val child = parent.getChildAt(i)

            val params = child.layoutParams as RecyclerView.LayoutParams

            val dividerTop = child.bottom + params.bottomMargin
            val dividerBottom = dividerTop + divider.intrinsicHeight

            divider.setBounds(dividerLeft, dividerTop, dividerRight, dividerBottom)
            divider.draw(c)
        }
    }

    override fun getItemOffsets(outRect: Rect?, view: View?, parent: RecyclerView?, state: RecyclerView.State?) {
        super.getItemOffsets(outRect, view, parent, state)
        if (parent!!.getChildAdapterPosition(view) == 0) {
            return;
        }

        outRect!!.top = divider.intrinsicHeight;
    }
}