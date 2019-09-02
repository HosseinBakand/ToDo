package i.part.app.course.todo.board.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.board.data.Task
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashBoardFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var recyclerView: RecyclerView
    lateinit var customMenuButton: ImageView
    lateinit var anchorForMenu: ImageView
    var mAdapter: RecyclerView.Adapter<*>? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    val myTasks: ArrayList<Task> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_fragment_menu, menu)
        setHasOptionsMenu(true)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profileItem -> {
                Toast.makeText(context, "profileItem", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.logOutItem -> {
                Toast.makeText(context, "logoutItem", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.fragment_dash_board, container, false)
        recyclerView = myView.findViewById(R.id.tasksRecyclerView) as RecyclerView
        customMenuButton = myView.findViewById(R.id.customMenuButton)
        anchorForMenu = myView.findViewById(R.id.anchorForMenu)
        customMenuButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val wrapper = ContextThemeWrapper(context, R.style.popupmenu)
                val popup = PopupMenu(wrapper, anchorForMenu, Gravity.END)
                popup.menuInflater.inflate(R.menu.dashboard_fragment_menu, popup.menu)
                popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem): Boolean {
                        Toast.makeText(
                            context,
                            " لمس شد" + item.title,
                            Toast.LENGTH_SHORT
                        ).show()
                        return true
                    }
                })
                popup.show()
            }
        })


        recyclerView.let { it.setHasFixedSize(true) }
        layoutManager = LinearLayoutManager(context)
        recyclerView.let { it.layoutManager = layoutManager }

        myTasks.add(Task("board1", 8, 118, 43, "done"))
        myTasks.add(Task("board2", 6, 118, 54, "todo"))
        myTasks.add(Task("board3", 7, 118, 12, "todo"))
        myTasks.add(Task("board4", 81, 118, 498, "done"))
        myTasks.add(Task("board5", 12, 118, 34, "done"))
        myTasks.add(Task("board6", 55, 118, 23, "todo"))
        mAdapter = BoardRecyclerAdapter(context!!, myTasks, Picasso.get())
        recyclerView.let { it.adapter = mAdapter }
        return myView


    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
//            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
