package i.part.app.course.todo.features.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.FragmentProfileBinding
import kotlinx.android.synthetic.main.fragment_profile.*
import android.widget.Toast


class ProfileFragment : Fragment() {
    lateinit var binding: FragmentProfileBinding
    private val userViewModel by lazy {
        ViewModelProviders.of(this).get(UserViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //data binding
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this  // use Fragment.viewLifecycleOwner for fragments
        userViewModel.getUser()
        binding.userView = userViewModel.user.value
        userViewModel.user.observe(this, Observer {
            binding.userView = it
        })
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
