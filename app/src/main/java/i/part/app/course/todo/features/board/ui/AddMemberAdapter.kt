package i.part.app.course.todo.features.board.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.ItemAddMemberBinding

private object AddMemberAdapterCallback : DiffUtil.ItemCallback<AddMemberView>() {
    override fun areItemsTheSame(oldItem: AddMemberView, newItem: AddMemberView): Boolean {
        return (oldItem.hashCode() == newItem.hashCode())
    }

    override fun areContentsTheSame(oldItem: AddMemberView, newItem: AddMemberView): Boolean {
        return (oldItem.name == newItem.name && oldItem.imageUrl == newItem.imageUrl)
    }
}

class AddMemberAdapter(
    val removeMember: (AddMemberView) -> Unit
) : ListAdapter<AddMemberView, AddMemberAdapter.ViewHolder>(AddMemberAdapterCallback) {
    var context: Context? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        lateinit var memberBinding: ItemAddMemberBinding
        context = parent.context
        memberBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.item_add_member,
            parent,
            false
        )
        return ViewHolder(memberBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.itemView.tag = item
        holder.binding.inputItem = item
        holder.binding.btnAddMember2ItemsMinus.setOnClickListener {
            removeMember(item)
        }
    }

    inner class ViewHolder(val binding: ItemAddMemberBinding) :
        RecyclerView.ViewHolder(binding.root)
}