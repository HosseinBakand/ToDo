package i.part.app.course.todo.features.board.ui

data class SubTaskView(
    var subTaskDescription: String,
    var isCompleted: Boolean = false,
    var imageUri: String = "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png")
