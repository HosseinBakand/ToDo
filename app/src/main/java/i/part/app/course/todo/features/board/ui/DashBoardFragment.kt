package i.part.app.course.todo.features.board.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.board.data.BoardResponse
import i.part.app.course.todo.features.board.data.TodoSpecification
import kotlinx.android.synthetic.main.fragment_dash_board.*

class DashBoardFragment : Fragment(), BoardRecyclerAdapter.MyCallback {

    private lateinit var myView: View
    private val boardViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity).get(DashBoardViewModel::class.java)
        }
    }

    lateinit var mAdapter: BoardRecyclerAdapter
    var layoutManager: RecyclerView.LayoutManager? = null

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_fragment_menu, menu)
        setHasOptionsMenu(true)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.profileItem -> {
                Toast.makeText(context, "profileItem", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.logOutItem -> {
                Toast.makeText(context, "logoutItem", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        myView = inflater.inflate(R.layout.fragment_dash_board, container, false)
        return myView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        rv_boards.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        rv_boards.layoutManager = layoutManager
        mAdapter = BoardRecyclerAdapter(Picasso.get(), this)
        rv_boards.adapter = mAdapter
        boardViewModel?.getBoards()
        observeList()
        iv_dash_board_custom_menu_button.setOnClickListener {
            val wrapper = ContextThemeWrapper(context, R.style.popupmenu)
            val popup = PopupMenu(wrapper, iv_dash_board_anchor_for_menu, Gravity.END)
            popup.menuInflater.inflate(R.menu.dashboard_fragment_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                if (item.title == "Profile") {
                    myView.findNavController()
                        .navigate(R.id.action_dashBoardFragment_to_profile)
                } else if (item.title == "Log out") {
                    myView.findNavController()
                        .navigate(R.id.action_dashBoardFragment_to_loginFragment)
                }
                true
            }
            popup.show()
        }

        val floatingActionButton =
            myView.findViewById<FloatingActionButton>(R.id.fab_dash_board_fragment)
        floatingActionButton.setOnClickListener {
            myView.findNavController().navigate(R.id.action_dashBoardFragment_to_add_board)
        }

        boardViewModel?.isBoardUpdated?.observe(this, Observer {
            boardViewModel?.getBoards()
            observeList()
        })
        sr_dashboard.setOnRefreshListener {
            boardViewModel?.getBoards()
            val handler = Handler()
            handler.postDelayed({
                if (sr_dashboard.isRefreshing) {
                    sr_dashboard.isRefreshing = false
                }
                observeList()
            }, 500)
        }
        super.onActivityCreated(savedInstanceState)
    }

    private fun observeList() {
        boardViewModel?.boardList?.observe(this, Observer { boardList ->
            val lastNum: Int = mAdapter.itemCount
            when (boardList) {
                is Result.Success -> {
                    boardListToBoardViewList(boardList.data)
//                    mAdapter.submitList(boardListToBoardViewList(boardList.data))
                    val listNum = boardList.data?.size
                    if (listNum == 0) ll_dash_board_empty_state.visibility = View.VISIBLE
                    else ll_dash_board_empty_state.visibility = View.GONE
                    if (listNum == lastNum + 1) {
                        rv_boards.smoothScrollToPosition(lastNum)
                    }
                }
                is Result.Error -> {
                    showSnackBar(
                        myView,
                        boardList.message,
                        Snackbar.LENGTH_INDEFINITE,
                        "Connection"
                    )
                }
                is Result.Loading -> {
                    Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                }
            }
        })
    }

    override fun deleteBoard(item: BoardView) {
        val dialog = Dialog(context)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_delete_page)
        dialog.setCanceledOnTouchOutside(false)
        val okButton = dialog.findViewById<TextView>(R.id.tv_ok_button)
        okButton.setOnClickListener {
            boardViewModel?.removeBoard(item)
            boardViewModel?.removeBoardLiveData?.observe(this, Observer {
                when (it) {
                    is Result.Success -> {
                        boardViewModel?.updateBoardStatus()
                    }
                    is Result.Error -> {
                        showSnackBar(
                            myView,
                            it.message,
                            Snackbar.LENGTH_INDEFINITE,
                            "ERROR"
                        )
                    }
                    is Result.Loading -> {
                        Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                    }
                }
            })
            dialog.dismiss()
        }
        val closeButton = dialog.findViewById<TextView>(R.id.tv_cancel_button)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showSnackBar(view: View, message: String, duration: Int, type: String) {
        val snackBar = Snackbar.make(view, message, duration)
        snackBar.setActionTextColor(Color.YELLOW)
        snackBar.setAction("Refresh") {
            boardViewModel?.getBoards()
        }
        snackBar.show()
    }

    private fun extractBoardDetails(list: List<TodoSpecification>): MutableMap<Int, Triple<Int, Int, Int>> {
        val todoListMap = mutableMapOf<Int, Triple<Int, Int, Int>>()
        list.forEach {
            var remainingTasksCount = 0
            it.tasks.forEach { task ->
                if (!task.done)
                    remainingTasksCount++
            }
            if (it.todo.board_id in todoListMap.keys) {

                todoListMap[it.todo.board_id] = Triple(
                    todoListMap[it.todo.board_id]?.first?.plus(1) ?: 0
                    , todoListMap[it.todo.board_id]?.second?.plus(it.tasks.size) ?: 0
                    , todoListMap[it.todo.board_id]?.third?.plus(remainingTasksCount) ?: 0
                )
            } else {
                todoListMap[it.todo.board_id] = Triple(1, it.tasks.size, remainingTasksCount)
            }
        }
        return todoListMap
    }

    private fun boardListToBoardViewList(list: List<BoardResponse>?): MutableList<BoardView> {
        var todoListMap: MutableMap<Int, Triple<Int, Int, Int>>
        val viewList = mutableListOf<BoardView>()
        boardViewModel?.getCurrentTodos()
        boardViewModel?.getCurrentTodosLiveData?.observe(this, Observer {
            when (it) {
                is Result.Success -> {
                    it.data?.let { responseData ->
                        todoListMap = extractBoardDetails(responseData)
                        list?.forEach { boardResponse ->
                            viewList.add(
                                BoardView(
                                    id = boardResponse.id ?: -1,
                                    title = boardResponse.title ?: "",
                                    owner_name = boardResponse.owner_name ?: "",
                                    todo =
                                    if (todoListMap[boardResponse.id ?: -1]?.first != null)
                                        todoListMap[boardResponse.id ?: -1]?.first.toString()
                                    else "0",
                                    totalTasks =
                                    if (todoListMap[boardResponse.id ?: -1]?.second != null)
                                        todoListMap[boardResponse.id ?: -1]?.second.toString()
                                    else "0",
                                    remainingTasks =
                                    if (todoListMap[boardResponse.id ?: -1]?.third != null)
                                        todoListMap[boardResponse.id ?: -1]?.third.toString()
                                    else "0",
                                    status =
                                    when {
                                        todoListMap[boardResponse.id
                                            ?: -1]?.third == null -> BoardStatusEnum.Done
                                        todoListMap[boardResponse.id
                                            ?: -1]?.third != 0 -> BoardStatusEnum.ToDo
                                        else -> BoardStatusEnum.Done
                                    }
                                )
                            )
                        }
                    }
                    mAdapter.submitList(viewList)
                }
                is Result.Error -> {
                    Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                }
            }
        })
        return viewList
    }
}