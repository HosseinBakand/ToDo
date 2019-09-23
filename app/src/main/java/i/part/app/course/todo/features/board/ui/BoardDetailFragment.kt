package i.part.app.course.todo.features.board.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
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
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import i.part.app.course.todo.R
import kotlinx.android.synthetic.main.dialog_add_to_do_list.*
import kotlinx.android.synthetic.main.dialog_edit_todolist_name.*
import kotlinx.android.synthetic.main.fragment_board.*

class BoardDetailFragment : Fragment(), TodoListRecyclerAdapter.MyTodoListCallback {
    override fun addTodoList() {
        val dialog = Dialog(context)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_add_to_do_list)
        dialog.setCanceledOnTouchOutside(false)
        val confirmButton = dialog.findViewById<MaterialButton>(R.id.btn_add_todolist_confirm)
        confirmButton?.setOnClickListener {
            viewModel?.addTodoList(dialog.et_add_todolist_name.text.toString())
            dialog.dismiss()
        }
        confirmButton.onEditorAction(EditorInfo.IME_ACTION_DONE)
        val closeButton = dialog.findViewById<ImageButton>(R.id.ib_add_todolist_close)
        closeButton?.setOnClickListener {
            dialog.dismiss()
        }

        dialog.show() //To change body of created functions use File | Settings | File Templates.
    }

    lateinit var dialog: Dialog
    override fun addTask(position: Int) {
        //inflatedView.findNavController().navigate(R.id.action_board_to_addTaskFragment)
        val myBundle = Bundle()
        myBundle.putInt("toDoListViewPosition", position)
        inflatedView.findNavController().navigate(R.id.action_board_to_addTaskFragment, myBundle)
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
            viewModel?.editToDoListName(todoListView, dialog.et_edit_todolist_name.text.toString())
            dialog.dismiss()
        }
        val closeButton = dialog.findViewById<ImageButton>(R.id.ib_edit_todolist_close)
        closeButton?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
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
        viewModel?.getTodoLists()
        adapter = TodoListRecyclerAdapter(this)
        rv_board_fragment.adapter = adapter
        viewModel?.todolists?.observe(this, Observer { memberList ->
            adapter.submitList(memberList)
            val lastNum: Int = adapter.itemCount
            adapter.notifyDataSetChanged()
            //rv_board_fragment.smoothScrollToPosition(lastNum - 1)
        })
        rv_board_fragment.let {
            it.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            todoListViews.clear()
            viewModel?.getTodoLists()
//            viewModel?.todolists?.value?.let { list ->
//                context?.let { context ->
//                    it.adapter =
//                        TodoListRecyclerAdapter(this)
//                    adapter.submitList(list)
//                }
//            }
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