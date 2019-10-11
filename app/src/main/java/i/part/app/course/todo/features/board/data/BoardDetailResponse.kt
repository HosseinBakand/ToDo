package i.part.app.course.todo.features.board.data

data class BoardDetailResponse(
    val result: List<BoardDetailEntity>,
    val status: String
)