package i.part.app.course.todo.features.board.ui

data class TaskView(
    var name: String,
    var todo: String,
    var totalTasks: String,
    var remaningTasks: String,
    var status: String
) {
    var imageUrl = "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
}
