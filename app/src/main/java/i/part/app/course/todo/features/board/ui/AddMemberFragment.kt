package i.part.app.course.todo.features.board.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R
import kotlinx.android.synthetic.main.fragment_add_member.*


class AddMember : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: AddMemberAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var list: ArrayList<AddMemberView>
    private val addMemberViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(it).get(AddMemberViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_add_member, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView = rv_add_member_2_new_members
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager

        //View Model
        addMemberViewModel?.getMembers()
        mAdapter = AddMemberAdapter { addMemberView ->
            addMemberViewModel?.removeMember(addMemberView)
        }
        recyclerView.adapter = mAdapter
        addMemberViewModel?.memberList?.observe(this, Observer {
            mAdapter.submitList(it)
        })
        btn_add_member.setOnClickListener {
            addMemberViewModel?.setChosenContacts()
            this.findNavController().navigate(R.id.action_addMember2_to_addMember3Fragment)
        }
        mt_add_member_2.setNavigationOnClickListener {
            when (arguments?.getString("fragmentType")) {
                "add_board" -> this.findNavController().navigate(
                    R.id.action_addMember2_to_add_board
                )
                "edit_board" -> this.findNavController().navigate(
                    R.id.action_addMember2_to_edit_board
                )
                "add_task" -> this.findNavController().navigate(
                    R.id.action_addMember2_to_addTaskFragment
                )
                "board_detail" -> this.findNavController().navigate(
                    R.id.action_addMember2_to_board_detail
                )
            }
        }
    }
}
