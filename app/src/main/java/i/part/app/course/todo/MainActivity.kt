package i.part.app.course.todo

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(
            R.id.fragment_container, Profile()
        ).commit()
    }

    @SuppressLint("ResourceType")
    private fun frag() {

        //editNameDialogFragment.show(fm, "fragment_edit_name")
    }



}
