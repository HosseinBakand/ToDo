package i.part.app.course.todo


//import i.part.app.course.todo.board.ui.Add_board
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import i.part.app.course.todo.board.ui.DashBoardFragment
import i.part.app.course.todo.board.ui.Edit_board

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val register = DashBoardFragment()
        fragmentTransaction.add(R.id.fragment_container, register)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
        showEditDialog()
        //frag()

//        val manager = supportFragmentManager
//        val transaction = manager.beginTransaction()
//        transaction.add(R.id.main, AddMember2(), "first")
//        transaction.addToBackStack(null)
//        transaction.commit()


    }

    //
    private fun showEditDialog() {
        val fm = supportFragmentManager
        val editNameDialogFragment = Edit_board.newInstance("Some Title")
        editNameDialogFragment.show(fm, "fragment_edit_name")
    }

    @SuppressLint("ResourceType")
    private fun frag() {

        //editNameDialogFragment.show(fm, "fragment_edit_name")
    }



}
