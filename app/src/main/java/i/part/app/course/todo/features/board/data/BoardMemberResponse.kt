package i.part.app.course.todo.features.board.data

data class BoardMemberResponse(
    val id: Int,
    val profile_pic: String,
    val name: String,
    val passwd: String,
    val salt: String
)