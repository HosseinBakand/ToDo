package i.part.app.course.todo.features.board.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
abstract class BoardDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertTodo(todoList: TodoEntity)

    @Transaction
    @Query("SELECT * from TodoEntity")
    abstract fun getAllBoardsTodos(): LiveData<List<TodoListDto>>

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
    abstract fun insertBoards(boardEntities: List<BoardEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertBoard(boardEntity: BoardEntity)

    @Query("SELECT * FROM BoardEntity")
    abstract fun getBoards(): LiveData<List<BoardEntity>>

    @Query("SELECT * FROM BoardEntity WHERE id=:boardId")
    abstract fun getBoardById(boardId: Int): LiveData<BoardDetailEntity>

    @Query(
        """UPDATE BoardEntity
                    SET title = :boardTitle
                    WHERE id=:boardId
    """
    )
    abstract fun updateBoardTitle(boardId: Int, boardTitle: String)

    @Query("DELETE FROM BoardEntity")
    abstract fun deleteAllBoards()

    @Query("DELETE FROM BoardEntity WHERE id=:boardId")
    abstract fun deleteBoardById(boardId: Int)

    @Query("SELECT count(*) FROM BoardEntity")
    abstract fun count(): Int

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
}