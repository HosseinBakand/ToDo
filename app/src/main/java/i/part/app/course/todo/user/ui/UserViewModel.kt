package i.part.app.course.todo.user.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation



class UserViewModel:ViewModel() {
    val profilePhotoUrl = "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"
    var name = "Vahid Safari"
    var email = "vahidsafari132@gmail.com"
    var phone = "09124436345353"
    var password = "1234567"
}