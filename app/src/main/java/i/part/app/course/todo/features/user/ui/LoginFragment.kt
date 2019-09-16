package i.part.app.course.todo.features.user.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import i.part.app.course.todo.R

class LoginFragment : Fragment() {
    lateinit var myView: View
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_login, container, false)
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val registerButton =
            myView.findViewById<MaterialTextView>(R.id.tv_login_register_new_account)
        registerButton.setOnClickListener {
            myView.findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }

        val loginButton = myView.findViewById<MaterialButton>(R.id.btn_login)
        loginButton.setOnClickListener {
            myView.findNavController().navigate(R.id.action_loginFragment_to_dashBoardFragment)
        }
        super.onActivityCreated(savedInstanceState)
    }
}
