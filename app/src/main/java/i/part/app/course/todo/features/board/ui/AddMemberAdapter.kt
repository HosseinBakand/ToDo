package i.part.app.course.todo.features.board.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.ItemAddMemberBinding


class AddMemberAdapter(list: List<AddMemberItem>) :
    RecyclerView.Adapter<AddMemberAdapter.ViewHolder>() {
    private val list: List<AddMemberItem> = list
    lateinit var memberBinding: ItemAddMemberBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        memberBinding = DataBindingUtil.inflate(inflater, R.layout.item_add_member, parent, false)
        val myBindedView = memberBinding.root
        return ViewHolder(myBindedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = list[position]
        memberBinding.inputItem = list[position]

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

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var minesButton: AppCompatImageButton =
            itemView.findViewById(R.id.btn_add_member_2_items_minus)

        init {
            minesButton.setOnClickListener { view ->
                Toast.makeText(
                    view.context,
                    " ---delete-item--- ",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
    }
}


