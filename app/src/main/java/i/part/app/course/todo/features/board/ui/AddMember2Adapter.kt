package i.part.app.course.todo.features.board.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageButton
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation


class AddMember2Adapter(context: Context, personUtils: List<AddMember2Item>) :
    RecyclerView.Adapter<AddMember2Adapter.ViewHolder>() {
    private val context: Context = context
    private val list: List<AddMember2Item> = personUtils

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_add_member_2, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = list[position]

        val pu = list[position]
        //holder.image.setBackgroundColor(Color.BLACK)
        //set parametrs
        val objectFromPicasso = PicassoCircleTransformation()
        Picasso.get().load(pu.imageUrl).error(R.drawable.person_empty).transform(objectFromPicasso)
            .fit().into(holder.image)
//        val builder = Picasso.Builder(context)
//        builder.listener { picasso, uri, exception ->
//            Log.e("a",exception.message)}
//        builder.build().load(ul).into(holder.image)
        //Picasso.Builder

        holder.nameText.text = pu.name

    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var image: AppCompatImageView = itemView.findViewById(R.id.iv_add_member_2_items_image)
        var nameText: TextView = itemView.findViewById(R.id.tv_add_member_2_items_name)
        var minesButton: AppCompatImageButton =
            itemView.findViewById(R.id.btn_add_member_2_items_minus)

        init {


//            itemView.setOnClickListener { view ->
//                val cpu = view.tag as AddMember2Item
//
//                Toast.makeText(
//                    view.context,
//                    cpu.name + " is ",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }

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


