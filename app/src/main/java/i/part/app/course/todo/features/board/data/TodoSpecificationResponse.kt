package i.part.app.course.todo.features.board.data


data class TodoSpecificationResponse(
    val todo: TodoEntity,
    val tasks: List<TaskEntity>
)