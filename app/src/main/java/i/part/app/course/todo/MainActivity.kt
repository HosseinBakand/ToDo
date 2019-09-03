package i.part.app.course.todo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import i.part.app.course.todo.board.ui.RegisterFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val register = RegisterFragment()
        fragmentTransaction.add(R.id.fragment_container, register)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}
