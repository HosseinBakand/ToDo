package i.part.app.course.todo.features.user.data

data class RegisterResponse(
    var name: String? = null,
    var length: Int? = null,
    var severity: String? = null,
    var code: String? = null,
    var detail: String? = null,
    var schema: String? = null,
    var table: String? = null,
    var constraint: String? = null,
    var file: String? = null,
    var line: String? = null,
    var routine: String? = null,
    var status: String? = null,
    var massage: String? = null
)