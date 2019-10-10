package i.part.app.course.todo.features.board.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.ItemSelectMemberBinding


private object SelectSingleMemberAdapterCallback : DiffUtil.ItemCallback<SelectMemberView>() {
    override fun areItemsTheSame(oldItem: SelectMemberView, newItem: SelectMemberView): Boolean {
        return (oldItem.id == newItem.id)
    }

    override fun areContentsTheSame(oldItem: SelectMemberView, newItem: SelectMemberView): Boolean {
        return (oldItem.ischeck != newItem.ischeck)
    }
}

class SelectSingleMemberAdapter(val clickCallBack: ClickCallBack) :
    ListAdapter<SelectMemberView, SelectSingleMemberAdapter.ViewHolder>(
        SelectSingleMemberAdapterCallback
    ) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        lateinit var mBinding: ItemSelectMemberBinding

        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_select_member, parent, false)
        return ViewHolder(mBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.binding.allMember = item
        holder.binding.cbAddMember3.setOnClickListener {
            val tempList: MutableList<SelectMemberView> = mutableListOf()
            for (i in 0..itemCount - 1) {
                getItem(i).ischeck = (i == position)
                tempList.add(
                    SelectMemberView(
                        getItem(i).imageUrl,
                        getItem(i).name,
                        getItem(i).ischeck,
                        getItem(i).id
                    )
                )
            }
            clickCallBack.getList(tempList)
        }
//        holder.binding.cbAddMember3.setOnTouchListener { view, motionEvent ->
//
//          return@setOnTouchListener true
//        }


        holder.itemView.setOnClickListener {
            holder.binding.cbAddMember3.performClick()

        }
    }

    class ViewHolder(val binding: ItemSelectMemberBinding) :
        RecyclerView.ViewHolder(binding.root)


}


interface ClickCallBack {
    fun getBoardMember()
    fun getList(list: List<SelectMemberView>)
}