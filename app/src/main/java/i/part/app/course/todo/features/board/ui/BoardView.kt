package i.part.app.course.todo.features.board.ui

data class BoardView(
    val id: Int = -1,
    val title: String,
    val owner_name: String = "",
    var todo: String = "",
    var totalTasks: String = "",
    var remainingTasks: String = "",
    var status: BoardStatusEnum = BoardStatusEnum.ToDo,
    var imageUrl: String = "https://img.freepik.com/free-vector/colorful-watercolor-background_79603-99.jpg?size=626&ext=jpg"
)
