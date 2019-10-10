package i.part.app.course.todo.features.board.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
abstract class BoardDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTodo(todoList: TodoEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTasks(taskLists: List<TaskEntity>)

    @Transaction
    @Query("SELECT * from TodoEntity Where board_id=:boardId")
    abstract fun getAllTodos(boardId: Int): LiveData<List<TodoListDto>>

    fun insertTodoList(todo: TodoListDto) {
        insertTodo(todo.todo)
        insertTasks(todo.tasks)
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertUsers(userEntities: List<BoardMemberEntity>?)

    @Query("DELETE from MemberOfBoardEntity where boardID=:boardID")
    abstract fun cleanAllUsers(boardID: Int)

    @Query("SELECT * from MemberOfBoardEntity where boardID=:boardID")
    abstract fun getBoardUsers(boardID: Int): LiveData<List<MemberOfBoardEntity>>

    @Query("SELECT * from BoardMemberEntity")
    abstract fun getAllUsers(): LiveData<List<BoardMemberEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun setBoardMember(memberOfBoardEntity: MemberOfBoardEntity)

//    @Delete
//    fun deleteUsers(userEntity: UserEntity)
//
//    @Query("Delete from UserEntity where id=:id")
//    fun deleteAllUsers(id: Int)


}