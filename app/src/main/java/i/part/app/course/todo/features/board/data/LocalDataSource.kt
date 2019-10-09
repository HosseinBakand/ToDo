package i.part.app.course.todo.features.board.data


import androidx.lifecycle.LiveData
import i.part.app.course.todo.core.db.TodoDatabase

class LocalDataSource {

    private var db: TodoDatabase? = null

    init {
        db = TodoDatabase.getInstance()
    }

    fun insertTodoLists(list: ThisBoardTodoListResponse) {
        list.result.forEach { db?.getBoardDao()?.insertTodoList(it) }

    }

    fun getTodoLists(boardId: Int): LiveData<List<TodoListDto>>? {
        return db?.getBoardDao()?.getAllTodos(boardId)
    }
//
//    fun insertPets(list:List<PetEntity>){
//        db?.getUserDao()?.insertPets(list)
//    }
//
//    fun getAllUsers(): LiveData<List<UserPetEntity>>{
//        return db?.getUserDao()?.getAllUsers() ?: MutableLiveData()
//    }

}