package i.part.app.course.todo.features.board.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatImageButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.ItemAddMemberBinding


class AddMemberAdapter(list: MutableList<AddMemberItem>) :
    RecyclerView.Adapter<AddMemberAdapter.ViewHolder>() {
    private val list: MutableList<AddMemberItem> = list
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        lateinit var memberBinding: ItemAddMemberBinding
        context = parent.context
        memberBinding = DataBindingUtil.inflate(inflater, R.layout.item_add_member, parent, false)
        val myBindedView = memberBinding.root
        return ViewHolder(memberBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = list[position]
        holder.mbinding.inputItem = list[position]
        holder.minesButton.setOnClickListener {
            list.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, itemCount)

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
    
    inner class ViewHolder(val mbinding: ItemAddMemberBinding) :
        RecyclerView.ViewHolder(mbinding.root) {
        var minesButton: AppCompatImageButton =
            itemView.findViewById(R.id.btn_add_member_2_items_minus)

    }
}


