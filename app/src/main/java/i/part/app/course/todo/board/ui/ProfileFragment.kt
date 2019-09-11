package i.part.app.course.todo.board.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Profile : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
        val transformer = PicassoCircleTransformation()
        val profilePhoto = view.findViewById<ImageView>(R.id.iv_profile_photo)
        val plusSign = view.findViewById<ImageView>(R.id.iv_profile_photo_plus_sign)
//        Picasso
//            .get()
//            .load("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png")
//            .transform(transformer)
//            .into(profilePhoto)


        return view
    }
}
