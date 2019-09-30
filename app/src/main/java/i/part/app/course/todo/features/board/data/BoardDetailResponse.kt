package i.part.app.course.todo.features.board.data

data class BoardDetailResponse(
    val status: String,
    val result: List<BoardDetail>
)

data class BoardDetail(
    val id: Int,
    val title: String,
    val owner_name: String
)