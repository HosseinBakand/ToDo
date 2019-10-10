package i.part.app.course.todo.features.board.data

import androidx.room.Entity

@Entity(primaryKeys = ["boardID", "MemberId"])
data class MemberOfBoardEntity(
    val boardID: Int,
    val MemberId: Int,
    val MemberName: String
)