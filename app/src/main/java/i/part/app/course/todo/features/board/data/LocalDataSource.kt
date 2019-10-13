package i.part.app.course.todo.features.board.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import i.part.app.course.todo.core.db.TodoDatabase

class LocalDataSource {
    private var db: TodoDatabase? = null

    init {
        db = TodoDatabase.getInstance()
    }

    fun insertTodoLists(list: ThisBoardTodoListResponse) {
        Thread { list.result.forEach { db?.getBoardDao()?.insertTodoList(it) } }.start()
    }

    fun cleanAllDB() {
        db?.getBoardDao()?.cleanAllBoardMemberEntity()
        db?.getBoardDao()?.cleanAllMemberOfBoardEntity()
        db?.getBoardDao()?.cleanAllTaskEntity()
        db?.getBoardDao()?.cleanAllTodoEntity()
    }

    fun getTodoLists(boardId: Int): LiveData<List<TodoListDto>>? {
        return db?.getBoardDao()?.getAllTodos(boardId)
    }

    fun insertTodoLists(list: List<TodoListDto>) {
        Thread { list.forEach { db?.getBoardDao()?.insertTodoList(it) } }.start()
    }

    fun getAllBoardsTodos(): LiveData<List<TodoListDto>>? {
        return db?.getBoardDao()?.getAllBoardsTodos()
    }

    fun getBoards(): LiveData<List<BoardEntity>> {
        return db?.getBoardDao()?.getBoards() ?: MutableLiveData()
    }

    fun getBoardCount() = db?.getBoardDao()?.count() ?: 0
    fun getBoardById(boardId: Int): LiveData<BoardDetailEntity> {
        return db?.getBoardDao()?.getBoardById(boardId) ?: MutableLiveData()
    }

    fun insertBoard(boardEntity: BoardEntity) {
        db?.getBoardDao()?.insertBoard(boardEntity)
    }

    fun insertBoards(boardEntities: List<BoardEntity>) {
        Thread { db?.getBoardDao()?.insertBoards(boardEntities) }.start()
    }

    fun updateBoardTitle(boardId: Int, boardTitle: String) {
        db?.getBoardDao()?.updateBoardTitle(boardId, boardTitle)
    }

    fun deleteBoardById(boardId: Int) {
        db?.getBoardDao()?.deleteBoardById(boardId)
    }

    fun deleteAllBoards() {
        db?.getBoardDao()?.deleteAllBoards()
    }

    fun getAllusers(): LiveData<List<BoardMemberEntity>>? {
        return db?.getBoardDao()?.getAllUsers()
    }


    fun getBoardUsers(boardId: Int): LiveData<List<MemberOfBoardEntity>>? {
        return db?.getBoardDao()?.getBoardUsers(boardId)
    }

    fun insertUser(boardMemberEntity: List<BoardMemberEntity>?) {
        db?.getBoardDao()?.insertUsers(boardMemberEntity)
    }

    fun insertBoardMember(boardMemberEntity: List<BoardMemberEntity>?, boardId: Int) {
        var memberOfBoardEntity: MemberOfBoardEntity
        db?.getBoardDao()?.cleanAllUsers(boardId)
        boardMemberEntity?.let {
            for (item in boardMemberEntity) {
                memberOfBoardEntity = MemberOfBoardEntity(boardId, item.id, item.name)
                db?.getBoardDao()?.setBoardMember(memberOfBoardEntity)
            }
        }
    }
}