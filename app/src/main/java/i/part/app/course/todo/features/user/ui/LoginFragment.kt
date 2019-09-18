package i.part.app.course.todo.features.user.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import i.part.app.course.todo.R
import kotlinx.android.synthetic.main.dialog_login_progress_bar.*
import kotlinx.android.synthetic.main.fragment_login.*


class LoginFragment : Fragment() {
    lateinit var myView: View
    lateinit var progressLoginDialog: Dialog
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_login, container, false)
        return myView
    }

    private val loginViewModel by lazy {
        ViewModelProviders.of(this).get(LoginViewModel::class.java)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        loginViewModel.status.observe(this, Observer {
            if (it.equals("success")) {
                progressLoginDialog.dismiss()
                myView.findNavController()
                    .navigate(R.id.action_loginFragment_to_dashBoardFragment)
                Toast.makeText(context, "Welcome!!", Toast.LENGTH_LONG).show()
            } else {
                showSnackbar(myView, "Please Check your network", Snackbar.LENGTH_INDEFINITE)
            }

        })
        tv_login_register_new_account.setOnClickListener {
            myView.findNavController()
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }
        btn_login.setOnClickListener {

            LoginView(
                tiet_login_email.text.toString(),
                tiet_login_password.text.toString()
            ).apply {
                if (loginViewModel.validateEmail(this) && loginViewModel.validatePassword(this)) {
                    loginViewModel.loading()
                    progressLoginDialog = Dialog(context)
                    progressLoginDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
                    progressLoginDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                    progressLoginDialog.setCanceledOnTouchOutside(false)
                    progressLoginDialog.setContentView(R.layout.dialog_login_progress_bar)
                    progressLoginDialog.show()
                    progressLoginDialog.ib_login_dialog_close.setOnClickListener { progressLoginDialog.dismiss() }
                } else {
                    if (!loginViewModel.validateEmail(this)) {
                        til_login_email.error = "Wrong email format"
                    }
                    if (!loginViewModel.validatePassword(this)) {
                        til_login_password.error = "Wrong password format"
                    }
                }
            }
        }
        super.onActivityCreated(savedInstanceState)
    }

    fun showSnackbar(view: View, message: String, duration: Int) {
        val snackbar = Snackbar.make(view, message, duration)
        snackbar.setActionTextColor(Color.RED)
        snackbar.setAction("Try againg") {
            //try to recconect
        }

        snackbar.show()
    }
}
