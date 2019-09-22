package i.part.app.course.todo.features.board.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashBoardViewModel : ViewModel() {
    private val _boardList = MutableLiveData<List<BoardView>>()
    val boardList: LiveData<List<BoardView>>
        get() = _boardList


    fun removeBoard(user: BoardView) {
        _boardList.value = _boardList.value?.minus(user)
    }

    fun removeBoardPos(position: Int) {
        val list = mutableListOf<BoardView>()
        _boardList.value?.let { list.addAll(it) }
        list.removeAt(position)
        _boardList.value = list
    }

    fun addBoard(boardView: BoardView) {
        val list = mutableListOf<BoardView>()
        _boardList.value?.let { list.addAll(it) }
        list.add(list.size, boardView)
        _boardList.value = list
    }

    fun EditBoard(boardView: BoardView) {
        val list = mutableListOf<BoardView>()
        _boardList.value?.let { list.addAll(it) }
        list.add(list.size, boardView)
        _boardList.value = list
    }

    fun getBoards() {
        _boardList.value = loadBoard()
    }

    private fun loadBoard(): MutableList<BoardView> {
        val list = mutableListOf<BoardView>()

        list.add(
            BoardView(
                "board1",
                "8",
                "118",
                "43",
                BoardStatusEnum.Done,
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        list.add(
            BoardView(
                "board2",
                "6",
                "118",
                "54",
                BoardStatusEnum.ToDo,
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        list.add(
            BoardView(
                "board3",
                "7",
                "118",
                "12",
                BoardStatusEnum.ToDo,
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        list.add(
            BoardView(
                "board4",
                "81",
                "118",
                "498",
                BoardStatusEnum.Done,
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        list.add(
            BoardView(
                "board5",
                "12",
                "118",
                "34",
                BoardStatusEnum.Done,
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        list.add(
            BoardView(
                "board6",
                "55",
                "118",
                "23",
                BoardStatusEnum.Done,
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )

        return list
    }
}