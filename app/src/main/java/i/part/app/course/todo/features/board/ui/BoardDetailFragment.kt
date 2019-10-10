package i.part.app.course.todo.features.board.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import com.google.android.material.snackbar.Snackbar
import i.part.app.course.todo.R
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.board.data.UpdateTaskParam
import kotlinx.android.synthetic.main.dialog_add_to_do_list.*
import kotlinx.android.synthetic.main.dialog_edit_todolist_name.*
import kotlinx.android.synthetic.main.fragment_board.*

class BoardDetailFragment : Fragment(), TodoListRecyclerAdapter.MyTodoListCallback {

    lateinit var dialog: Dialog
    lateinit var boardOwner: String
    private var boardId: Int = 0
    private val boardViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity).get(DashBoardViewModel::class.java)
        }
    }


    val todoListViews: ArrayList<TodoListView> = ArrayList()
    private val viewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity)[TodoListViewModel::class.java]
        }
    }

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
        boardId = arguments?.getInt("boardID") ?: -1
        mt_board.title = ""
        boardId.let {
            boardViewModel?.getBoardById(boardId)
            observeGetBoard()

        }

        observeTodoList()

        viewModel?.getTodoLists(boardId)
        viewModel?.todoListDataBase?.observe(this, Observer { todoListDto ->
            adapter.submitList(todoListDto.map { it.toTodoListView() } as MutableList<TodoListView>)
//            if (scrollToFirst) {
//                rv_board_fragment.smoothScrollToPosition(0)
//                scrollToFirst = false
//            }
        })
        adapter = TodoListRecyclerAdapter(this)
        rv_board_fragment.adapter = adapter

        rv_board_fragment.let {
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            todoListViews.clear()
            observeTodoList()
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
                    myBundle.putInt("boardID", boardId)
                    inflatedView.findNavController()
                        .navigate(R.id.action_board_to_addMember2, myBundle)
                    true
                }
                popup.show()
            }
        }
        sr_todo_list.setOnRefreshListener {
            val handler = Handler()
            handler.postDelayed({
                if (sr_todo_list.isRefreshing) {
                    sr_todo_list.isRefreshing = false
                }
                observeTodoList()
            }, 500)
        }

        iv_edit_board_button?.let {
            it.setOnClickListener {
                val myBundle = Bundle()
                myBundle.putString("boardName", mt_board.title.toString())
                myBundle.putString("ownerName", boardOwner)
                myBundle.putInt("boardID", boardId)
                inflatedView.findNavController().navigate(R.id.action_board_to_edit_board, myBundle)
            }
        }
        viewModel?.addTaskChanged?.observe(this, Observer {
            //            observeAddTask()
            observeTodoList()
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
        super.onActivityCreated(savedInstanceState)
    }

    private fun observeGetBoard() {
        boardViewModel?.getBoardByIdLiveData?.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    mt_board.title = it.data?.result?.get(0)?.title
                    boardOwner = it.data?.result?.get(0)?.owner_name ?: ""
                }
                is Result.Error -> {
                    showSnackBar(
                        inflatedView,
                        it.message,
                        Snackbar.LENGTH_LONG,
                        "ERROR"
                    )
                }
                is Result.Loading -> {
                }
            }
        })
    }

    private fun showSnackBar(view: View, message: String, duration: Int, type: String) {
        val snackBar = Snackbar.make(view, message, duration)
        snackBar.setActionTextColor(Color.YELLOW)
        snackBar.setAction("Refresh") {
            boardViewModel?.getBoards()
        }
        snackBar.show()
    }

    override fun addTodoList() {
        val dialog = Dialog(context)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_add_to_do_list)
        dialog.setCanceledOnTouchOutside(false)

        val confirmButton =
            dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btn_add_todolist_confirm)
        confirmButton?.setOnClickListener {
            if (dialog.et_add_todolist_name.text.toString() == "") {
//                Toast.makeText(context, "your task should have name", Toast.LENGTH_SHORT).show()
                dialog.et_add_todolist_name.error = "your task should have name"
            } else {
                viewModel?.addTodoList(dialog.et_add_todolist_name.text.toString(), boardId)
                dialog.btn_add_todolist_confirm.setBackgroundResource(R.drawable.dialog_button_round_down)

                val btn =
                    dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btn_add_todolist_confirm)
                bindProgressButton(btn)
                btn.showProgress {
                    progressColor = Color.BLACK
                }

                Handler().postDelayed({
                    observeTodoAdd(btn, dialog)


                }, 500)
            }
        }
        confirmButton.onEditorAction(EditorInfo.IME_ACTION_DONE)
        val closeButton = dialog.findViewById<ImageButton>(R.id.ib_add_todolist_close)
        closeButton?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show() //To change body of created functions use File | Settings | File Templates.
    }

    override fun addTask(id: Int, boardId: Int) {
        //inflatedView.findNavController().navigate(R.id.action_board_to_addTaskFragment)
        val myBundle = Bundle()
        myBundle.putInt("TaskID", id)
        myBundle.putInt("boardID", boardId)
        inflatedView.findNavController().navigate(R.id.action_board_to_addTaskFragment, myBundle)
    }

    override fun checkTask(taskId: Int, state: Boolean, description: String) {
        //inflatedView.findNavController().navigate(R.id.action_board_to_addTaskFragment)
        viewModel?.editTask(taskId, UpdateTaskParam(state, description))
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
        dialog.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        val okButton =
            dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btn_edit_todolist_confirm)
        okButton?.setOnClickListener {
            if (dialog.et_edit_todolist_name.text.toString() == "") {
                dialog.et_edit_todolist_name?.error = "your task should have name"

            } else {
                dialog.et_edit_todolist_name?.error = null
                viewModel?.editToDoListName(
                    todoListView.id,
                    dialog.et_edit_todolist_name.text.toString()
                )
                val btn =
                    dialog.findViewById<androidx.appcompat.widget.AppCompatButton>(R.id.btn_edit_todolist_confirm)
                bindProgressButton(btn)
                btn.showProgress {
                    progressColor = Color.BLACK
                }

                Handler().postDelayed({
                    observeEditTodoName(btn)
                }, 500)
            }
        }
        val closeButton = dialog.findViewById<ImageButton>(R.id.ib_edit_todolist_close)
        closeButton?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun observeTodoList() {
        viewModel?.loadTodoList(boardId)
        sr_todo_list.isRefreshing = true
        viewModel?.todoLists?.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    sr_todo_list.isRefreshing = false
                }
                is Result.Error -> {
                    showSnackBar(inflatedView, "Please Check your network", Snackbar.LENGTH_LONG)
                    sr_todo_list.isRefreshing = false
                }
            }

        })

    }

    private fun observeTodoAdd(btn: androidx.appcompat.widget.AppCompatButton, thisDialog: Dialog) {
        viewModel?.addTodoListResponse?.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> {
                    context?.let {
                        val animatedDrawable =
                            ContextCompat.getDrawable(context as Context, R.drawable.animated_check)
                        animatedDrawable?.setBounds(0, 0, 75, 75)
                        animatedDrawable?.let { drawable ->
                            btn.showDrawable(drawable)
                        }
                    }

                    btn.attachTextChangeAnimator {
                        fadeOutMills = 100
                        fadeInMills = 100
                    }
                    btn.setBackgroundResource(R.drawable.dialog_button_round_down_green)

                    val h = Handler()
                    h.postDelayed({

                        thisDialog.dismiss()
                        observeTodoList()

                    }, 500)
                }
                is Result.Error -> {
                    btn.showProgress {
                        progressColor = Color.TRANSPARENT
                    }
                    btn.setBackgroundResource(R.drawable.dialog_button_round_down_red)
                    btn.text = "No Internet"
                }
            }
        })
    }

    private fun observeEditTodoName(btn: androidx.appcompat.widget.AppCompatButton) {
        viewModel?.editTodoListResponse?.observe(this, Observer { result ->
            when (result) {
                is Result.Success -> {
                    context?.let {
                        val animatedDrawable =
                            ContextCompat.getDrawable(context as Context, R.drawable.animated_check)
                        animatedDrawable?.setBounds(0, 0, 75, 75)
                        animatedDrawable?.let { drawable ->
                            btn.showDrawable(drawable)
                        }
                    }

                    btn.attachTextChangeAnimator {
                        fadeOutMills = 100
                        fadeInMills = 100
                    }
                    btn.setBackgroundResource(R.drawable.dialog_button_round_down_green)

                    val h = Handler()
                    h.postDelayed({

                        dialog.dismiss()
                        observeTodoList()

                    }, 500)
                }
                is Result.Error -> {
                    btn.showProgress {
                        progressColor = Color.TRANSPARENT
                    }
                    btn.setBackgroundResource(R.drawable.dialog_button_round_down_red)
                    btn.text = "No Internet"
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
            observeTodoList()
        }
        snackbar.show()


    }

}