package i.part.app.course.todo.features.board.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import i.part.app.course.todo.R
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.board.data.AddTaskParam
import kotlinx.android.synthetic.main.dialog_add_to_do_list.*
import kotlinx.android.synthetic.main.dialog_edit_todolist_name.*
import kotlinx.android.synthetic.main.fragment_board.*


class BoardDetailFragment : Fragment(), TodoListRecyclerAdapter.MyTodoListCallback {

    lateinit var dialog: Dialog
    val boardId = 311
    private val todoListViews: ArrayList<TodoListView> = ArrayList()
    private val viewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity)[TodoListViewModel::class.java]
        }
    }

    var scrollToFirst = false
    private lateinit var inflatedView: View
    private val fakeLink: String =
        "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"
    lateinit var adapter: TodoListRecyclerAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        inflatedView = inflater.inflate(R.layout.fragment_board, container, false)
        return inflatedView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        mt_board.title = arguments?.getString("boardName")
        viewModel?.getTodoLists(boardId)
        adapter = TodoListRecyclerAdapter(this)
        rv_board_fragment.adapter = adapter

        observeTodoList()
        rv_board_fragment.let {
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            todoListViews.clear()
            viewModel?.getTodoLists(boardId)
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

        sr_todo_list.setOnRefreshListener {
            viewModel?.getTodoLists(boardId)
            val handler = Handler()
            handler.postDelayed(Runnable {
                if (sr_todo_list.isRefreshing) {
                    sr_todo_list.isRefreshing = false
                }
                observeTodoList()
            }, 500)
        }

        iv_edit_board_button?.let {
            it.setOnClickListener {
                inflatedView.findNavController().navigate(R.id.action_board_to_edit_board)

            }
        }
        viewModel?.addTaskChanged?.observe(this, Observer {
            observeAddTask()

        })
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

    override fun addTodoList() {
        val dialog = Dialog(context)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_add_to_do_list)
        dialog.setCanceledOnTouchOutside(false)
        val confirmButton = dialog.findViewById<MaterialButton>(R.id.btn_add_todolist_confirm)
        confirmButton?.setOnClickListener {
            viewModel?.addTodoList(dialog.et_add_todolist_name.text.toString(), boardId)
            observeTodoAdd()
            dialog.dismiss()
        }
        confirmButton.onEditorAction(EditorInfo.IME_ACTION_DONE)
        val closeButton = dialog.findViewById<ImageButton>(R.id.ib_add_todolist_close)
        closeButton?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show() //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTask(id: Int) {
        //inflatedView.findNavController().navigate(R.id.action_board_to_addTaskFragment)
        val myBundle = Bundle()
        myBundle.putInt("TaskID", id)
        inflatedView.findNavController().navigate(R.id.action_board_to_addTaskFragment, myBundle)
    }

    override fun checkTask(taskId: Int, state: Boolean, description: String) {
        //inflatedView.findNavController().navigate(R.id.action_board_to_addTaskFragment)
        viewModel?.editTask(taskId, AddTaskParam(state, description))
    }

    override fun editTodoListName(todoListView: TodoListView) {
        dialog = Dialog(context)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_edit_todolist_name)
        dialog.setCanceledOnTouchOutside(false)
        dialog.et_edit_todolist_name.setText(todoListView.todoListName)
        dialog.et_edit_todolist_name.setSelectAllOnFocus(true)
        dialog.et_edit_todolist_name.setTextColor(resources.getColor(R.color.black))
        dialog.window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        val okButton = dialog.findViewById<MaterialButton>(R.id.btn_edit_todolist_confirm)
        okButton?.setOnClickListener {
            viewModel?.editToDoListName(
                todoListView.id,
                dialog.et_edit_todolist_name.text.toString()
            )
            observeEditTodoName()
            dialog.dismiss()
            if (todoListView == adapter.getItemView(0)) scrollToFirst = true
        }
        val closeButton = dialog.findViewById<ImageButton>(R.id.ib_edit_todolist_close)
        closeButton?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun observeTodoList() {
        sr_todo_list.isRefreshing = true
        viewModel?.todoLists?.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    val list = it.data?.result?.map { item -> item.toTodoListView() }
                    adapter.submitList(list as MutableList<TodoListView>)
//                    adapter.notifyDataSetChanged()
                    if (scrollToFirst) {
                        rv_board_fragment.smoothScrollToPosition(0)
                        scrollToFirst = false
                    }
                    sr_todo_list.isRefreshing = false
                }
                is Result.Error -> {
                    showSnackBar(inflatedView, "Please Check your network", Snackbar.LENGTH_LONG)
                    sr_todo_list.isRefreshing = false
                }
            }

        })
    }

    private fun observeTodoAdd() {
        viewModel?.addTodoListResponse?.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> {
                    viewModel?.getTodoLists(boardId)
                    observeTodoList()
                }
                is Result.Error -> {
                    Toast.makeText(context, "problem occurred", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun observeEditTodoName() {
        viewModel?.editTodoListResponse?.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> {
                    viewModel?.getTodoLists(boardId)
                    observeTodoList()
                }
                is Result.Error -> {
                    Toast.makeText(context, "problem occurred", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    private fun observeAddTask() {

        viewModel?.addTask?.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> {
                    viewModel?.getTodoLists(boardId)
                    observeTodoList()
                }
                is Result.Error -> {
                    Toast.makeText(context, "problem occurred", Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                }
            }
        })
    }

    private fun showSnackBar(view: View, message: String, duration: Int) {
        val snackbar = Snackbar.make(view, message, duration)
        snackbar.setActionTextColor(Color.RED)
        snackbar.setAction("Try againg") {
            //try to reconnect
            sr_todo_list.performClick()
            viewModel?.getTodoLists(boardId)
        }
        snackbar.show()


    }

}