package i.part.app.course.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import i.part.app.course.todo.features.user.ui.LoginFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(
            R.id.nav_host_fragment, LoginFragment()
        ).commit()

    }
}
