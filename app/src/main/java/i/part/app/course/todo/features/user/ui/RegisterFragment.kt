package i.part.app.course.todo.features.user.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import i.part.app.course.todo.core.api.Result
import kotlinx.android.synthetic.main.dialog_login_progress_bar.*
import kotlinx.android.synthetic.main.fragment_register.*


class RegisterFragment : Fragment() {
    lateinit var myView: View
    lateinit var progressRegisterDialog: Dialog

    private val registerViewModel by lazy {
        ViewModelProviders.of(this).get(RegisterViewModel::class.java)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_register, container, false)
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        registerViewModel.registerState.observe(this, Observer {
            val registerState = it ?: return@Observer

            btn_register_confirm.isEnabled = registerState.isDataValid

            if (registerState.emailError != null) {
                til_register_email.error = registerState.emailError
            } else {
                til_register_email.error = null
            }
            if (registerState.phoneNumberError != null) {
                til_register_phone.error = registerState.phoneNumberError
            } else {
                til_register_phone.error = null
            }
            if (registerState.passError != null) {
                til_register_password.error = registerState.passError
            } else {
                til_register_password.error = null
            }
            if (registerState.confirmPassError != null) {
                til_profile_confirm_password.error = registerState.confirmPassError
            } else {
                til_profile_confirm_password.error = null
            }
        })

        tiet_register_email.setOnFocusChangeListener { _, _ ->
            registerViewModel.onDataChange(
                tiet_register_email.text.toString(),
                tiet_register_phone.text.toString(),
                tiet_register_password.text.toString(),
                tiet_register_confirm_password.text.toString()
            )

        }
        tiet_register_phone.setOnFocusChangeListener { _, _ ->
            registerViewModel.onDataChange(
                tiet_register_email.text.toString(),
                tiet_register_phone.text.toString(),
                tiet_register_password.text.toString(),
                tiet_register_confirm_password.text.toString()
            )

        }
        tiet_register_password.setOnFocusChangeListener { _, _ ->
            registerViewModel.onDataChange(
                tiet_register_email.text.toString(),
                tiet_register_phone.text.toString(),
                tiet_register_password.text.toString(),
                tiet_register_confirm_password.text.toString()
            )

        }

        tiet_register_confirm_password.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                registerViewModel.onDataChange(
                    tiet_register_email.text.toString(),
                    tiet_register_phone.text.toString(),
                    tiet_register_password.text.toString(),
                    tiet_register_confirm_password.text.toString()
                )
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })

        btn_register_confirm.setOnClickListener {
            showDialog()

            registerViewModel.clickOnRegisterButton(
                tiet_register_email.text.toString(),
                tiet_register_phone.text.toString(),
                tiet_register_password.text.toString()
            )

            registerViewModel.registerResult.observe(this, Observer {

                when (it) {
                    is Result.Success -> {
                        progressRegisterDialog.dismiss()
                        Toast.makeText(
                            context,
                            "Please Login with your new user",
                            Toast.LENGTH_SHORT
                        )
                            .show()
                        myView.findNavController()
                            .navigate(R.id.action_registerFragment_to_loginFragment)
                    }
                    is Result.Error -> {
                        if (it.message == "ConnectionError") {
                            progressRegisterDialog.dismiss()
                            showSnackbar(
                                myView,
                                "Please Check your network",
                                Snackbar.LENGTH_INDEFINITE,
                                "Connection"
                            )
                        } else if (it.message == "Exist") {
                            progressRegisterDialog.dismiss()
                            showSnackbar(myView, "Your email exist", Snackbar.LENGTH_LONG, "Exist")
                        }
                    }
                    is Result.Loading -> {
                    }
                }
            })
        }

        super.onActivityCreated(savedInstanceState)

    }

    private fun showDialog() {
        progressRegisterDialog = Dialog(context)
        progressRegisterDialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        progressRegisterDialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        progressRegisterDialog.setCanceledOnTouchOutside(false)
        progressRegisterDialog.setContentView(R.layout.dialog_login_progress_bar)
        progressRegisterDialog.show()
        progressRegisterDialog.ib_login_dialog_close.setOnClickListener { progressRegisterDialog.dismiss() }
    }

    fun showSnackbar(view: View, message: String, duration: Int, type: String) {
        if (type == "Connection") {
            val snackbar = Snackbar.make(view, message, duration)
            snackbar.setActionTextColor(Color.RED)
            snackbar.setAction("Try againg") {
                //try to recconect
                btn_register_confirm.performClick()
            }
            snackbar.show()
        } else if (type == "Exist") {
            val snackbar = Snackbar.make(view, message, duration)
            snackbar.show()
        }

    }
}