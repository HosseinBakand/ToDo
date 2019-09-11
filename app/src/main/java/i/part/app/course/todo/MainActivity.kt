package i.part.app.course.todo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import i.part.app.course.todo.user.ui.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment, LoginFragment()
        ).commit()

    }

    @SuppressLint("ResourceType")
    private fun frag() {

        //editNameDialogFragment.show(fm, "fragment_edit_name")
    }



}
