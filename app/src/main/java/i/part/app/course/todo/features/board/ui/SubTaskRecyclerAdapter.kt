package i.part.app.course.todo.features.board.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.ItemSubtaskBinding


class SubTaskRecyclerAdapter(
    private val subtasks: MutableList<SubTaskView>,
    private val allTasksDone: TextView,
    val callBack: MyTaskListCallback
) :
    RecyclerView.Adapter<SubTaskRecyclerAdapter.ViewHolder>() {
    lateinit var view:View
    var checkedTasks = 0

    lateinit var mBinding: ItemSubtaskBinding


    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {

        internal val checkBox = itemView.findViewById<CheckBox>(R.id.cb_item_subtask)
        internal var assigneeProfilePhoto =
            itemView.findViewById<ImageView>(R.id.iv_item_task_description)
        internal val descriptionTextView =
            itemView.findViewById<TextView>(R.id.tv_item_subtask_description)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_subtask, parent, false)
        val myBindedView = mBinding.root
        return ViewHolder(myBindedView)
        }

    override fun getItemCount() = subtasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        mBinding.subtask =subtasks[position]
        holder.checkBox.setOnCheckedChangeListener { _, _ ->
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
            callBack.checkTask(
                subtasks[position].id,
                holder.checkBox.isChecked,
                subtasks[position].subTaskDescription
            )
        }
    }

    interface MyTaskListCallback {
        fun checkTask(taskId: Int, state: Boolean, description: String)
    }
}