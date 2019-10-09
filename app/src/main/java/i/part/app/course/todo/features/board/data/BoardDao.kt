package i.part.app.course.todo.features.board.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
abstract class BoardDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTodo(todoList: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTasks(taskLists: List<TaskEntity>)

    @Query("SELECT * from TodoEntity Where board_id=:boardId")
    abstract fun getAllTodos(boardId: Int): LiveData<List<TodoListDto>>

    fun insertTodoList(todo: TodoListDto) {
        insertTodo(todo.todo)
        insertTasks(todo.tasks)
    }


//    @Insert
//    fun insertUser(userEntities: List<UserEntity>)
//
//    @Query("SELECT * from UserEntity")
//    fun getAllUsers(): LiveData<List<UserEntity>>
//
//    @Delete
//    fun deleteUsers(userEntity: UserEntity)
//
//    @Query("Delete from UserEntity where id=:id")
//    fun deleteAllUsers(id: Int)


}