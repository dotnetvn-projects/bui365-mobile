package bui365.mobile.main.request


import android.os.AsyncTask
import android.util.Log

import java.net.URL

import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.util.Config
import bui365.mobile.main.util.Connection

class HandbookArticleRequest(private val mListener: AsyncTaskListener<String>?, private val mStart: Int) : AsyncTask<String, Void, Any>() {

    override fun doInBackground(vararg params: String): String {
        var json = ""
        try {
            val url = Connection.createUrl(Config.BUI_SERVER + "get-articles?start=" + mStart + "&take=5")
            Log.e("kyo", "doInBackground: " + url!!)
            json = Connection.makeHttpRequest(url)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return json
    }

    override fun onPreExecute() {
        super.onPreExecute()
        mListener!!.onTaskPreExecute()
    }

    override fun onPostExecute(s: Any) {
        super.onPostExecute(s)
        if (mListener == null) {
            return
        }
        mListener.onTaskComplete(s)
    }
}
