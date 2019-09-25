package i.part.app.course.todo.features.user.ui

data class RegisterState(
    var emailError: String? = null,
    var phoneNumberError: String? = null,
    var passError: String? = null,
    var confirmPassError: String? = null,
    var isDataValid: Boolean = false
)
