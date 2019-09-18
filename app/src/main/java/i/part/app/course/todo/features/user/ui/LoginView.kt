package i.part.app.course.todo.features.user.ui

import android.util.Patterns

data class LoginView(var email: String, var password: String) {
    fun validateEmail() = Patterns.EMAIL_ADDRESS.matcher(email).matches()
    fun validatePassword() = password.length > 6
}