package i.part.app.course.todo.core.util.network

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.net.ConnectivityManager
import i.part.app.course.todo.MyApplication


class NetworkChangeReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        try {
            MyApplication.isConnectedToNetwork = isOnline(context)
        } catch (e: NullPointerException) {
            e.printStackTrace()
        }
    }

    private fun isOnline(context: Context?): Boolean {

            val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = cm.activeNetworkInfo
            //should check null because in airplane mode it will be null
        return netInfo != null && netInfo.isConnected
    }
}

class NetworkHandler constructor(private val context: Context) {
    companion object {
        private lateinit var connectivityManager: ConnectivityManager
        fun init(context: Context) {
            connectivityManager =
                context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        }

        fun hasNetworkConnection(): Boolean {
            if (!::connectivityManager.isInitialized)
                return false
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            return activeNetworkInfo != null && activeNetworkInfo.isConnected
        }
    }

}