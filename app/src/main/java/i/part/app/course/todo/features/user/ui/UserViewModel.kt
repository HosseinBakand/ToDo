package i.part.app.course.todo.features.user.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class UserViewModel:ViewModel(){
    private val _user = MutableLiveData<UserView>()

    val user: LiveData<UserView>
        get() = _user

    fun getUser() {
        _user.value = loadUser()
    }

    private fun loadUser() : UserView {
        return UserView(
            "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png",
            "Mamad Bahadori",
            "dsavcdas@gmail.com",
            "09212314241"
        )
    }
}