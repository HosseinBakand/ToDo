package i.part.app.course.todo.features.board.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.ItemSelectMemberBinding


class SelectMemberAdapter(
    private val list: List<SelectMemberItem>
) :
    RecyclerView.Adapter<SelectMemberAdapter.ViewHolder1>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {

        val inflater = LayoutInflater.from(parent.context)
        lateinit var mBinding: ItemSelectMemberBinding

        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_select_member, parent, false)
        val myBindedView = mBinding.root
        return ViewHolder1(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        val item = list[position]


        holder.checkbox.setOnCheckedChangeListener { _, isChecked -> item.ischeck = isChecked }
        holder.itemView.setOnClickListener {
            item.ischeck = !holder.checkbox.isChecked
            holder.checkbox.isChecked = !holder.checkbox.isChecked
        }

        holder.mbinding.allMember = item
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder1(val mbinding: ItemSelectMemberBinding) :
        RecyclerView.ViewHolder(mbinding.root) {

        var checkbox: CheckBox =
            itemView.findViewById(R.id.cb_add_member_3)


    }
}


