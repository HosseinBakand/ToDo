package i.part.app.course.todo.features.user.data

data class LoginResponse(
    val status: String,
    val massage: String,
    val token: String,
    val username: String
)