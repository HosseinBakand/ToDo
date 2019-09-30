package i.part.app.course.todo.features.board.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.board.data.*

class DashBoardViewModel : ViewModel() {
    private var _boardList: MutableLiveData<Result<List<BoardResponse>?>>? = null
    val boardList: LiveData<Result<List<BoardResponse>?>>?
        get() = _boardList
    private var _removeBoardLiveData: MutableLiveData<Result<DeleteBoardResponse?>>? = null
    val removeBoardLiveData: LiveData<Result<DeleteBoardResponse?>>?
        get() = _removeBoardLiveData
    private var _addBoardLiveData: MutableLiveData<Result<NewBoardResponse?>>? = null
    val addBoardLiveData: LiveData<Result<NewBoardResponse?>>?
        get() = _addBoardLiveData
    private var _updateBoardTitleLiveData: MutableLiveData<Result<UpdateBoardResponse?>>? = null
    val updateBoardTitleLivaData: LiveData<Result<UpdateBoardResponse?>>?
        get() = _updateBoardTitleLiveData
    private var _getBoardByIdLiveData: MutableLiveData<Result<BoardDetailResponse?>>? = null
    val getBoardByIdLiveData: LiveData<Result<BoardDetailResponse?>>?
        get() = _getBoardByIdLiveData
    private var _isBoardUpdated = MutableLiveData<Boolean>()
    val isBoardUpdated: LiveData<Boolean>
        get() = _isBoardUpdated
    private var _getCurrentTodosLiveData: MutableLiveData<Result<List<TodoSpecification>?>>? = null
    val getCurrentTodosLiveData: LiveData<Result<List<TodoSpecification>?>>?
        get() = _getCurrentTodosLiveData

    private val boardRepository = BoardRepository()
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
        _boardList = loadBoards()
    }

    private fun loadBoards(): MutableLiveData<Result<List<BoardResponse>?>>? {
        return boardRepository.getBoards()
    }

    fun getBoardById(id: Int) {
        _getBoardByIdLiveData = boardRepository.getBoardById(id)
    }

    fun updateBoardStatus() {
        _isBoardUpdated.value = _isBoardUpdated.value != true
    }

    fun getCurrentTodos() {
        _getCurrentTodosLiveData = boardRepository.getCurrentTodos()
    }
}