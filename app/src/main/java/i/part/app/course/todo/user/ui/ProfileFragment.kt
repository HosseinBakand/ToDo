package i.part.app.course.todo.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.material.appbar.MaterialToolbar
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation
import i.part.app.course.todo.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_profile.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Profile : Fragment() {

    // Obtain ViewModel from ViewModelProviders
    private val userViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        val view = binding.root
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments

        binding.userViewModel = userViewModel
        // Inflate the layout for this fragment
        val transformer = PicassoCircleTransformation()
        val profilePhoto = view.findViewById<ImageView>(R.id.iv_profile_photo)
        val plusSign = view.findViewById<ImageView>(R.id.iv_profile_photo_plus_sign)
        Picasso
            .get()
            .load(userViewModel.profilePhotoUrl)
            .transform(transformer)
            .into(profilePhoto)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.mt_profile)

        toolbar.setNavigationOnClickListener {
            //            //            Toast.makeText(
////                view.context,
////                "---back---",
////                Toast.LENGTH_SHORT
////            ).show()
//
//
//           // activity?.supportFragmentManager?.popBackStack()
//            this.findNavController().navigate(R.id.action_addMember2_to_add_board)
            this.findNavController().navigate(R.id.action_profile_to_dashBoardFragment)
        }
        return view
    }
}
