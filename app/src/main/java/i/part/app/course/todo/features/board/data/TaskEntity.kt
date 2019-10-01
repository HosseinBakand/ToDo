package i.part.app.course.todo.features.board.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import i.part.app.course.todo.features.board.ui.SubTaskView

@Entity
data class TaskEntity(
    @PrimaryKey
    val id: Int,
    val description: String,
    val done: Boolean,
    val assignee: String,
    val todo_id: Int
) {
    fun toSubTaskView() = SubTaskView(
        id = id,
        subTaskDescription = description,
        imageUri = "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png",
        isCompleted = done,
        todoId = todo_id
    )
}
