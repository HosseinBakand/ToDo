package i.part.app.course.todo.user.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.ViewModel
import com.squareup.picasso.Picasso
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation



class UserViewModel:ViewModel() {
    lateinit var profilePhotoUrl : String
    lateinit var name : String
    lateinit var email : String
    lateinit var phone : String
}