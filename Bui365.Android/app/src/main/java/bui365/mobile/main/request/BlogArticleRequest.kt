package bui365.mobile.main.request


import android.os.AsyncTask

import java.net.URL

import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.util.Config
import bui365.mobile.main.util.Connection

class BlogArticleRequest(private val mListener: AsyncTaskListener<String>?, private val mStart: Int) : AsyncTask<String, Void, Any>() {

    override fun onPreExecute() {
        super.onPreExecute()
        mListener!!.onTaskPreExecute()
    }

    override fun doInBackground(vararg strings: String): String {
        var json = ""
        try {
            val url = Connection.createUrl(Config.BUI_SERVER + "get-articles?start=" + 0 + "&take=5")
            json = Connection.makeHttpRequest(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return json
    }

    override fun onPostExecute(s: Any) {
        super.onPostExecute(s)
        if (mListener == null) {
            return
        }
        mListener.onTaskComplete(s)

    }
}
