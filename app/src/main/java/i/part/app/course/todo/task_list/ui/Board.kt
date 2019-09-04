package i.part.app.course.todo.task_list.ui

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.task_list.data.TodoList

class Board : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val todoLists:ArrayList<TodoList> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.board_fragment, container, false)
        recyclerView = inflatedView.findViewById<RecyclerView>(R.id.board_fragment_recycler_view)
        recyclerView.let {
            it.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            todoLists.apply {
                add(TodoList("todoList1", true))
                add(TodoList("todoList2", true))
                add(TodoList("thy", false))
                add(TodoList("poker", true))
                add(TodoList("joker", false))
                add(TodoList("foster", true))
            }
            context?.let { con -> it.adapter = TodoListRecyclerAdapter(con, todoLists, Picasso.get()) }
            recyclerView.adapter = it.adapter
        }

        val customMenuButton = inflatedView.findViewById<View>(R.id.board_custom_menu_button)
        val anchorForMenu = inflatedView.findViewById<View>(R.id.board_anchor_for_Menu)
        customMenuButton?.setOnClickListener {
            val wrapper = ContextThemeWrapper(context, R.style.popupmenu)
            anchorForMenu?.let {
                val popup = PopupMenu(wrapper, anchorForMenu, Gravity.END)
                popup.menuInflater.inflate(R.menu.board_menu, popup.menu)
                popup.setOnMenuItemClickListener { item ->
                    Toast.makeText(
                        context,
                        "Some Text" + item.title,
                        Toast.LENGTH_SHORT
                    ).show()
                    true
                }
                popup.show()
            }
        }
        val recycler = inflatedView.findViewById<RecyclerView>(R.id.board_fragment_recycler_view)
        val snapHelper = LinearSnapHelper()
        recycler.clipToPadding =false
        recycler.setPadding(108,recycler.paddingTop,108,recycler.paddingBottom)
        snapHelper.attachToRecyclerView(recyclerView)
        return inflatedView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)






    }
}