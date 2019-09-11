package i.part.app.course.todo.board.ui


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation


class AddMember3Adapter(
    private val context: Context,
    private val list: List<AddMember3Item>
) :
    RecyclerView.Adapter<AddMember3Adapter.ViewHolder1>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder1 {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_member_3, parent, false)
        return ViewHolder1(v)
    }

    override fun onBindViewHolder(holder: ViewHolder1, position: Int) {
        //holder.itemView.tag = list[position]

        val item = list[position]
        holder.bind(
            item
        )
        val pu = list[position]
        if (pu.imageUrl == "") pu.imageUrl = "salam"
        val objectFromPicasso = PicassoCircleTransformation()
        Picasso.get().load(pu.imageUrl).error(R.drawable.person_empty).transform(objectFromPicasso)
            .fit().into(holder.image)
        //set parametrs


    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder1(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: AppCompatImageView = itemView.findViewById(R.id.iv_add_member_3_items_image)
        var nameText: TextView = itemView.findViewById(R.id.tv_add_member_3_items_name)

        var checkbox: CheckBox =
            itemView.findViewById(R.id.cb_add_member_3)

        fun bind(item: AddMember3Item) {
            nameText.text = item.name
            checkbox.isChecked = item.ischeck

            checkbox.setOnCheckedChangeListener { _, isChecked -> item.ischeck = !isChecked }

            itemView.setOnClickListener {
                checkbox.isChecked = !checkbox.isChecked
                item.ischeck = !checkbox.isChecked
            }

        }

        init {


        }
    }
}


