package bui365.mobile.main.request

import android.os.AsyncTask
import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.util.Connection


class FacebookUrlRequest(private val listener: AsyncTaskListener<String>?, private val articleUrl: String) : AsyncTask<String, Void, Any>() {
    override fun doInBackground(vararg p0: String?): Any {
        var json = ""
        try {
            val url = Connection.createUrl("https://graph.facebook.com/" +
                    "?fields=og_object{likes.summary(true).limit(0)}%2Cshare&id=" + articleUrl)
            json = Connection.makeHttpRequest(url)
        } catch (e: Exception) {

        }
        return json
    }

    override fun onPreExecute() {
        super.onPreExecute()
        listener!!.onTaskPreExecute()
    }

    override fun onPostExecute(s: Any) {
        super.onPostExecute(s)
        if (listener == null) {
            return
        }
        listener.onTaskComplete(s)
    }
}