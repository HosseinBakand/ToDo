package i.part.app.course.todo.features.board.data

data class UpdateTaskParam(
    val done: Boolean = false,
    val description: String
)