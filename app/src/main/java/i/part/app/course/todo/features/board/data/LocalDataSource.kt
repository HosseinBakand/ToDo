package i.part.app.course.todo.features.board.data


import android.content.Context
import i.part.app.course.todo.core.db.TodoDatabase

class LocalDataSource(
    context: Context
) {

    private var db: TodoDatabase? = null

    init {
        db = TodoDatabase.getInstance()
    }


}