package i.part.app.course.todo.features.board.data


data class AddTaskResponse(
    val status: String,
    val message: String,
    val data: TaskId
)