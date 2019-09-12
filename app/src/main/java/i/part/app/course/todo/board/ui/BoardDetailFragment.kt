package i.part.app.course.todo.board.ui

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R

class BoardDetailFragment : Fragment() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    val todoListViews: ArrayList<TodoListView> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val inflatedView = inflater.inflate(R.layout.fragment_board, container, false)
        recyclerView = inflatedView.findViewById<RecyclerView>(R.id.rv_board_fragment)

        recyclerView.let {
            it.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            todoListViews.apply {
                add(
                    TodoListView(
                        TodoListView.TODOLIST,
                        "todoList1",
                        true
                    )
                )
                add(
                    TodoListView(
                        TodoListView.TODOLIST,
                        "todoList2",
                        true
                    )
                )
                add(
                    TodoListView(
                        TodoListView.TODOLIST,
                        "thy",
                        false
                    )
                )
                add(
                    TodoListView(
                        TodoListView.TODOLIST,
                        "poker",
                        true
                    )
                )
                add(
                    TodoListView(
                        TodoListView.TODOLIST,
                        "joker",
                        false
                    )
                )
                add(
                    TodoListView(
                        TodoListView.TODOLIST,
                        "foster",
                        true
                    )
                )
                add(
                    TodoListView(
                        TodoListView.ADD_TODOLIST_BUTTON,
                        "button",
                        false
                    )
                )
            }
            context?.let { context ->
                it.adapter = TodoListRecyclerAdapter(context, todoListViews, Picasso.get())
            }
            recyclerView.adapter = it.adapter
        }

        val customMenuButton = inflatedView.findViewById<View>(R.id.iv_board_custom_menu_button)
        val anchorForMenu = inflatedView.findViewById<View>(R.id.iv_board_anchor_for_menu)
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
                    val fragmentType: String? = "board_detail"
                    val myBundle = bundleOf("fragmentType" to fragmentType)
                    inflatedView.findNavController()
                        .navigate(R.id.action_board_to_addMember2, myBundle)

                    true
                }
                popup.show()
            }
        }

        inflatedView.findViewById<View>(R.id.iv_edit_board_button)?.let {
            it.setOnClickListener {
                //                Toast.makeText(context,"Edit board",Toast.LENGTH_LONG)
//                    .show()
                inflatedView.findNavController().navigate(R.id.action_board_to_edit_board)

            }
        }

        val recycler = inflatedView.findViewById<RecyclerView>(R.id.rv_board_fragment)
        val snapHelper = LinearSnapHelper()
        recycler.clipToPadding =false
        val display = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        recycler.setPadding(50*display.widthPixels/1080,recycler.paddingTop,70*display.widthPixels/1080,recycler.paddingBottom)
        snapHelper.attachToRecyclerView(recyclerView)
        return inflatedView
    }

}