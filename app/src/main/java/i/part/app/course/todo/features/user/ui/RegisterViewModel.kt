package i.part.app.course.todo.features.user.ui

import android.os.Handler
import android.text.TextUtils
import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class RegisterViewModel : ViewModel() {
    private val _registerState = MutableLiveData<RegisterState>()
    val registerState: LiveData<RegisterState> = _registerState

    private val _registerResult = MutableLiveData<RegisterResult>()
    val registerResult: LiveData<RegisterResult> = _registerResult

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
                    RegisterState(passError = "Password Should be Minimum 6 Characters")
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
        val handler = Handler()
        handler.postDelayed(Runnable {
            // Do something after 3s = 3000ms
            val result = true //TODO add code for connect to Server


            if (result) {
                _registerResult.value = RegisterResult(success = "HosseiN")
            } else {
                _registerResult.value = RegisterResult(error = 0)
            }
        }, 3000)
    }

    private fun isConfirmPassword(password: String, confirmPassword: String): Boolean =
        password == confirmPassword

    private fun isValidEmail(email: String): Boolean =
        !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches()

    private fun isValidPassword(password: String): Boolean = password.length >= 6

    fun exist_email(email: String): Boolean {
        //TODO is not implement
        return false
    }
}