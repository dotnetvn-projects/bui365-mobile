package bui365.mobile.main.util


import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import android.util.Log

import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

class Connection {

    @Throws(Exception::class)
    fun getImageFromBase64(imageString: String): Bitmap {
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
    }

    companion object {
        private const val TAG = "Log Tag"

        @Throws(Exception::class)
        fun makeHttpRequest(url: URL?): String {
            var jsonResponse = ""
            if (url == null) {
                throw Exception("Url cannot empty")
            }
            var urlConnection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            try {
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.requestMethod = "GET"
                urlConnection.setRequestProperty("content-type", " application/json; charset=utf-8")
                urlConnection.readTimeout = 3000
                urlConnection.connectTimeout = 5000
                urlConnection.connect()
                val responseCode = urlConnection.responseCode

                if (responseCode == 200) {
                    inputStream = urlConnection.inputStream
                    jsonResponse = readFromStream(inputStream)
                }
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    throw Exception("HTTP error code: $responseCode")
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect()
                }
                if (inputStream != null) {
                    inputStream.close()
                }
            }

            return jsonResponse
        }

        @Throws(IOException::class)
        private fun readFromStream(inputStream: InputStream?): String {
            val output = StringBuilder()
            if (inputStream != null) {
                val isr = InputStreamReader(inputStream, "UTF-8")
                val br = BufferedReader(isr)
                var line: String? = br.readLine()
                while (line != null) {
                    output.append(line)
                    line = br.readLine()
                }
            }
            return output.toString()
        }

        fun createUrl(stringUrl: String): URL? {
            var url: URL? = null
            try {
                url = URL(stringUrl)
            } catch (e: MalformedURLException) {
                Log.e(TAG, "createUrl: ", e)
            }

            return url
        }
    }
}
