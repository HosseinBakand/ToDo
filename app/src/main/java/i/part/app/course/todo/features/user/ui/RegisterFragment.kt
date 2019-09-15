package i.part.app.course.todo.features.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import i.part.app.course.todo.R

class RegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.fragment_register, container, false)
        val registerButton = myView.findViewById<MaterialButton>(R.id.btn_register_confirm)
        registerButton.setOnClickListener {
            myView.findNavController().navigate(R.id.action_registerFragment_to_dashBoardFragment)
        }
        return myView
    }
}
