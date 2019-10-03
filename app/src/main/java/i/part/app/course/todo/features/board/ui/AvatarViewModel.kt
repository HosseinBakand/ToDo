package i.part.app.course.todo.features.board.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import i.part.app.course.todo.features.board.data.BoardRepository

class AvatarViewModel {
    private val _avatarViewList = MutableLiveData<List<AvatarView>>()
    val avatarViewList: LiveData<List<AvatarView>>
        get() = _avatarViewList
    val BoardRepository = BoardRepository()
}