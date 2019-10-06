package i.part.app.course.todo.features.board.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.board.data.AddUserParam
import i.part.app.course.todo.features.board.data.BoardMemberResponse
import kotlinx.android.synthetic.main.dialog_select_member.*

class SelectMemberDialogFragment : DialogFragment() {
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var list: ArrayList<SelectMemberView>
    var tempView: MutableList<SelectMemberView> = mutableListOf()
    var boardId = 0
    var boardName: String = ""
    var newBoard = false
    private lateinit var mAdapter: SelectMemberAdapter
    private val addMemberViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity).get(AddMemberViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_select_member, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)
        super.onActivityCreated(savedInstanceState)
        rv_add_member_3.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        rv_add_member_3.layoutManager = layoutManager
        arguments?.let {
            it.getString("boardName", boardName)
            if (it.getInt("boardID") != 0) {
                boardId = it.getInt("boardID")
            } else {
                newBoard = it.getBoolean("newBoard")
            }
        }
        addMemberViewModel?.loadAllusers()
        mAdapter = SelectMemberAdapter()
        observeAllUsers()
        rv_add_member_3.adapter = mAdapter
        ib_add_member_3_close.setOnClickListener {
            this.findNavController().navigate(R.id.action_addMember3Fragment_to_addMember2)
        }
        btn_select_member_confirm.setOnClickListener {
            var myCheckedList: MutableList<String> = mutableListOf()
            for (myItem in tempView) {
                if (myItem.ischeck)
                    myCheckedList.add(myItem.name)
                //myCheckedList.add(myItem.name)
            }
            var addUserParam = AddUserParam(myCheckedList)
            if (newBoard) {
                addMemberViewModel?.setSelectedMembers(myCheckedList)
                //Toast.makeText(context, "Hi", Toast.LENGTH_LONG).show()
                val myBundle = Bundle()
                myBundle.putString("boardName", boardName)
                myBundle.putInt("boardID", boardId)
                this.findNavController()
                    .navigate(
                        R.id.action_addMember3Fragment_to_addMember2,
                        myBundle
                    )
            } else {
                addMemberViewModel?.addUsersToBoard(boardId, addUserParam)
                observeAddUserToBoard()
            }
            //addMemberViewModel?.setMembers(mAdapter.getItems())
        }
        tiet_search_member.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                val start = tiet_search_member.text.toString()
                val list = tempView
                val newList = mutableListOf<SelectMemberView>()
                list.forEach {
                    if(it.name.startsWith(start,true)){
                        newList.add(it)
                    }
                }
                mAdapter.submitList(newList)
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
        })
    }

    private fun observeAddUserToBoard() {
        addMemberViewModel?.addUserToBoardResponse?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Success -> {
                    if (it.data?.message.equals("added member")) {
                        val myBundle = Bundle()
                        myBundle.putString("boardName", boardName)
                        myBundle.putInt("boardID", boardId)
                        addMemberViewModel?.updateMemberStatus()
                        this.findNavController()
                            .navigate(
                                R.id.action_addMember3Fragment_to_addMember2,
                                myBundle
                            )
                    }
                }
                is Result.Error -> {
                }
            }
        })
    }

    private fun observeAllUsers() {
        addMemberViewModel?.allUsers?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Success -> {
                    it.data
                    val templist : List<BoardMemberResponse>? = it.data?.toList()
                    templist?.let {
                        tempView = mutableListOf()
                        for (i in 0..templist.size - 1) {
                            tempView.add(
                                SelectMemberView(
                                    templist[i].profile_pic,
                                    templist[i].name,
                                    false,
                                    templist[i].id
                                )
                            )
                        }
                        deleteMutalMember()
                        mAdapter.submitList(tempView)
                    }
                }
            }
        })
    }

    private fun deleteMutalMember() {
        addMemberViewModel?.let {
            for (alreadyMember in it.alreadMember) {
                for (newMembers in tempView) {
                    if (alreadyMember.id == newMembers.id && alreadyMember.name == newMembers.name) {
                        tempView.remove(newMembers)
                        break
                    }
                }
            }
        }
    }
}