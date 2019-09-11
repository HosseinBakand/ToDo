package i.part.app.course.todo.board.data

data class Task(
    var name: String,
    var todo: Int,
    var totalTasks: Int,
    var remaningTasks: Int,
    var status: String
) {
    var imageUrl = "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
}
