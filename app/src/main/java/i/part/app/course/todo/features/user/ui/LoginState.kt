package i.part.app.course.todo.features.user.ui

data class LoginState(
    var emailError: String? = null,
    var passError: String? = null,
    var isDataValid: Boolean = false
)