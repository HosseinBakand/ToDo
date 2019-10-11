package i.part.app.course.todo.features.board.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BoardDetailEntity(
    @PrimaryKey
    val id: Int,
    val title: String,
    val owner_name: String
)