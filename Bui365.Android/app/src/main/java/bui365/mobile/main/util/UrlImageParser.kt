package bui365.mobile.main.util


import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.text.Html
import android.util.Base64
import android.view.Gravity
import android.widget.TextView
import java.io.InputStream
import java.net.URL

class UrlImageParser(private val container: TextView, private val context: Context) : Html.ImageGetter {

    override fun getDrawable(source: String): Drawable {
        return if (source.matches("data:image.*base64.*".toRegex())) {
            val base64Source = source.replace("data:image.*base64".toRegex(), "")
            val data = Base64.decode(base64Source, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(data, 0, data.size)
            val image = BitmapDrawable(context.resources, bitmap)
            image.setBounds(0, 0, image.intrinsicWidth, image.intrinsicHeight)
            image.gravity = Gravity.TOP
            image
        } else {
            val urlDrawable = UrlDrawable()
            val asyncTask = ImageGetterAsyncTask(urlDrawable)
            asyncTask.execute(source)
            urlDrawable //return reference to URLDrawable where We will change with actual image from the src tag
        }
    }

    private inner class ImageGetterAsyncTask internal constructor(internal var urlDrawable: UrlDrawable) : AsyncTask<String, Void, Drawable>() {
        private var scale: Float = 0.toFloat()

        override fun doInBackground(vararg params: String): Drawable? {
            val source = params[0]
            return fetchDrawable(source)
        }

        override fun onPostExecute(result: Drawable) {
            urlDrawable.setBounds(0, 0, (urlDrawable.intrinsicWidth * scale).toInt(), (urlDrawable.intrinsicHeight * scale).toInt()) //set the correct bound according to the result from HTTP call
            urlDrawable.drawable = result //change the reference of the current drawable to the result from the HTTP call
            this@UrlImageParser.container.invalidate() //redraw the image by invalidating the container
            container.text = container.text
        }

        internal fun fetchDrawable(urlString: String): Drawable? {
            return try {
                val `is` = URL(urlString).content as InputStream
                val drawable = Drawable.createFromStream(`is`, "src")
                scale = getScale(drawable)
                drawable.setBounds(0, 0, (drawable.intrinsicWidth * scale).toInt(), (drawable.intrinsicHeight * scale).toInt())
                drawable
            } catch (e: Exception) {
                null
            }

        }

        private fun getScale(drawable: Drawable): Float {
            val maxWidth = container.width.toFloat()
            val originalDrawableWidth = drawable.intrinsicWidth.toFloat()
            return maxWidth / originalDrawableWidth
        }
    }
}
