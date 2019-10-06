package i.part.app.course.todo.features.user.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import com.google.android.material.snackbar.Snackbar
import i.part.app.course.todo.MyApplication
import i.part.app.course.todo.R
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.user.data.LoginParam
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

        loginViewModel.loginState.observe(this, Observer {
            val loginState = it ?: return@Observer
            btn_login.isEnabled = loginState.isDataValid
            if (loginState.emailError != null) {
                til_login_email.error = loginState.emailError
            } else {
                til_login_email.error = null
            }
            if (loginState.passError != null) {
                til_login_password.error = loginState.passError
            } else {
                til_login_password.error = null
            }
        })




        tv_login_register_new_account.setOnClickListener {
            myView.findNavController()
                .navigate(R.id.action_loginFragment_to_registerFragment)
        }
        btn_login.setOnClickListener {
            showDialog()
            val myLoginParam =
                LoginParam(tiet_login_email.text.toString(), tiet_login_password.text.toString())
            loginViewModel.login(myLoginParam)
            loginViewModel.loginResult.observe(this, Observer {


                //TODO:remove this section for final version
                //start
//                progressLoginDialog.dismiss()
//                myView.findNavController()
//                    .navigate(R.id.action_loginFragment_to_dashBoardFragment)
                //end


                //TODO:Uncomment this section for final version
                //start
                when (it) {
                    is Result.Success -> {
                        progressLoginDialog.dismiss()
                        myView.findNavController()
                            .navigate(R.id.action_loginFragment_to_dashBoardFragment)
                        it.data?.let { itdata ->
                            itdata.token.let { itdatatoken ->
                                saveToken(itdatatoken)
                            }
                        }

                    }
                    is Result.Error -> {
                        if (it.message == "Password Not Verified") {
                            progressLoginDialog.dismiss()
                            showSnackbar(
                                myView,
                                "Check your password",
                                Snackbar.LENGTH_LONG
                            )
                        } else if (it.message == "Not Found") {
                            progressLoginDialog.dismiss()
                            showSnackbar(myView, "User not found!", Snackbar.LENGTH_LONG)
                        } else if (it.message == "ConnectionError") {
                            progressLoginDialog.dismiss()
                            showSnackbar(myView, "Check your network", Snackbar.LENGTH_LONG)
                        }
                    }
                    is Result.Loading -> {

                    }
                }

                //end

            })
        }
        tiet_login_email.setOnFocusChangeListener { _, _ ->
            loginViewModel.onDataChanged(
                tiet_login_email.text.toString(),
                tiet_login_password.text.toString()
            )
        }
        tiet_login_password.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                loginViewModel.onDataChanged(
                    tiet_login_email.text.toString(),
                    tiet_login_password.text.toString()
                )
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        super.onActivityCreated(savedInstanceState)
    }

    private fun showDialog() {
        progressLoginDialog = Dialog(context)
        progressLoginDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        progressLoginDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressLoginDialog.setCanceledOnTouchOutside(false)
        progressLoginDialog.setContentView(R.layout.dialog_login_progress_bar)
        progressLoginDialog.show()
        progressLoginDialog.ib_login_dialog_close.setOnClickListener { progressLoginDialog.dismiss() }
    }

    fun showSnackbar(view: View, message: String, duration: Int) {
        val snackbar = Snackbar.make(view, message, duration)
        snackbar.setActionTextColor(Color.RED)
        snackbar.setAction("Try againg") {
            //try to recconect
        }
        snackbar.show()
    }


    fun saveToken(token: String) {
        val editor = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)?.edit()
        editor?.putString("myToken", token)
        editor?.apply()
        MyApplication.TOKEN = token
    }
}
