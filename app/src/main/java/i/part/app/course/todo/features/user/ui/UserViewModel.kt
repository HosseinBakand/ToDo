package i.part.app.course.todo.features.user.ui

import androidx.lifecycle.ViewModel


class UserViewModel:ViewModel() {
    lateinit var profilePhotoUrl : String
    lateinit var name : String
    lateinit var email : String
    lateinit var phone : String
}