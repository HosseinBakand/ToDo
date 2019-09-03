package i.part.app.course.todo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import i.part.app.course.todo.task_list.ui.Board

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, Board()
        ).commit()
    }
}
