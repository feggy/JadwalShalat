package com.empe.jadwalshalat

import android.content.Context
import android.os.AsyncTask
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL

class ClientAsyncTask constructor(private val mContext: Context, postExecuteListener: OnPostExecuteListener) :
    AsyncTask<String, String, String>(){

    val TIMEOUT = 60000
    private val mPostExecuteListener: OnPostExecuteListener = postExecuteListener

    interface OnPostExecuteListener {
        fun onPostExecute(result: String)
    }

    override fun onPostExecute(result: String) {
        super.onPostExecute(result)
        if (mPostExecuteListener != null){
            mPostExecuteListener.onPostExecute(result)
        }
    }

    override fun doInBackground(vararg urls: String?): String {
        var urlConnection: HttpURLConnection? = null

        try {
            val url = URL(urls[0])

            urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.connectTimeout = TIMEOUT
            urlConnection.readTimeout = TIMEOUT

            var inString = streamToString(urlConnection.inputStream)

            return inString
        } catch (ex: Exception) {

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect()
            }
        }

        return ""
    }

    private fun streamToString(inputStream: InputStream?): String {
        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result += line
                }
            } while (line != null)
            inputStream?.close()
        } catch (ex : Exception) {

        }

        return result
    }
}