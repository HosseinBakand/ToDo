package i.part.app.course.todo.features.user.ui

import android.os.Handler
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {

    private val _status = MutableLiveData<String>()

    val status: LiveData<String>
        get() = _status

    fun getStatus() {
        _status.value = "failed"
    }

    fun setStatus() {
        _status.value = "success"
    }

    fun validateEmail(loginView: LoginView) = loginView.validateEmail()
    fun validatePassword(loginView: LoginView) = loginView.validatePassword()

    fun loading() {
        Handler().postDelayed(Runnable {
            setStatus()
        }, 3000)
    }
}