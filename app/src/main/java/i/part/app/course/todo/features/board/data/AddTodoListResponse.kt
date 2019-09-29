package i.part.app.course.todo.features.board.data

data class AddTodoListResponse(
    val status: String? = null,
    val message: String? = null,
    val data: TodoIdResponse? = null,
    val name: String? = null,
    val length: Int? = null,
    val severity: String? = null,
    val code: String? = null,
    val file: String? = null,
    val line: String? = null,
    val routine: String? = null
)
