package i.part.app.course.todo.features.board.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity
data class TodoEntity @JvmOverloads constructor(
    @PrimaryKey
    val id: Int,
    val title: String,
    val board_id: Int,
    @Ignore
    val tasks: List<TaskEntity>? = null
)