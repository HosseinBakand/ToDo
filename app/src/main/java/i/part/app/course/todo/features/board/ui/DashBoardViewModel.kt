package i.part.app.course.todo.features.board.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.board.data.*

class DashBoardViewModel : ViewModel() {
    private var _removeBoardLiveData: MutableLiveData<Result<DeleteBoardResponse?>>? = null
    val removeBoardLiveData: LiveData<Result<DeleteBoardResponse?>>?
        get() = _removeBoardLiveData
    private var _addBoardLiveData: MutableLiveData<Result<NewBoardResponse?>>? = null
    val addBoardLiveData: LiveData<Result<NewBoardResponse?>>?
        get() = _addBoardLiveData
    private var _updateBoardTitleLiveData: MutableLiveData<Result<UpdateBoardResponse?>>? = null
    val updateBoardTitleLivaData: LiveData<Result<UpdateBoardResponse?>>?
        get() = _updateBoardTitleLiveData
    private lateinit var _getBoardByIdLiveData: LiveData<BoardDetailEntity>
    val getBoardByIdLiveData: LiveData<BoardDetailEntity>
        get() = _getBoardByIdLiveData
    private var _isBoardUpdated = MutableLiveData<Boolean>()
    val isBoardUpdated: LiveData<Boolean>
        get() = _isBoardUpdated
    var getBoardsFromRemoteLiveData: MutableLiveData<Result<String>>? = null
    private val boardRepository = BoardRepository()
    private val _boardList: LiveData<List<BoardEntity>> = boardRepository.boards
    val boardList: LiveData<List<BoardEntity>>
        get() = _boardList

    private var _getCurrentTodosLiveData: LiveData<List<TodoListDto>>? =
        boardRepository.currentTodos
    val getCurrentTodosLiveData: LiveData<List<TodoListDto>>?
        get() = _getCurrentTodosLiveData

    fun addBoard(boardView: BoardView) {
        _addBoardLiveData =
            boardRepository.createBoard(NewBoardParam(boardView.title, boardView.owner_name))
    }

    fun removeBoard(boardView: BoardView) {
        _removeBoardLiveData = boardRepository.deleteBoardById(boardView.id)
    }

    fun updateBoardTitle(boardView: BoardView) {
        _updateBoardTitleLiveData = boardRepository.updateBoardTitle(boardView.id, boardView.title)
    }

    fun getBoards() {
        getBoardsFromRemoteLiveData = boardRepository.getBoardsFromRemoteDataSource()
    }

    fun getBoardById(id: Int) {
        _getBoardByIdLiveData = boardRepository.getBoardByIdFromLocalDataSource(id)
    }

    fun updateBoardStatus() {
        _isBoardUpdated.value = _isBoardUpdated.value != true
    }

    fun getCurrentTodos() {
        boardRepository.getCurrentTodos()
    }
}