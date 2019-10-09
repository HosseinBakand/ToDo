package i.part.app.course.todo.features.board.data

import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import i.part.app.course.todo.features.board.ui.SubTaskView
import i.part.app.course.todo.features.board.ui.TodoListType
import i.part.app.course.todo.features.board.ui.TodoListView

@Entity
data class TodoEntity @JvmOverloads constructor(
    @PrimaryKey
    val id: Int,
    val title: String,
    val board_id: Int,
    @Ignore
    val tasks: List<TaskEntity>? = null
) {
    fun toTodoListView() {
        var subTasks = mutableListOf<SubTaskView>()
        (tasks?.map { it.toSubTaskView() } as MutableList<SubTaskView>).let {
            subTasks = it
        }

        TodoListView(
            id = id,
            todoListType = TodoListType.TODOLIST,
            todoListName = title,
            subtasks = subTasks,
            boardId = board_id
        )
    }
}