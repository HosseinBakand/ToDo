package i.part.app.course.todo.features.user.ui

import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.user.data.LoginParam
import i.part.app.course.todo.features.user.data.LoginResponse
import i.part.app.course.todo.features.user.data.UserRepository

class LoginViewModel : ViewModel() {
    private var _loginResult = MutableLiveData<Result<LoginResponse?>>()
    val loginResult: LiveData<Result<LoginResponse?>>
        get() = _loginResult

    private var _loginState = MutableLiveData<LoginState?>()
    val loginState: LiveData<LoginState?>
        get() = _loginState

    private val repository = UserRepository()
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

    fun login(loginParam: LoginParam) {
        _loginResult = repository.login(loginParam)
    }

    private fun isValidEmail(email: String): Boolean =
        !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean {

        return (password.length >= 8 && password.matches(Regex("(.*)[A-Z](.*)"))
                && password.matches(Regex("(.*)[a-z](.*)"))
                && password.matches(Regex("(.*)[0-9](.*)")))
    }

    private fun passwordError(password: String): String {


        return if (!password.matches(Regex("(.*)[A-Z](.*)"))) "Password should contain at least one uppercase character"
        else if (!password.matches(Regex("(.*)[a-z](.*)"))) "Password should contain at least one lowercase character"
        else if (!password.matches(Regex("(.*)[0-9](.*)"))) "Password should contain at least one number character"
        else "Password should contain at least 8 character"
    }

    fun onDataChanged(email: String, password: String) {
        if (!isValidEmail(email)) {
            if (email.isNotEmpty())
                _loginState.value = LoginState(emailError = "Invalid Email")
            else
                _loginState.value = LoginState()

        } else {
            _loginState.value = LoginState(isDataValid = true)
        }

    }
}