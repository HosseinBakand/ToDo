package i.part.app.course.todo.board.ui

data class TodoListView(val viewType: Int, var todoListName: String, var isCompleted: Boolean) {
    companion object{
        const val ADD_TODOLIST_BUTTON = 0
        const val TODOLIST = 1
    }
}