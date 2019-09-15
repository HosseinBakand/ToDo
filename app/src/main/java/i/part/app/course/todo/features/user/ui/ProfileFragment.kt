package i.part.app.course.todo.features.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation
import i.part.app.course.todo.databinding.FragmentProfileBinding

class Profile : Fragment() {

    // Obtain ViewModel from ViewModelProviders
    private val userViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //data binding
        val binding: FragmentProfileBinding =
            DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        val view = binding.root
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments
        userViewModel.profilePhotoUrl = "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"
        userViewModel.name = "Vahid Safari"
        userViewModel.email = "safarivahid132@gmail.com"
        userViewModel.phone = "09213421432"
        binding.userViewModel = userViewModel
        //toolbar
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        til_profile_email.setEndIconOnClickListener {
                tiet_profile_email.isEnabled = !tiet_profile_email.isEnabled
        }

        til_profile_phone.setEndIconOnClickListener {
            tiet_profile_phone.isEnabled = !tiet_profile_phone.isEnabled
        }

        mt_profile.setNavigationOnClickListener {
            this.findNavController().navigate(R.id.action_profile_to_dashBoardFragment)
        }
    }
}
