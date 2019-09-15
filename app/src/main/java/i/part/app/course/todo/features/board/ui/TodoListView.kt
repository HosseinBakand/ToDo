package i.part.app.course.todo.features.board.ui

data class TodoListView(
    val todoListType: TodoListType,
    var todoListName: String,
    var subtasks:MutableList<SubTaskView>
)
