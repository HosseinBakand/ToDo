package i.part.app.course.todo.features.board.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BoardMemberEntity(
    @PrimaryKey
    val id: Int,
    val profile_pic: String,
    val name: String,
    val passwd: String,
    val salt: String
)