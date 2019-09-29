package i.part.app.course.todo.features.board.data

import i.part.app.course.todo.features.board.ui.SubTaskView
import i.part.app.course.todo.features.board.ui.TodoListType
import i.part.app.course.todo.features.board.ui.TodoListView

data class TodoListResponse(
    val todo: TodoResponse,
    val tasks: List<TaskResponse>
) {
    fun toTodoListView() = TodoListView(
        id = todo.id,
        todoListType = TodoListType.TODOLIST,
        todoListName = todo.title,
        subtasks = tasks.map { it.toSubTaskView() } as MutableList<SubTaskView>,
        boardId = todo.board_id
    )
}
