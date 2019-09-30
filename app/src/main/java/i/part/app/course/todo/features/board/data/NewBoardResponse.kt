package i.part.app.course.todo.features.board.data

data class NewBoardResponse(
    val status: String,
    val message: String,
    val data: AddedBoardDetails
)

data class AddedBoardDetails(
    val boardId: Int
)