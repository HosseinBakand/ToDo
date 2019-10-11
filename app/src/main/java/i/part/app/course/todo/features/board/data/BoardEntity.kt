package i.part.app.course.todo.features.board.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class BoardEntity(
    @PrimaryKey
    var id: Int?,
    var title: String?,
    var owner_name: String?
)