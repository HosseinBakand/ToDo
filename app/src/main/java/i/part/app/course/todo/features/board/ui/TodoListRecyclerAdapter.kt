package i.part.app.course.todo.features.board.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.EditorInfo
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.ItemTodoListBinding

private object TodoListRecyclerAdapterCallback : DiffUtil.ItemCallback<TodoListView>() {
    override fun areContentsTheSame(oldItem: TodoListView, newItem: TodoListView): Boolean {
        return (oldItem.todoListName == newItem.todoListName && oldItem.subtasks == newItem.subtasks)
    }

    override fun areItemsTheSame(oldItem: TodoListView, newItem: TodoListView): Boolean {
        return (oldItem.hashCode() == newItem.hashCode())
    }
}

class TodoListRecyclerAdapter(
    val callback: MyTodoListCallback
) :
    ListAdapter<TodoListView, RecyclerView.ViewHolder>(TodoListRecyclerAdapterCallback),
    SubTaskRecyclerAdapter.MyTaskListCallback {
    lateinit var view: View
    lateinit var context: Context
    lateinit var recyclerView: RecyclerView
    lateinit var mBinding: ItemTodoListBinding
    inner class TodoListViewHolder(binding: ItemTodoListBinding) : RecyclerView.ViewHolder(binding.root) {
        val holderBinding = binding
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
                    taskTag.todoListName,
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
    }

    inner class AddToDoListButtonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal val button = itemView.findViewById<MaterialButton>(R.id.btn_add_todolist)

        init {
            button.setOnClickListener {
                val dialog = Dialog(context)
                dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.dialog_add_to_do_list)
                dialog.setCanceledOnTouchOutside(false)
                val confirmButton = dialog.findViewById<MaterialButton>(R.id.btn_add_todolist_confirm)
                confirmButton.onEditorAction(EditorInfo.IME_ACTION_DONE)
                val closeButton = dialog.findViewById<ImageButton>(R.id.ib_add_todolist_close)
                closeButton?.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        context = parent.context
        recyclerView = parent.findViewById(R.id.rv_board_fragment)
        return when (viewType) {
            TodoListType.ADD_TODOLIST_BUTTON.type -> {
                view = LayoutInflater.from(parent.context).inflate(R.layout.add_todo_list, parent, false)
                AddToDoListButtonViewHolder(view)
            }
            else -> {
                mBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.item_todo_list, parent, false)
                TodoListViewHolder(mBinding)
            }
        }
    }

    override fun submitList(list: MutableList<TodoListView>?) {
        list?.sortBy { it.id }
        list?.add(
            TodoListView(
                -1,
                TodoListType.ADD_TODOLIST_BUTTON,
                "button",
                mutableListOf(),
                -1

            )
        )
        super.submitList(list)
    }

    override fun checkTask(taskId: Int, state: Boolean, description: String) {
        callback.checkTask(taskId, state, description)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TodoListViewHolder) {
            //used in the listener for add task
            holder.itemView.tag = getItem(position)
            holder.holderBinding.todolist = getItem(position)
            holder.subTaskRecyclerView.setHasFixedSize(true)
            holder.subTaskRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            val subTaskRecyclerAdapter = SubTaskRecyclerAdapter(
                getItem(position).subtasks,
                holder.allTasksDoneTextView,
                this
            )
            holder.subTaskRecyclerView.adapter = subTaskRecyclerAdapter
            holder.editImageView.setOnClickListener {
                callback.editTodoListName(getItem(position))
            }


            holder.addTaskButton.setOnClickListener {
                callback.addTask(getItem(position).id, getItem(position).boardId)
            }
        }
        else if(holder is AddToDoListButtonViewHolder){
            // callback.addTodoList()
            holder.button.setOnClickListener {
                callback.addTodoList()
            }
        }
    }
    override fun getItemViewType(position: Int): Int {
        return getItem(position).todoListType.type
    }

    override fun getItemId(position: Int): Long {
        return RecyclerView.NO_ID
    }

    fun getItemView(position: Int): TodoListView {
        return getItem(position)
    }

    interface MyTodoListCallback {
        fun addTask(id: Int, boardID: Int)
        fun editTodoListName(todoListView: TodoListView)
        fun addTodoList()
        fun checkTask(taskId: Int, state: Boolean, description: String)
    }
}