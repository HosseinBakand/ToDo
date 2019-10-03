package i.part.app.course.todo.features.board.data

data class AddTaskParam(
    val done: Boolean = false,
    val description: String,
    val assignee: String
)