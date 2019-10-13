package i.part.app.course.todo.features.board.ui

import android.app.Dialog
import android.content.Context
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
import i.part.app.course.todo.features.board.data.BoardEntity
import i.part.app.course.todo.features.board.data.TodoListDto
import kotlinx.android.synthetic.main.fragment_dash_board.*

class DashBoardFragment : Fragment(), BoardRecyclerAdapter.MyCallback {

    private lateinit var myView: View
    private var boardList: List<BoardEntity>? = listOf<BoardEntity>()
    private val boardViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity).get(DashBoardViewModel::class.java)
        }
    }
    private val addMemberViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity).get(AddMemberViewModel::class.java)
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
        boardViewModel?.getCurrentTodos()
        boardViewModel?.getCurrentTodosLiveData?.observe(viewLifecycleOwner, Observer {
            val viewList = mutableListOf<BoardView>()
            it?.let { responseData ->
                val todoListMap = extractBoardDetails(responseData)
                boardList?.let { boardEntityList ->
                    for (boardEntity in boardEntityList) {
                        viewList.add(
                            BoardView(
                                id = boardEntity.id ?: -1,
                                title = boardEntity.title ?: "",
                                owner_name = boardEntity.owner_name ?: "",
                                todo =
                                if (todoListMap[boardEntity.id ?: -1]?.first != null)
                                    todoListMap[boardEntity.id ?: -1]?.first.toString()
                                else "0",
                                totalTasks =
                                if (todoListMap[boardEntity.id ?: -1]?.second != null)
                                    todoListMap[boardEntity.id ?: -1]?.second.toString()
                                else "0",
                                remainingTasks =
                                if (todoListMap[boardEntity.id ?: -1]?.third != null)
                                    todoListMap[boardEntity.id ?: -1]?.third.toString()
                                else "0",
                                status =
                                when {
                                    todoListMap[boardEntity.id
                                        ?: -1]?.third == null -> BoardStatusEnum.Done
                                    todoListMap[boardEntity.id
                                        ?: -1]?.third != 0 -> BoardStatusEnum.ToDo
                                    else -> BoardStatusEnum.Done
                                }
                            )
                        )
                    }
                }
            }
            mAdapter.submitList(viewList)

        })

        iv_dash_board_custom_menu_button.setOnClickListener {
            val wrapper = ContextThemeWrapper(context, R.style.popupmenu)
            val popup = PopupMenu(wrapper, iv_dash_board_anchor_for_menu, Gravity.END)
            popup.menuInflater.inflate(R.menu.dashboard_fragment_menu, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                if (item.title == "Profile") {
                    myView.findNavController()
                        .navigate(R.id.action_dashBoardFragment_to_profile)
                } else if (item.title == "Log out") {
                    val editor =
                        context?.getSharedPreferences("myPref", Context.MODE_PRIVATE)?.edit()
                    editor?.putBoolean("shouldLogin", true)
                    editor?.apply()
                    addMemberViewModel?.cleanAllDB()
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
            addMemberViewModel?.reSet()
            myView.findNavController().navigate(R.id.action_dashBoardFragment_to_add_board)
        }
        observeUpdated()

        sr_dashboard.setOnRefreshListener {
            boardViewModel?.getBoards()
            val handler = Handler()
            handler.postDelayed({
                if (sr_dashboard.isRefreshing) {
                    sr_dashboard.isRefreshing = false
                }
                observeRemoteResponse()
            }, 500)
        }

        super.onActivityCreated(savedInstanceState)
    }

    private fun observeUpdated() {
        boardViewModel?.isBoardUpdated?.observe(viewLifecycleOwner, Observer {
            boardViewModel?.getBoards()
        })
    }

    private fun observeRemoteResponse() {
        sr_dashboard.isRefreshing = true
        boardViewModel?.getBoardsFromRemoteLiveData?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Success -> {
                    sr_dashboard.isRefreshing = false
                }

                is Result.Error -> {
                    showSnackBar(myView, it.message, Snackbar.LENGTH_LONG)
                    sr_dashboard.isRefreshing = false
                }

                is Result.Loading -> {
                }
            }
        })
    }

    private fun observeList() {
        boardViewModel?.boardList?.observe(viewLifecycleOwner, Observer { bl ->
            val lastNum: Int = mAdapter.itemCount
            if (bl.isNotEmpty()) {
                boardList = bl
                boardViewModel?.getCurrentTodos()
            }
            val listNum = boardList?.size
            if (listNum == 0) ll_dash_board_empty_state.visibility = View.VISIBLE
            else ll_dash_board_empty_state.visibility = View.GONE
            if (listNum == lastNum + 1) {
                rv_boards.smoothScrollToPosition(lastNum + 1)
            }
        })
    }

    override fun deleteBoard(item: BoardView) {
        val dialog = Dialog(context)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val mes = "Are you sure you want to delete ${item.title}?"
        dialog.setContentView(R.layout.dialog_delete_page)
        val dbm = dialog.findViewById<TextView>(R.id.tv_delete_board_message)
        dbm.text = mes
        dialog.setCanceledOnTouchOutside(false)
        val okButton = dialog.findViewById<TextView>(R.id.tv_ok_button)
        okButton.setOnClickListener {
            boardViewModel?.removeBoard(item)
            boardViewModel?.removeBoardLiveData?.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Result.Success -> {
                        boardViewModel?.updateBoardStatus()
                        dialog.dismiss()
                    }
                    is Result.Error -> {
                        dialog.dismiss()
                        showSnackBar(
                            myView,
                            it.message,
                            Snackbar.LENGTH_LONG
                        )
                    }
                    is Result.Loading -> {
                    }
                }
            })
        }
        val closeButton = dialog.findViewById<TextView>(R.id.tv_cancel_button)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun showSnackBar(view: View, message: String, duration: Int) {
        val snackBar = Snackbar.make(view, message, duration)
        snackBar.setActionTextColor(resources.getColor(R.color.colorPrimary))
        snackBar.setAction("Try again") {
            boardViewModel?.getBoards()
        }
        snackBar.show()
    }

    private fun extractBoardDetails(list: List<TodoListDto>): MutableMap<Int, Triple<Int, Int, Int>> {
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

    override fun onDetach() {
        boardViewModel?.getBoardsFromRemoteLiveData?.removeObservers(viewLifecycleOwner)
        boardViewModel?.addBoardLiveData?.removeObservers(viewLifecycleOwner)
        boardViewModel?.boardList?.removeObservers(viewLifecycleOwner)
        super.onDetach()
    }
}