package i.part.app.course.todo
import android.app.Application
import android.content.Context
import i.part.app.course.todo.core.db.TodoDatabase

class MyApplication : Application() {
    companion object {
        var TOKEN: String? = null
        var ownerName: String? = "Pie"
        var isConnectedToNetwork: Boolean = false
    }

    override fun onCreate() {
        super.onCreate()
        getToken()
        getOwner()
        TodoDatabase.init(this)
    }


    private fun getToken() {
        val prefs = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        TOKEN = prefs.getString(
            "myToken",
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InBpZUB0ZXN0LmNvbSIsImlhdCI6MTU2OTMxNjQ3OH0.CFZ10UXOIhujcMVDm82xg3axdCAu6y1bAsk0IcDZbSI"
        )//"No name defined" is the default value.
    }

    private fun getOwner() {
        val prefs = getSharedPreferences("myPref", Context.MODE_PRIVATE)
        ownerName = prefs.getString(
            "myOwner",
            "HosseiN_Bakand"
        )//"No name defined" is the default value.
    }
}