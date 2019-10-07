package i.part.app.course.todo.features.board.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.board.data.*

class AddMemberViewModel:ViewModel() {
    private val repository = BoardRepository()
    private val _memberList = MutableLiveData<List<AddMemberView>>()
    val memberList: LiveData<List<AddMemberView>>
        get() = _memberList
    private val _contactList = MutableLiveData<List<SelectMemberView>>()
    val contactList: LiveData<List<SelectMemberView>>
        get() = _contactList

    private var _contactList2 = MutableLiveData<Result<ListResponse<BoardMemberResponse>?>>()
    val contactList2: LiveData<Result<ListResponse<BoardMemberResponse>?>>
        get() = _contactList2


    private var _allUsers = MutableLiveData<Result<List<BoardMemberResponse>?>>()
    val allUsers: LiveData<Result<List<BoardMemberResponse>?>>
        get() = _allUsers

    private var _addUserToBoardResponse = MutableLiveData<Result<AddUserResponse?>>()
    val addUserToBoardResponse: LiveData<Result<AddUserResponse?>>
        get() = _addUserToBoardResponse


    private var _removeMemberFromBoardResponse = MutableLiveData<Result<RemoveMemberResponse?>>()
    val removeMemberFromBoardResponse: LiveData<Result<RemoveMemberResponse?>>
        get() = _removeMemberFromBoardResponse


    private var _selectedMembers = MutableLiveData<List<String>>()
    val selectedMembers: LiveData<List<String>>
        get() = _selectedMembers


    private var _isMembersUpdated = MutableLiveData<Boolean>()
    val isMembersUpdated: LiveData<Boolean>
        get() = _isMembersUpdated

    val alreadMember = mutableListOf<BoardMemberResponse>()


    fun updateMemberStatus() {
        _isMembersUpdated.value = _isMembersUpdated.value != true
    }


    fun removeMember(boardID: Int, member: AddMemberView) {
        _removeMemberFromBoardResponse = repository.removeMemberFromBoard(boardID, member.name)
    }

    fun removeTempMember(member: AddMemberView) {
        _selectedMembers.value = _selectedMembers.value?.minus(member.name)
    }

    fun reSet() {
        _selectedMembers = MutableLiveData<List<String>>()
    }


    fun setSelectedMembers(myList: List<String>) {
        _selectedMembers.value = myList
    }

    fun addUsersToBoard(boardID: Int, addUserParam: AddUserParam) {
    _addUserToBoardResponse = repository.setUserToBoard(boardID, addUserParam)
}
//    fun setChosenContacts() {
//        _contactList.value = loadContacts()
//        _memberList.value?.let { ml ->
//            for (addMemberView in ml) {
//                _contactList.value?.let {
//                    for (selectMemberView in it) {
//                        if (selectMemberView.name == addMemberView.name) {
//                            selectMemberView.ischeck = true
//                            break
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//    fun getContacts(){
//        _contactList.value = loadContacts()
//    }

    fun changeContactState(selectMemberView: SelectMemberView) {
        val list = mutableListOf<SelectMemberView>()
        _contactList.value?.let {
            list.addAll(it)
        }
        for (smv in list) {
            if (smv.name == selectMemberView.name) {
                smv.ischeck = !smv.ischeck
                break
            }
        }
        _contactList.value = list
    }

    fun loadBoardMember(boardID: Int) {
        _contactList2 = repository.getBoardMembers(boardID)
    }

    fun loadAllusers() {
        _allUsers = repository.getAllUsers()
    }

//
//    private fun loadMembers(boardID: Int):MutableList<AddMemberView>{
//        //_memberList.value=repository.getBoardMembers(boardID = boardID)
//        return null
//    }
//
//    private fun loadContacts():MutableList<SelectMemberView>{
//        val list = mutableListOf<SelectMemberView>()
//        val url =
//            "https://cdn.cnn.com/cnnnext/dam/assets/190403152417-estados-unidos-joker-batman-trailer-perspectivas-buenos-aires-00001426-small-169.jpg"
////        list.add(SelectMemberView(url, "1Project Manager",false))
////        list.add(SelectMemberView("2Bradley Matthews", "2Bradley Matthews",false))
////        list.add(SelectMemberView(url, "3Lead Developer",false))
////        list.add(SelectMemberView("4Gary Thompson", "4Gary Thompson",false))
////        list.add(SelectMemberView("5Corey Williamson", "5Corey Williamson",false))
////        list.add(SelectMemberView("6Samuel Jones", "6Samuel Jones",false))
////        list.add(SelectMemberView(url, "7Micheal Jackson",false))
////        list.add(SelectMemberView("8Caroline Dhavernas", "8Caroline Dhavernas",false))
////        list.add(SelectMemberView(url, "9 Vahid",false))
////        list.add(SelectMemberView("10 Mohammad", "10 Mohammad",false))
////        list.add(SelectMemberView("21 Hossein", "21 Hossein", false))
//        return list
//    }
}