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


    private var _isMembersUpdated = MutableLiveData<Boolean>()
    val isMembersUpdated: LiveData<Boolean>
        get() = _isMembersUpdated


    fun updateMemberStatus() {
        _isMembersUpdated.value = _isMembersUpdated.value != true
    }



    fun removeMember(member:AddMemberView){
        val list = mutableListOf<AddMemberView>()
        val members = _memberList.value
        members?.let {
            list.addAll(it)
            list.removeAt(memberList.value?.indexOf(member) as Int)
            _memberList.value = list
        }
    }

    fun removeMember(member:SelectMemberView){
        val list = mutableListOf<AddMemberView>()
        val members = _memberList.value
        members?.let { ms ->
            list.addAll(ms)
            list.removeAll { it.name == member.name }
            _memberList.value = list
        }
    }

    fun addMember(member:SelectMemberView) {
        val list = mutableListOf<AddMemberView>()
        val members = _memberList.value
        members?.let {
            list.addAll(it)
            list.add(AddMemberView(member.imageUrl, member.name))
            _memberList.value = list
        }
    }

//    fun getMembers() {
//        _memberList.value = loadMembers()
//    }
fun addUsersToBoard(boardID: Int, addUserParam: AddUserParam) {
    _addUserToBoardResponse = repository.setUserToBoard(boardID, addUserParam)
}
    fun setMembers(list: List<AddMemberView>) {
        _memberList.value = list
    }

    fun setChosenContacts() {
        _contactList.value = loadContacts()
        _memberList.value?.let { ml ->
            for (addMemberView in ml) {
                _contactList.value?.let {
                    for (selectMemberView in it) {
                        if (selectMemberView.name == addMemberView.name) {
                            selectMemberView.ischeck = true
                            break
                        }
                    }
                }
            }
        }
    }

    fun getContacts(){
        _contactList.value = loadContacts()
    }

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

    private fun loadContacts():MutableList<SelectMemberView>{
        val list = mutableListOf<SelectMemberView>()
        val url =
            "https://cdn.cnn.com/cnnnext/dam/assets/190403152417-estados-unidos-joker-batman-trailer-perspectivas-buenos-aires-00001426-small-169.jpg"
//        list.add(SelectMemberView(url, "1Project Manager",false))
//        list.add(SelectMemberView("2Bradley Matthews", "2Bradley Matthews",false))
//        list.add(SelectMemberView(url, "3Lead Developer",false))
//        list.add(SelectMemberView("4Gary Thompson", "4Gary Thompson",false))
//        list.add(SelectMemberView("5Corey Williamson", "5Corey Williamson",false))
//        list.add(SelectMemberView("6Samuel Jones", "6Samuel Jones",false))
//        list.add(SelectMemberView(url, "7Micheal Jackson",false))
//        list.add(SelectMemberView("8Caroline Dhavernas", "8Caroline Dhavernas",false))
//        list.add(SelectMemberView(url, "9 Vahid",false))
//        list.add(SelectMemberView("10 Mohammad", "10 Mohammad",false))
//        list.add(SelectMemberView("21 Hossein", "21 Hossein", false))
        return list
    }
}