package i.part.app.course.todo.features.board.ui

data class TodoListView(
    val id: Int,
    val todoListType: TodoListType,
    var todoListName: String,
    var subtasks: MutableList<SubTaskView>,
    var boardId: Int
)
