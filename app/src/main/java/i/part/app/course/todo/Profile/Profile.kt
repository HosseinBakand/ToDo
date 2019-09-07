package i.part.app.course.todo

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ScrollView
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.marginTop
import androidx.core.widget.NestedScrollView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation
import kotlinx.android.synthetic.main.fragment_profile.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [Profile.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [Profile.newInstance] factory method to
 * create an instance of this fragment.
 */
class Profile : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        // Inflate the layout for this fragment
        val transformer = PicassoCircleTransformation()
        val profilePhoto = view.findViewById<ImageView>(R.id.profile_photo)
        val plusSign = view.findViewById<ImageView>(R.id.profilePhotoPlusSign)
        Picasso
            .get()
            .load("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png")
            .transform(transformer)
            .error(R.color.design_default_color_error)
            .into(profilePhoto)
//        Picasso
//            .get()
//            .load("https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png")
//            .transform(transformer)
//            .error(R.color.design_default_color_error)
//            .into(plusSign)

        return view
    }
}
