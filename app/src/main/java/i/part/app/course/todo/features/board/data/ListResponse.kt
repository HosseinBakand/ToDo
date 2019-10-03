package i.part.app.course.todo.features.board.data

data class ListResponse<T>(val status: String, val result: List<T>)