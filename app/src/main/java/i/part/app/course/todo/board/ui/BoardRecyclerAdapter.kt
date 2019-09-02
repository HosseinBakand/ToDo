package i.part.app.course.todo.board.ui

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.board.data.Avatar
import i.part.app.course.todo.board.data.Task
import i.part.app.course.todo.core.util.ui.OverlapDecoration
import i.part.app.course.todo.core.util.ui.RoundedCornersTransformation
import java.util.*


class BoardRecyclerAdapter(private val context: Context, tasks: List<Task>, picasso: Picasso) :
    RecyclerView.Adapter<BoardRecyclerAdapter.ViewHolder>() {
    var avatarAdapter: RecyclerView.Adapter<*>? = null
    var avatarManager: RecyclerView.LayoutManager? = null
    val picasso: Picasso
    private val tasks: List<Task>
    lateinit var v: View

    init {
        this.tasks = tasks
        this.picasso = picasso
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        v = LayoutInflater.from(parent.context).inflate(R.layout.board_item, parent, false)
        return ViewHolder(v)
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    val Float.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myAvatars: ArrayList<Avatar> = ArrayList()
        if (position == 0) {
            val params = RelativeLayout.LayoutParams(v.layoutParams)
            params.setMargins(8.dp, 25.dp, 8.dp, 10.dp)
            v.layoutParams = params
        }
        holder.itemView.tag = tasks[position]
        val t = tasks[position]
        holder.name.text = t.name
        holder.todo.text = "${t.todo} to do (${t.totalTasks} task)"
        holder.remaningTasks.text = "${t.remaningTasks} remaining tasks"
        val radius = 8
        val margin = 0
        val transformation = RoundedCornersTransformation(radius, margin)
        if (t.status.equals("todo")) {
            holder.tv_status_label.apply {
                text = "Todo"
                setBackgroundResource(R.drawable.round_red_label)
            }

        } else {
            holder.tv_status_label.apply {
                text = "Done"
                setBackgroundResource(R.drawable.round_green_label)
            }
        }
        picasso.load(t.imageUrl).transform(transformation).fit().into(holder.iv_task)
        holder.rv_avatar.let { it.setHasFixedSize(true) }
        val overlap: OverlapDecoration = OverlapDecoration()
        holder.rv_avatar.addItemDecoration(overlap)
        avatarManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        holder.rv_avatar.let { it.layoutManager = avatarManager }

        //start generating fake data
        myAvatars.add(Avatar())
        myAvatars.add(Avatar())
        myAvatars.add(Avatar())
        myAvatars.add(Avatar())
        myAvatars.add(Avatar())
        myAvatars.add(Avatar())
        //end
        avatarAdapter = AvatarRecyclerAdapter(context, myAvatars, picasso)
        holder.rv_avatar.let { it.adapter = avatarAdapter }


    }

    override fun getItemCount(): Int {
        return tasks.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView
        internal var todo: TextView
        internal var totalTasks: TextView? = null
        internal var remaningTasks: TextView
        internal var rv_avatar: RecyclerView
        internal var iv_task: ImageView
        internal var tv_status_label: TextView

        init {
            name =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.boardNameTextView) as TextView
            tv_status_label =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.statusLabel) as TextView
            iv_task =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.iv_task) as ImageView

            rv_avatar =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.avatarsRecyclerView) as RecyclerView
            todo =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.todoCountTextView) as TextView
            remaningTasks =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.remainingTasksTextView) as TextView
            itemView.setOnClickListener { view ->
                val myTask = view.tag as Task
                Toast.makeText(view.context, myTask.name + " is " + myTask.todo, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}