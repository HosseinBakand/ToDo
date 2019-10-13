package i.part.app.course.todo.core.util.ui

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import i.part.app.course.todo.R
import kotlinx.android.synthetic.main.fragment_splash_screen.*


class SplashScreenFragment : Fragment() {
    lateinit var myView: View
    var shouldRun: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_splash_screen, container, false)
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        iv_pie_icon.startAnimation(
            AnimationUtils.loadAnimation(
                activity,
                R.anim.rotate_indefinitely
            )
        )
        val handler = Handler()
        handler.postDelayed({
            if (shouldLogin() && shouldRun) {
                shouldRun = false
                myView.findNavController()
                    .navigate(R.id.action_splashScreenFragment_to_loginFragment)
            } else {
                shouldRun = false
                myView.findNavController()
                    .navigate(R.id.action_splashScreenFragment_to_dashBoardFragment)
            }
        }, 2500)

    }

    private fun shouldLogin(): Boolean {
        val prefs = context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)
        if (prefs?.getBoolean("shouldLogin", true) == true) {
            return true
        } else if (prefs?.getBoolean("shouldLogin", true) == false) {
            return false
        }
        return true
    }
}