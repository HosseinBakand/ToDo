package i.part.app.course.todo.features.board.data

data class TodoSpecification(
    val todo: TodoResponse,
    val tasks: List<TaskResponse>
)