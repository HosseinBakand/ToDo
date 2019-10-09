package i.part.app.course.todo.features.board.data

import androidx.room.Embedded
import androidx.room.Relation
import i.part.app.course.todo.features.board.ui.SubTaskView
import i.part.app.course.todo.features.board.ui.TodoListType
import i.part.app.course.todo.features.board.ui.TodoListView

data class TodoListDto(
    @Embedded
    val todo: TodoEntity = TodoEntity(0, "", 0),
    @Relation(parentColumn = "id", entityColumn = "todo_id", entity = TaskEntity::class)
    val tasks: List<TaskEntity>
) {
//    fun toTodoListView() = TodoListView(
//        id = todo.id,
//        todoListType = TodoListType.TODOLIST,
//        todoListName = todo.title,
//        subtasks = tasks.map { it.toSubTaskView() } as MutableList<SubTaskView>,
//        boardId = todo.board_id
//    )

    fun toTodoListView(): TodoListView {
        var subTasks = mutableListOf<SubTaskView>()
        (tasks.map { it.toSubTaskView() } as MutableList<SubTaskView>).let {
            subTasks = it
        }

        return TodoListView(
            id = todo.id,
            todoListType = TodoListType.TODOLIST,
            todoListName = todo.title,
            subtasks = subTasks,
            boardId = todo.board_id
        )
    }
}
