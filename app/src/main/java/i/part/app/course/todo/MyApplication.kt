package i.part.app.course.todo

import android.app.Application
import android.content.Context
import i.part.app.course.todo.core.db.TodoDatabase


class MyApplication : Application() {
    companion object {
        var TOKEN: String? = null
    }

    override fun onCreate() {
        super.onCreate()
        getToken()
        TodoDatabase.init(this)
    }


    fun getToken() {
        val prefs = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        TOKEN = prefs.getString(
            "myToken",
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InBpZUB0ZXN0LmNvbSIsImlhdCI6MTU2OTMxNjQ3OH0.CFZ10UXOIhujcMVDm82xg3axdCAu6y1bAsk0IcDZbSI"
        )//"No name defined" is the default value.
    }
}