package i.part.app.course.todo.board.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation
import android.widget.CompoundButton





class SubTaskRecyclerAdapter(
    private val context: Context,
    private val subtasks: MutableList<SubTaskView>,
    private val allTasksDone: TextView,
    private val picasso: Picasso
) :
RecyclerView.Adapter<SubTaskRecyclerAdapter.ViewHolder>() {
    lateinit var view:View
    var checkedTasks = 0

    init {
        subtasks.forEach {
            if(it.isCompleted)
                checkedTasks++
        }
    }




    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        internal val checkBox = itemView.findViewById<CheckBox>(R.id.cb_item_subtask)
        internal var assigneeProfilePhoto =
            itemView.findViewById<ImageView>(R.id.iv_item_task_description)
        internal val descriptionTextView =
            itemView.findViewById<TextView>(R.id.tv_item_subtask_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_subtask,parent,false)
            return ViewHolder(view)
        }

    override fun getItemCount() = subtasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = subtasks[position]
        holder.descriptionTextView.text = subtasks[position].subTaskDescription
        holder.checkBox.isChecked = subtasks[position].isCompleted

        holder.checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if (holder.checkBox.isChecked) {
                checkedTasks++
                if (checkedTasks == itemCount) {
                    allTasksDone.visibility = TextView.VISIBLE
                }
            } else {
                checkedTasks--
                allTasksDone.visibility = TextView.GONE
            }
            subtasks[position].isCompleted = holder.checkBox.isChecked
        }

        val currentItem = subtasks[position]
        val transformer = PicassoCircleTransformation()
        picasso.load(currentItem.imageUri).transform(transformer)
            .error(R.color.design_default_color_error)
            .into(holder.assigneeProfilePhoto)
    }
}