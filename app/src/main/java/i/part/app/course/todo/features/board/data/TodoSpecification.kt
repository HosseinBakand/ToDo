package i.part.app.course.todo.features.board.data

data class TodoSpecification(
    val todo: TodoEntity,
    val tasks: List<TaskEntity>
)