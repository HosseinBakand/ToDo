package i.part.app.course.todo.features.board.ui

data class SubTaskView(
    var id: Int,
    var subTaskDescription: String,
    var imageUri: String,
    var isCompleted: Boolean = false,
    var todoId: Int
)
