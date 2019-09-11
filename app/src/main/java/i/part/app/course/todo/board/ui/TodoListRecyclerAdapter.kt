package i.part.app.course.todo.board.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R

class TodoListRecyclerAdapter(
    private val context: Context,
    private val todoListViews: List<TodoListView>,
    private val picasso: Picasso
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var view: View
    lateinit var todoListRecyclerView: RecyclerView

    inner class TodoListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var todoNameTextView =
            itemView.findViewById<MaterialTextView>(R.id.tv_todo_list_item_name)
        internal val editImageView =
            itemView.findViewById<ImageView>(R.id.iv_todo_list_item_edit)
        internal var allTasksDoneTextView =
            itemView.findViewById<MaterialTextView>(R.id.tv_todo_list_item_all_tasks_done)
        internal val subTaskRecyclerView =
            itemView.findViewById<RecyclerView>(R.id.rv_todo_list_item_sub_task)
        internal val addTaskButton =
            itemView.findViewById<MaterialButton>(R.id.btn_todo_list_item_add_task)

        init {
            itemView.setOnClickListener { view ->
                val taskTag = view.tag as TodoListView
                Toast.makeText(
                    view.context,
                    "${taskTag.todoListName} is ${taskTag.isCompleted}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            editImageView.setOnClickListener {
                val dialog = Dialog(context)
                dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.edit_todolist_name)
                dialog.setCanceledOnTouchOutside(false)
                val okButton = dialog.findViewById<MaterialButton>(R.id.btn_edit_todolist_confirm)
                okButton?.setOnClickListener {
                    dialog.dismiss()
                }
                val closeButton = dialog.findViewById<ImageButton>(R.id.ib_edit_todolist_close)
                closeButton?.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }

            addTaskButton.setOnClickListener {
                itemView.findNavController().navigate(R.id.action_board_to_addTaskFragment)
//                Toast.makeText(context,"add task",Toast.LENGTH_LONG)
//                    .show()
            }
        }
    }

    inner class AddToDoListButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        internal val button = itemView.findViewById<MaterialButton>(R.id.btn_add_todolist)

        init {
            itemView.setOnClickListener {
                Toast.makeText(it.context, "add todo list", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        return when (viewType) {
            TodoListView.ADD_TODOLIST_BUTTON -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.add_todo_list, parent, false)
                AddToDoListButtonViewHolder(view)
            }
            else -> {
                view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_todo_list, parent, false)
                TodoListViewHolder(view)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TodoListViewHolder) {
            val subtasks: ArrayList<SubTaskView> = ArrayList()
            holder.itemView.tag = todoListViews[position]
            val currentItem = todoListViews[position]
            holder.todoNameTextView.text = currentItem.todoListName
            holder.subTaskRecyclerView.setHasFixedSize(true)
            holder.subTaskRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)

            subtasks.apply {

                add(SubTaskView("Awesome", true))
                add(SubTaskView("Marvelous", true))
                add(SubTaskView("Spectacular", true))
                add(SubTaskView("Awesome", true))
                add(SubTaskView("Marvelous", true))
                add(SubTaskView("Awesome", true))
            }

            var allCompleted = true
            for (st in subtasks) {
                if (!st.isCompleted) {
                    allCompleted = false
                    break
                }
            }
            if (subtasks.size == 0)
                allCompleted = false

            holder.allTasksDoneTextView.visibility =
                if (allCompleted) View.VISIBLE else View.GONE

            val subTaskRecyclerAdapter = SubTaskRecyclerAdapter(context, subtasks, picasso)
            holder.subTaskRecyclerView.adapter = subTaskRecyclerAdapter

        }
    }

    override fun getItemCount() = todoListViews.size

    override fun getItemViewType(position: Int): Int {
        return todoListViews[position].viewType
    }

}


//
//dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
//dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//dialog?.setCanceledOnTouchOutside(false)
//
//val closeButton = dialog.findViewById<ImageButton>(R.id.addBoardCloseButton)
//closeButton.setOnClickListener {
//    dialog.dismiss()
//}