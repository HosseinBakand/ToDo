package i.part.app.course.todo.features.user.ui

import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.user.data.RegisterParam
import i.part.app.course.todo.features.user.data.RegisterResponse
import i.part.app.course.todo.features.user.data.UserRepository

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState

    private var _registerResult = MutableLiveData<Result<RegisterResponse?>>()
    val registerResult: LiveData<Result<RegisterResponse?>>
        get() = _registerResult

    private val repository = UserRepository()
    fun onDataChange(
        email: String,
        phoneNumber: String,
        password: String,
        confirmPassword: String
    ) {

        if (!isValidEmail(email)) {
            if (email.isNotEmpty())
                _registerState.value = RegisterState(emailError = "Invalid Email")
            else
                _registerState.value = RegisterState()

        } else if (!(phoneNumber.startsWith("09") && phoneNumber.length == 11)) {
            if (phoneNumber.isNotEmpty())
                _registerState.value = RegisterState(phoneNumberError = "Invalid phone number")
            else
                _registerState.value = RegisterState()

        } else if (!isValidPassword(password)) {
            if (password.isNotEmpty())
                _registerState.value =
                    RegisterState(passError = passwordError(password))
            else
                _registerState.value = RegisterState()

        } else if (!isConfirmPassword(password, confirmPassword)) {
            if (confirmPassword.isNotEmpty())
                _registerState.value =
                    RegisterState(confirmPassError = "confirm password is not correct")
            else
                _registerState.value = RegisterState()
        } else {
            _registerState.value = RegisterState(isDataValid = true)
        }
    }

    fun clickOnRegisterButton(email: String, phoneNumber: String, password: String) {
        _registerResult = repository.register(RegisterParam(email, password))
    }

    private fun isConfirmPassword(password: String, confirmPassword: String): Boolean =
        password == confirmPassword

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

    fun exist_email(email: String): Boolean {
        //TODO is not implement
        return false
    }
}