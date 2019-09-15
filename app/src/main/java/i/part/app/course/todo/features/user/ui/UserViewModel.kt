package i.part.app.course.todo.features.user.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class UserViewModel:ViewModel(){
    val user : MutableLiveData<UserView> = MutableLiveData()

    init {
        user.value = UserView(
            "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png",
            "Vahid Safari",
            "safarivahid132@gmail.com",
            "09213421432")
    }
}