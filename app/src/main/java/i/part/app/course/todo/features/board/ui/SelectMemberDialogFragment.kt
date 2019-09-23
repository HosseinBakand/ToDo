package i.part.app.course.todo.features.board.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
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
import kotlinx.android.synthetic.main.dialog_select_member.*

class SelectMemberDialogFragment : DialogFragment() {
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var list: ArrayList<SelectMemberView>
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
        val mAdapter = SelectMemberAdapter()
        addMemberViewModel?.contactList?.observe(viewLifecycleOwner, Observer {
                mAdapter.submitList(it)
        })
        rv_add_member_3.adapter = mAdapter
        ib_add_member_3_close.setOnClickListener {
            this.findNavController().navigate(R.id.action_addMember3Fragment_to_addMember2)
        }
        btn_select_member_confirm.setOnClickListener {
            addMemberViewModel?.setMembers(mAdapter.getItems())
            this.findNavController().navigate(R.id.action_addMember3Fragment_to_addMember2)
        }
    }
}