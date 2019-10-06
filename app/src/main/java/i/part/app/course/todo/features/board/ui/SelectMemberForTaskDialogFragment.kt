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
import i.part.app.course.todo.features.board.data.BoardMemberResponse
import kotlinx.android.synthetic.main.dialog_select_member.*

class SelectMemberForTaskDialogFragment : DialogFragment(), ClickCallBack {
    lateinit var layoutManager: RecyclerView.LayoutManager
    var boardId = 0
    val tempView: MutableList<SelectMemberView> = mutableListOf()
    private lateinit var mAdapter: SelectSingleMemberAdapter
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
            boardId = it.getInt("boardID")
        }
        //addMemberViewModel?.loadBoardMember(boardId)
        mAdapter = SelectSingleMemberAdapter(this)
///
        addMemberViewModel?.contactList2?.observe(viewLifecycleOwner, Observer {
            when (it) {
                is Result.Success -> {
                    it.data
                    val templist: List<BoardMemberResponse>? = it.data?.result
                    templist?.let {
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
                        mAdapter.submitList(tempView)
                    }
                }
            }

        })
        rv_add_member_3.adapter = mAdapter
        ib_add_member_3_close.setOnClickListener {
            activity?.onBackPressed()
            // this.findNavController().navigate(R.id.action_selectMemberForTaskDialogFragment_to_addTaskFragment)
        }
        btn_select_member_confirm.setOnClickListener {
            //addMemberViewModel?.setMembers(mAdapter.getItems())
            for (i in 0..tempView.size - 1) {
                if (tempView.get(i).ischeck == true) {
                    val bundle = Bundle()
                    bundle.putString("todoListName", arguments?.get("todoListName") as String)
                    bundle.putString("userName", tempView.get(i).name)
                    bundle.putInt("boardID", arguments?.get("boardID") as Int)
                    bundle.putInt("TaskID", arguments?.get("TaskID") as Int)
                    bundle.putString("userImageUrl", tempView.get(i).imageUrl)
                    this.findNavController().navigate(
                        R.id.action_selectMemberForTaskDialogFragment_to_addTaskFragment,
                        bundle
                    )
                }
            }
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

    override fun getList(list: List<SelectMemberView>) {
        mAdapter.submitList(list)
        mAdapter.notifyDataSetChanged()
    }


    override fun getBoardMember() {
        //addMemberViewModel?.getMembers()
    }

}