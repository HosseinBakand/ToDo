package i.part.app.course.todo.features.board.ui

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import java.util.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class DashBoardFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null
    lateinit var recyclerView: RecyclerView
    lateinit var customMenuButton: ImageView
    lateinit var ll_empty_stat: LinearLayout
    lateinit var anchorForMenu: ImageView
    var mAdapter: RecyclerView.Adapter<*>? = null
    var layoutManager: RecyclerView.LayoutManager? = null
    val myTaskViews: ArrayList<TaskView> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_fragment_menu, menu)
        setHasOptionsMenu(true)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.profileItem -> {
                Toast.makeText(context, "profileItem", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.logOutItem -> {
                Toast.makeText(context, "logoutItem", Toast.LENGTH_SHORT).show()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val myView = inflater.inflate(R.layout.fragment_dash_board, container, false)
        recyclerView = myView.findViewById(R.id.rv_boards) as RecyclerView
        customMenuButton = myView.findViewById(R.id.iv_dash_board_custom_menu_button)
        anchorForMenu = myView.findViewById(R.id.iv_dash_board_anchor_for_menu)
        ll_empty_stat = myView.findViewById(R.id.ll_dash_board_empty_state)
        customMenuButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val wrapper = ContextThemeWrapper(context, R.style.popupmenu)
                val popup = PopupMenu(wrapper, anchorForMenu, Gravity.END)
                popup.menuInflater.inflate(R.menu.dashboard_fragment_menu, popup.menu)
                popup.setOnMenuItemClickListener(object : PopupMenu.OnMenuItemClickListener {
                    override fun onMenuItemClick(item: MenuItem): Boolean {
                        if (item.title == "Profile") {
                            myView.findNavController()
                                .navigate(R.id.action_dashBoardFragment_to_profile)

                        } else if (item.title == "Log out") {
                            myView.findNavController()
                                .navigate(R.id.action_dashBoardFragment_to_loginFragment)

                        }
                        return true
                    }
                })
                popup.show()
            }
        })
        recyclerView.let { it.setHasFixedSize(true) }
        layoutManager = LinearLayoutManager(context)
        recyclerView.let { it.layoutManager = layoutManager }
        myTaskViews.add(
            TaskView(
                "board1",
                "8",
                "118",
                "43",
                "done",
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        myTaskViews.add(
            TaskView(
                "board2",
                "6",
                "118",
                "54",
                "todo",
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        myTaskViews.add(
            TaskView(
                "board3",
                "7",
                "118",
                "12",
                "todo",
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        myTaskViews.add(
            TaskView(
                "board4",
                "81",
                "118",
                "498",
                "done",
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        myTaskViews.add(
            TaskView(
                "board5",
                "12",
                "118",
                "34",
                "done",
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        myTaskViews.add(
            TaskView(
                "board6",
                "55",
                "118",
                "23",
                "todo",
                "https://images-na.ssl-images-amazon.com/images/I/71QMsWSZqaL._SL1152_.jpg"
            )
        )
        context?.let { mAdapter = BoardRecyclerAdapter(it, myTaskViews, Picasso.get()) }
        if (mAdapter?.itemCount == 0) {
            ll_empty_stat.visibility = View.VISIBLE
        }
        recyclerView.let { it.adapter = mAdapter }
        val floatingActionButton =
            myView.findViewById<FloatingActionButton>(R.id.fab_dash_board_fragment)
        floatingActionButton.setOnClickListener {
            myView.findNavController().navigate(R.id.action_dashBoardFragment_to_add_board)
        }
        return myView


    }

    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }


    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            DashBoardFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
