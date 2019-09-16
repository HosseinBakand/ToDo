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
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.ItemTodoListBinding
import kotlinx.android.synthetic.main.dialog_add_to_do_list.*


class TodoListRecyclerAdapter(
    val adapter: TodoListRecyclerAdapter?,
    val todoListViews: MutableList<TodoListView>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    lateinit var view: View
    lateinit var context: Context
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
            editImageView.setOnClickListener {
                val dialog = Dialog(context)
                dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.dialog_edit_todolist_name)
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
                confirmButton?.setOnClickListener {
                    todoListViews.add(
                        todoListViews.size-1,
                        TodoListView(
                            TodoListType.TODOLIST,
                            dialog.et_todolist_name.text.toString(),
                            mutableListOf()
                        )
                    )
                    notifyDataSetChanged()
                    dialog.dismiss()
                }
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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is TodoListViewHolder) {
            //used in the listener for add task
            holder.itemView.tag = todoListViews[position]
            holder.holderBinding.todolist = todoListViews[position]
            holder.subTaskRecyclerView.setHasFixedSize(true)
            holder.subTaskRecyclerView.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)
            val subTaskRecyclerAdapter = SubTaskRecyclerAdapter(
                todoListViews[position].subtasks,
                holder.allTasksDoneTextView
            )
            holder.subTaskRecyclerView.adapter = subTaskRecyclerAdapter
        }
        else if(holder is AddToDoListButtonViewHolder){
            holder.button.setOnClickListener {
                val dialog = Dialog(context)
                dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.dialog_add_to_do_list)
                dialog.setCanceledOnTouchOutside(false)
                val confirmButton = dialog.findViewById<MaterialButton>(R.id.btn_add_todolist_confirm)
                confirmButton?.setOnClickListener {
                    todoListViews.add(
                        todoListViews.size-1,
                        TodoListView(
                            TodoListType.TODOLIST,
                            dialog.et_todolist_name.text.toString(),
                            mutableListOf()
                        )
                    )
                    notifyItemInserted(position-1)
                    notifyItemRangeChanged(position-1,itemCount)
                    dialog.dismiss()
                    //recyclerView.scrollToPosition(position)
                }
                confirmButton.onEditorAction(EditorInfo.IME_ACTION_DONE)
                val closeButton = dialog.findViewById<ImageButton>(R.id.ib_add_todolist_close)
                closeButton?.setOnClickListener {
                    dialog.dismiss()
                }

                dialog.show()
            }
        }
    }

    override fun getItemCount() = todoListViews.size

    override fun getItemViewType(position: Int): Int {
        return todoListViews[position].todoListType.type
    }

    override fun getItemId(position: Int): Long {
        return RecyclerView.NO_ID
    }
}