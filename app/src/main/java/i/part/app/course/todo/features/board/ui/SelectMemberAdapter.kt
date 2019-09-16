package i.part.app.course.todo.features.board.ui


import android.view.LayoutInflater
import android.view.View
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
    lateinit var mBinding: ItemSelectMemberBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {

        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_select_member, parent, false)
        val myBindedView = mBinding.root
        return ViewHolder1(myBindedView)
    }

    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        val item = list[position]
        holder.bind(
            item
        )

        mBinding.allMember = item
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var checkbox: CheckBox =
            itemView.findViewById(R.id.cb_add_member_3)

        fun bind(item: SelectMemberItem) {
            checkbox.setOnCheckedChangeListener { _, isChecked -> item.ischeck = !isChecked }
            itemView.setOnClickListener {
                checkbox.isChecked = !checkbox.isChecked
                item.ischeck = !checkbox.isChecked
            }
        }

    }
}


