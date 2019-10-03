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
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.board.data.BoardMemberResponse
import kotlinx.android.synthetic.main.fragment_add_member.*


class AddMember : Fragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var mAdapter: AddMemberAdapter
    private lateinit var layoutManager: RecyclerView.LayoutManager
    var tempView: MutableList<AddMemberView> = mutableListOf()
    lateinit var list: ArrayList<AddMemberView>
    var boardID = 0
    var boardName = ""
    private val addMemberViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity).get(AddMemberViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
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
        arguments?.let {
            boardID = it.getInt("boardID")
            if (it.getString("boardName") != null) {
                boardName = it.getString("boardName")
            }
        }
        //View Model
        //addMemberViewModel?.loadBoardMember(boardID)


//        addMemberViewModel?.contactList2?.observe(viewLifecycleOwner, Observer {
//            when (it) {
//                is Result.Success -> {
//                    it.data
//                    val templist: List<BoardMemberResponse>? = it.data?.result
//                    templist?.let {
//                        for (i in 0..templist.size - 1) {
//                            tempView.add(AddMemberView(templist[i].profile_pic, templist[i].name))
//                        }
//                        mAdapter.submitList(tempView)
//                    }
//                }
//            }
//
//        })
        mAdapter = AddMemberAdapter { addMemberView ->
            addMemberViewModel?.removeMember(addMemberView)
        }
        recyclerView.adapter = mAdapter
        addMemberViewModel?.isMembersUpdated?.observe(viewLifecycleOwner, Observer {
            addMemberViewModel?.loadBoardMember(boardID)
            addMemberViewModel?.contactList2?.observe(viewLifecycleOwner, Observer {
                when (it) {
                    is Result.Success -> {
                        it.data
                        val templist: List<BoardMemberResponse>? = it.data?.result
                        templist?.let {
                            tempView = mutableListOf()
                            for (i in 0..templist.size - 1) {
                                tempView.add(
                                    AddMemberView(
                                        templist[i].profile_pic,
                                        templist[i].name
                                    )
                                )
                            }
                            mAdapter.submitList(tempView)
                        }
                    }
                }

            })
        })

        addMemberViewModel?.memberList?.observe(this, Observer {
            mAdapter.submitList(it)
        })
        btn_add_member.setOnClickListener {
            addMemberViewModel?.setChosenContacts()
            val myBundle = Bundle()
            arguments?.let {
                myBundle.putInt("boardID", it.getInt("boardID"))
            }
            this.findNavController()
                .navigate(R.id.action_addMember2_to_addMember3Fragment, myBundle)

        }
        mt_add_member_2.setNavigationOnClickListener {
            when (arguments?.getString("fragmentType")) {
                "add_board" -> this.findNavController().navigate(
                    R.id.action_addMember2_to_add_board
                )
                "edit_board" -> {
                    val myBundle = Bundle()
                    myBundle.putString("boardName", boardName)
                    myBundle.putInt("boardID", boardID)
                    this.findNavController().navigate(
                        R.id.action_addMember2_to_edit_board
                        , myBundle
                    )
                }
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
