package bui365.mobile.main.request


import android.os.AsyncTask
import bui365.mobile.main.impl.AsyncTaskListener
import bui365.mobile.main.util.Config
import bui365.mobile.main.util.Connection

class BlogArticleRequest(private val listener: AsyncTaskListener<String>?, private val start: Int) : AsyncTask<String, Void, Any>() {

    override fun doInBackground(vararg params: String): String {
        var json = ""
        try {
            val url = Connection.createUrl(Config.BUI_SERVER + "get-articles?start=" + start + "&take=5")
            json = Connection.makeHttpRequest(url)
        } catch (e: Exception) {
            e.printStackTrace()
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
