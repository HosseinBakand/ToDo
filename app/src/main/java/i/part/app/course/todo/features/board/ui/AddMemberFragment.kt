package i.part.app.course.todo.features.board.ui


import android.os.Bundle
import android.util.Log
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
import i.part.app.course.todo.features.board.data.BoardMemberEntity
import i.part.app.course.todo.features.board.data.MemberOfBoardEntity
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
        addMemberViewModel?.loadBoardMember(boardID)


        observeContactList2()
        mAdapter = AddMemberAdapter { addMemberView ->
            if (boardID != 0) {
                addMemberViewModel?.removeMember(boardID, addMemberView)
            } else {
                addMemberViewModel?.removeTempMember(addMemberView)
            }
            observeRemoveMemberResponse()
        }
        recyclerView.adapter = mAdapter
        observeSelectedMembers()

        observeIsMemberUpdated()

        addMemberViewModel?.memberList?.observe(this, Observer {
            mAdapter.submitList(it)
        })
        btn_add_member.setOnClickListener {
            //TODO: addMemberViewModel?.setChosenContacts()
            val myBundle = Bundle()
            arguments?.let {
                myBundle.putString("boardName", boardName)
                if (it.getInt("boardID") != 0) {
                    myBundle.putInt("boardID", it.getInt("boardID"))
                } else {
                    myBundle.putBoolean("newBoard", true)
                }
            }
            this.findNavController()
                .navigate(R.id.action_addMember2_to_addMember3Fragment, myBundle)

        }
        mt_add_member_2.setNavigationOnClickListener {
            when (arguments?.getString("fragmentType")) {
                //addmember to addboardFragment
                "add_board" -> {
                    val myBundle = Bundle()
                    myBundle.putString("boardName", boardName)
                    this.findNavController().navigate(
                        R.id.action_addMember2_to_add_board, myBundle
                    )
                }
                "edit_board" -> {
                    val myBundle = Bundle()
                    myBundle.putString("boardName", boardName)
                    myBundle.putInt("boardID", boardID)
                    this.findNavController().navigate(
                        R.id.action_addMember2_to_edit_board, myBundle
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

    private fun observeSelectedMembers() {
        addMemberViewModel?.selectedMembers?.observe(viewLifecycleOwner, Observer {
            val fakeLink =
                "https://www.shareicon.net/download/2016/05/24/770136_man_512x512.png"
            tempView = mutableListOf()
            for (i in it) {
                tempView.add(
                    AddMemberView(
                        fakeLink,
                        i
                    )
                )
            }
            if (arguments?.getString("fragmentType").equals("add_board")) {
                mAdapter.submitList(tempView)
            }

        })
    }

    private fun observeContactList2() {
        addMemberViewModel?.callGetBoardUser(boardID)?.observe(viewLifecycleOwner, Observer {
            val templist: List<MemberOfBoardEntity>? = it
            templist?.let {
                tempView = mutableListOf()
                addMemberViewModel?.alreadMember?.clear()
                for (i in 0..templist.size - 1) {
                    addMemberViewModel?.alreadMember?.add(
                        BoardMemberEntity(
                            templist[i].MemberId,
                            "imge_avr",
                            "",
                            "",
                            ""
                        )
                    )
                    tempView.add(AddMemberView(templist[i].MemberName, templist[i].MemberName))
                }
                mAdapter.submitList(tempView)
            }
        })
        addMemberViewModel?.contactList2?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Success -> {

//                    val templist: List<BoardMemberEntity>? = it.data?.result
//                    templist?.let {
//                        tempView = mutableListOf()
//                        addMemberViewModel?.alreadMember?.clear()
//                        for (i in 0..templist.size - 1) {
//                            addMemberViewModel?.alreadMember?.add(templist[i])
//                            tempView.add(AddMemberView(templist[i].profile_pic, templist[i].name))
//                        }
//
//                        mAdapter.submitList(tempView)
//                    }
                }
            }

        })
    }

    private fun observeIsMemberUpdated() {
        addMemberViewModel?.isMembersUpdated?.observe(viewLifecycleOwner, Observer {
            addMemberViewModel?.loadBoardMember(boardID)
            observeContactList2()
        })
    }

    private fun observeRemoveMemberResponse() {
        addMemberViewModel?.removeMemberFromBoardResponse?.observe(
            viewLifecycleOwner,
            Observer {
                when (it) {
                    is Result.Success -> {
                        addMemberViewModel?.loadBoardMember(boardID)
                        addMemberViewModel?.alreadMember?.clear()
                        addMemberViewModel?.contactList2?.observe(viewLifecycleOwner, Observer {
                            when (it) {
                                is Result.Success -> {
//
//                                    val templist: List<BoardMemberEntity>? = it.data?.result
//                                    templist?.let {
//                                        tempView = mutableListOf()
//                                        for (i in 0..templist.size - 1) {
//                                            addMemberViewModel?.alreadMember?.add(templist[i])
//                                            tempView.add(
//                                                AddMemberView(
//                                                    templist[i].profile_pic,
//                                                    templist[i].name
//                                                )
//                                            )
//                                        }
//                                        mAdapter.submitList(tempView)
//                                        //mAdapter.notifyDataSetChanged()
//                                    }
                                }
                            }

                        })
                    }
                    is Result.Error -> {
                        Log.e("", "")
                    }
                }
            })
    }

}
