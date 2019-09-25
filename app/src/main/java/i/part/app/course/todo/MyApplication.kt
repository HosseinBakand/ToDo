package i.part.app.course.todo

import android.app.Application
import android.content.Context


class MyApplication : Application() {
    companion object {
        var TOKEN: String? = null
    }

    override fun onCreate() {
        super.onCreate()
        getToken()
    }

    fun saveToken(token: String) {
        val editor = getSharedPreferences("myPref", Context.MODE_PRIVATE).edit()
        editor.putString("myToken", token)
        editor.apply()
        TOKEN = token
    }

    fun getToken() {
        val prefs = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        TOKEN = prefs.getString(
            "myToken",
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InBpZUB0ZXN0LmNvbSIsImlhdCI6MTU2OTMxNjQ3OH0.CFZ10UXOIhujcMVDm82xg3axdCAu6y1bAsk0IcDZbSI"
        )//"No name defined" is the default value.
    }
}