package i.part.app.course.todo.features.board.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddMemberViewModel:ViewModel() {
    private val _memberList = MutableLiveData<List<AddMemberView>>()
    val memberList: LiveData<List<AddMemberView>>
        get() = _memberList
    private val _contactList = MutableLiveData<List<SelectMemberView>>()
    val contactList: LiveData<List<SelectMemberView>>
        get() = _contactList

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

    fun getMembers() {
        _memberList.value = loadMembers()
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

    private fun loadMembers():MutableList<AddMemberView>{
        val list = mutableListOf<AddMemberView>()
        val url =
            "https://cdn.cnn.com/cnnnext/dam/assets/190403152417-estados-unidos-joker-batman-trailer-perspectivas-buenos-aires-00001426-small-169.jpg"
        list.add(AddMemberView(url, "1Project Manager"))
        list.add(AddMemberView("2Bradley Matthews", "2Bradley Matthews"))
        list.add(AddMemberView(url, "3Lead Developer"))
        list.add(AddMemberView("4Gary Thompson", "4Gary Thompson"))
        list.add(AddMemberView("5Corey Williamson", "5Corey Williamson"))
        list.add(AddMemberView("6Samuel Jones", "6Samuel Jones"))

        return list
    }

    private fun loadContacts():MutableList<SelectMemberView>{
        val list = mutableListOf<SelectMemberView>()
        val url =
            "https://cdn.cnn.com/cnnnext/dam/assets/190403152417-estados-unidos-joker-batman-trailer-perspectivas-buenos-aires-00001426-small-169.jpg"
        list.add(SelectMemberView(url, "1Project Manager",false))
        list.add(SelectMemberView("2Bradley Matthews", "2Bradley Matthews",false))
        list.add(SelectMemberView(url, "3Lead Developer",false))
        list.add(SelectMemberView("4Gary Thompson", "4Gary Thompson",false))
        list.add(SelectMemberView("5Corey Williamson", "5Corey Williamson",false))
        list.add(SelectMemberView("6Samuel Jones", "6Samuel Jones",false))
        list.add(SelectMemberView(url, "7Micheal Jackson",false))
        list.add(SelectMemberView("8Caroline Dhavernas", "8Caroline Dhavernas",false))
        list.add(SelectMemberView(url, "9 Vahid",false))
        list.add(SelectMemberView("10 Mohammad", "10 Mohammad",false))
        list.add(SelectMemberView("21 Hossein", "21 Hossein", false))
        return list
    }
}