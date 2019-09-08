package i.part.app.course.todo.task_list.ui

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.board.data.TodoList

class Board : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val todoLists:ArrayList<TodoList> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.board_fragment, container, false)
        recyclerView = inflatedView.findViewById<RecyclerView>(R.id.board_fragment_recycler_view)
        recyclerView.let {
            it.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            todoLists.apply {
                add(TodoList(TodoList.TODOLIST,"todoList1", true))
                add(TodoList(TodoList.TODOLIST,"todoList2", true))
                add(TodoList(TodoList.TODOLIST,"thy", false))
                add(TodoList(TodoList.TODOLIST,"poker", true))
                add(TodoList(TodoList.TODOLIST,"joker", false))
                add(TodoList(TodoList.TODOLIST,"foster", true))
                add(TodoList(TodoList.ADD_TODOLIST_BUTTON,"button",false))
            }
            context?.let { context -> it.adapter = TodoListRecyclerAdapter(context, todoLists, Picasso.get()) }
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
                    //                    Toast.makeText(
//                        context,
//                        "Some Text" + item.title,
//                        Toast.LENGTH_SHORT
//                    ).show()
                    inflatedView.findNavController().navigate(R.id.action_board_to_addMember2)

                    true
                }
                popup.show()
            }
        }

        inflatedView.findViewById<View>(R.id.btn_edit_board)?.let {
            it.setOnClickListener {
                //                Toast.makeText(context,"Edit board",Toast.LENGTH_LONG)
//                    .show()
                inflatedView.findNavController().navigate(R.id.action_board_to_edit_board)

            }
        }

        val recycler = inflatedView.findViewById<RecyclerView>(R.id.board_fragment_recycler_view)
        val snapHelper = LinearSnapHelper()
        recycler.clipToPadding =false
        val display = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        recycler.setPadding(50*display.widthPixels/1080,recycler.paddingTop,70*display.widthPixels/1080,recycler.paddingBottom)
        snapHelper.attachToRecyclerView(recyclerView)
        return inflatedView
    }

}