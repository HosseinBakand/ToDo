package i.part.app.course.todo.features.board.ui

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import kotlinx.android.synthetic.main.fragment_dash_board.*

class DashBoardFragment : Fragment(), BoardRecyclerAdapter.MyCallback {

    lateinit var myView: View
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
        super.onActivityCreated(savedInstanceState)
        rv_boards.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        rv_boards.layoutManager = layoutManager
        mAdapter = BoardRecyclerAdapter(Picasso.get(), this)
        rv_boards.adapter = mAdapter
        boardViewModel?.getBoards()
        boardViewModel?.boardList?.observe(this, Observer { boardList ->
            val lastNum: Int = mAdapter.itemCount
            mAdapter.submitList(boardList)
            val listNum = boardList.size
            if (listNum == 0) ll_dash_board_empty_state.visibility = View.VISIBLE
            else ll_dash_board_empty_state.visibility = View.GONE
            if (listNum == lastNum + 1) {
                rv_boards.smoothScrollToPosition(lastNum)
            }
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
    }

    override fun deleteBoard(item: BoardView) {
        var dialog = Dialog(context)
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_delete_page)
        dialog.setCanceledOnTouchOutside(false)
        val okButton = dialog.findViewById<TextView>(R.id.tv_ok_button)
        okButton.setOnClickListener {
            boardViewModel?.removeBoard(item)
            dialog.dismiss()
        }
        val closeButton = dialog.findViewById<TextView>(R.id.tv_cancel_button)
        closeButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}
