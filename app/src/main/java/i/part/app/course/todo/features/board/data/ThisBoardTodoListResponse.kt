package i.part.app.course.todo.features.board.data


data class ThisBoardTodoListResponse(
    val status: String,
    val result: List<TodoListDto>
)