package i.part.app.course.todo.features.board.ui


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.ItemSelectMemberBinding


private object SelectMemberAdapterCallback : DiffUtil.ItemCallback<SelectMemberView>(){
    override fun areItemsTheSame(oldItem: SelectMemberView, newItem: SelectMemberView): Boolean {
        return (oldItem.hashCode() == newItem.hashCode())
    }

    override fun areContentsTheSame(oldItem: SelectMemberView, newItem: SelectMemberView): Boolean {
        return (oldItem.imageUrl == newItem.imageUrl && oldItem.name == newItem.name && oldItem.ischeck == newItem.ischeck)
    }
}

class SelectMemberAdapter
    : ListAdapter<SelectMemberView, SelectMemberAdapter.ViewHolder>(SelectMemberAdapterCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        lateinit var mBinding: ItemSelectMemberBinding

        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_select_member, parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.allMember = item

        holder.binding.cbAddMember3.setOnCheckedChangeListener { _, checked ->
            item.ischeck = checked
        }

        holder.itemView.setOnClickListener {
            holder.binding.cbAddMember3.performClick()
        }
    }

    class ViewHolder(val binding: ItemSelectMemberBinding) :
        RecyclerView.ViewHolder(binding.root)

    fun getItems(): List<AddMemberView> {
        val list = mutableListOf<AddMemberView>()
        for (i in 0 until itemCount)
            if (getItem(i).ischeck)
                list.add(AddMemberView(getItem(i).imageUrl, getItem(i).name))
        return list
    }
}


