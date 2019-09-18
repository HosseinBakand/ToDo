package i.part.app.course.todo.features.board.ui

data class BoardView(
    var name: String,
    var todo: String,
    var totalTasks: String,
    var remainingTasks: String,
    var status: String,
    var imageUrl: String
)
