package i.part.app.course.todo.task_list.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation
import i.part.app.course.todo.task_list.data.SubTask

class SubTaskRecyclerAdapter(private val context: Context, private val subtasks: List<SubTask>, private val picasso: Picasso):
RecyclerView.Adapter<SubTaskRecyclerAdapter.ViewHolder>() {
    lateinit var view:View

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        internal val checkBox = itemView.findViewById<CheckBox>(R.id.subtask_item_check_box)
        internal var asigneeProfilePhoto = itemView.findViewById<ImageView>(R.id.task_description_item_image_view)

        init {
            itemView.setOnClickListener {
                val subtask = it.tag as SubTask
                Toast.makeText(it.context,subtask.imageUri,Toast.LENGTH_LONG)
                    .show()
            }
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        view = LayoutInflater.from(parent.context)
            .inflate(R.layout.subtask_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount() = subtasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = subtasks[position]
        holder.checkBox.text = subtasks[position].subTaskDescription
        holder.checkBox.isChecked = subtasks[position].isCompleted
        val currentItem = subtasks[position]
        val transformer = PicassoCircleTransformation()
        picasso.load(currentItem.imageUri).transform(transformer).error(R.color.design_default_color_error)
            .into(holder.asigneeProfilePhoto)
    }
}