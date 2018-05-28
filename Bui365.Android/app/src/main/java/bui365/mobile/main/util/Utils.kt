package bui365.mobile.main.util


import android.content.Context
import android.os.Build
import android.text.Html
import android.text.Spanned
import android.widget.TextView


class Utils {
    companion object {
        fun calculateNoOfColumns(context: Context): Int {
            val displayMetrics = context.resources.displayMetrics
            val dpWidth = displayMetrics.widthPixels / displayMetrics.density
            return (dpWidth / 180).toInt()
        }
    }

    fun fromHtml(context: Context, html: String, container: TextView): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, UrlImageParser(container, context), HtmlTagHandler())
        } else {
            Html.fromHtml(html, UrlImageParser(container, context), HtmlTagHandler())
        }
    }


}
