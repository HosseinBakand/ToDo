package i.part.app.course.todo.task_list.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.board.ui.BoardRecyclerAdapter
import i.part.app.course.todo.core.util.ui.OverlapDecoration
import i.part.app.course.todo.task_list.data.SubTask
import i.part.app.course.todo.task_list.data.TodoList

class TodoListRecyclerAdapter(private val context: Context, private val todoLists: List<TodoList>, private val picasso: Picasso) :
    RecyclerView.Adapter<TodoListRecyclerAdapter.ViewHolder>() {
    lateinit var view:View

    inner class ViewHolder(itemView : View): RecyclerView.ViewHolder(itemView){
        internal var todoNameTextView = itemView.findViewById<MaterialTextView>(R.id.todo_list_item_name_text_view)
        internal val editImageView = itemView.findViewById<ImageView>(R.id.todo_list_item_edit_image_view)
        internal var allTasksDoneTextView = itemView.findViewById<MaterialTextView>(R.id.todo_list_item_all_tasks_done_text_view)
        internal val subTaskRecyclerView = itemView.findViewById<RecyclerView>(R.id.todo_list_item_sub_task_recycler_view)
        internal val addTaskButton = itemView.findViewById<MaterialButton>(R.id.todo_list_item_add_task_button)

        init {
            itemView.setOnClickListener{ view ->
                val taskTag = view.tag as TodoList
                Toast.makeText(view.context,"${taskTag.todoListName} is ${taskTag.isCompleted}",Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        view = LayoutInflater.from(parent.context).inflate(R.layout.todo_list_item,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val subtasks:ArrayList<SubTask> = ArrayList()
        holder.itemView.tag = todoLists[position]
        val currentItem = todoLists[position]
        holder.todoNameTextView.text = currentItem.todoListName
        holder.subTaskRecyclerView.setHasFixedSize(true)
        holder.subTaskRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true)

        subtasks.apply {
            add(SubTask("GOOD",true))
            add(SubTask("GREAT",true))
            add(SubTask("Awesome",true))
            add(SubTask("Marvelous",true))
            add(SubTask("Spectacular",true))
        }

        var allCompleted = true
        for (st in subtasks){
            if (!st.isCompleted){
                allCompleted = false
                break
            }
        }

        holder.allTasksDoneTextView.visibility =
            if (allCompleted) View.VISIBLE else View.GONE

        val subTaskRecyclerAdapter = SubTaskRecyclerAdapter(context,subtasks,picasso)
        holder.subTaskRecyclerView.adapter = subTaskRecyclerAdapter


    }

    override fun getItemCount() = todoLists.size
}