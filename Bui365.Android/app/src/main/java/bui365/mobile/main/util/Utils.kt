package bui365.mobile.main.util


import android.content.Context
import android.text.Html
import android.text.Spanned
import android.widget.TextView

object Utils {
    fun fromHtml(context: Context, html: String, container: TextView): Spanned {
        val result: Spanned
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY, UrlImageParser(container, context), HtmlTagHandler())
        } else {
            result = Html.fromHtml(html, UrlImageParser(container, context), HtmlTagHandler())
        }
        return result
    }
}
