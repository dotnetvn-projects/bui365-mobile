package bui365.mobile.main.util

import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable


class UrlDrawable : BitmapDrawable() {
    // the drawable that you need to set, you could set the initial drawing
    // with the loading image if you need to
    var drawable: Drawable? = null

    override fun draw(canvas: Canvas) {
        // override the draw to facilitate refresh function later
        if (drawable != null) {
            drawable!!.draw(canvas)
        }
    }
}
