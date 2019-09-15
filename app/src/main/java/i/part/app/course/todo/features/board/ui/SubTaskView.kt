package i.part.app.course.todo.features.board.ui

data class SubTaskView(
    var subTaskDescription: String,
    var imageUri: String,
    var isCompleted: Boolean = false
)
