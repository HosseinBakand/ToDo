package i.part.app.course.todo.features.board.ui

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import kotlinx.android.synthetic.main.fragment_board.*

class BoardDetailFragment : Fragment() {

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private val todoListViews: ArrayList<TodoListView> = ArrayList()
    private lateinit var inflatedView: View
    private val fakeLink: String =
        "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflatedView = inflater.inflate(R.layout.fragment_board, container, false)
        return inflatedView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        rv_board_fragment.let {
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            todoListViews.clear()
            todoListViews.apply {
                add(
                    TodoListView(
                        TodoListType.TODOLIST,
                        "poker",
                        mutableListOf(
                            SubTaskView("this is a good subtask", fakeLink),
                            SubTaskView("this is a nasty subtask", fakeLink),
                            SubTaskView("todo", fakeLink),
                            SubTaskView("nice subtask!", fakeLink)
                        )
                    )
                )
                add(
                    TodoListView(
                        TodoListType.TODOLIST,
                        "joker",
                        mutableListOf(
                            SubTaskView("this is a good subtask", fakeLink),
                            SubTaskView("todo", fakeLink),
                            SubTaskView("nice subtask!", fakeLink)
                        )
                    )
                )
                add(
                    TodoListView(
                        TodoListType.TODOLIST,
                        "stalker",
                        mutableListOf(
                            SubTaskView("this is a good subtask", fakeLink),
                            SubTaskView("this is a nasty subtask", fakeLink),
                            SubTaskView("todo", fakeLink)
                        )
                    )
                )
                add(
                    TodoListView(
                        TodoListType.TODOLIST,
                        "walker",
                        mutableListOf(
                            SubTaskView("this is a good subtask", fakeLink),
                            SubTaskView("nice subtask!", fakeLink)
                        )
                    )
                )
                add(
                    TodoListView(
                        TodoListType.TODOLIST,
                        "nasty jobs todolist",
                        mutableListOf(
                            SubTaskView("this is a good subtask", fakeLink),
                            SubTaskView("this is a nasty subtask", fakeLink),
                            SubTaskView("Good one", fakeLink),
                            SubTaskView("harsh one", fakeLink),
                            SubTaskView("hard one", fakeLink),
                            SubTaskView("impossible one", fakeLink),
                            SubTaskView("nice subtask!", fakeLink)
                        )
                    )
                )
                add(
                    TodoListView(
                        TodoListType.TODOLIST,
                        "kill bill",
                        mutableListOf(
                            SubTaskView("this is a good subtask", fakeLink),
                            SubTaskView("this is a nasty subtask", fakeLink),
                            SubTaskView("todo", fakeLink),
                            SubTaskView("nice subtask!", fakeLink)
                        )
                    )
                )
                add(
                    TodoListView(
                        TodoListType.ADD_TODOLIST_BUTTON,
                        "button",
                        mutableListOf()
                    )
                )
            }
            context?.let { context ->
                it.adapter = TodoListRecyclerAdapter(todoListViews, Picasso.get())
            }
            rv_board_fragment.adapter = it.adapter
        }
        iv_board_custom_menu_button?.setOnClickListener {
            val wrapper = ContextThemeWrapper(context, R.style.popupmenu)
            iv_board_anchor_for_menu?.let {
                val popup = PopupMenu(wrapper, it, Gravity.END)
                popup.menuInflater.inflate(R.menu.board_menu, popup.menu)
                popup.setOnMenuItemClickListener {
                    val fragmentType: String? = "board_detail"
                    val myBundle = bundleOf("fragmentType" to fragmentType)
                    inflatedView.findNavController()
                        .navigate(R.id.action_board_to_addMember2, myBundle)
                    true
                }
                popup.show()
            }
        }

        iv_edit_board_button?.let {
            it.setOnClickListener {
                inflatedView.findNavController().navigate(R.id.action_board_to_edit_board)

            }
        }
        val snapHelper = LinearSnapHelper()
        rv_board_fragment.clipToPadding = false
        val display = DisplayMetrics()
        activity?.windowManager?.defaultDisplay?.getMetrics(display)
        rv_board_fragment.setPadding(
            50 * display.widthPixels / 1080,
            rv_board_fragment.paddingTop,
            70 * display.widthPixels / 1080,
            rv_board_fragment.paddingBottom
        )
        snapHelper.attachToRecyclerView(rv_board_fragment)

        mt_board.setNavigationOnClickListener {
            this.findNavController().navigate(R.id.action_board_to_dashBoardFragment)
        }
    }
}