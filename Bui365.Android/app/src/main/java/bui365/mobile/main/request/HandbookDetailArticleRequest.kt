package bui365.mobile.main.request


import android.os.AsyncTask
import android.util.Log

import java.net.URL

import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.util.Config
import bui365.mobile.main.util.Connection

class HandbookDetailArticleRequest(private val mListener: AsyncTaskListener<*>?, private val id: String) : AsyncTask<String, Void, Any>() {

    override fun onPreExecute() {
        super.onPreExecute()
        mListener!!.onTaskPreExecute()
    }

    override fun doInBackground(vararg strings: String): Any {
        var json = ""
        try {
            val url = Connection.createUrl(Config.BUI_SERVER + "get-article-detail?id=" + id)
            Log.e("kyo", "doInBackground: " + url!!)
            json = Connection.makeHttpRequest(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return json
    }

    override fun onPostExecute(o: Any) {
        super.onPostExecute(o)
        if (mListener == null) {
            return
        }
        mListener.onTaskComplete(o)
    }
}
