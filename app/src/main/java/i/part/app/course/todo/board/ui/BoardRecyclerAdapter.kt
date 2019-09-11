package i.part.app.course.todo.board.ui

import android.app.Dialog
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.OverlapDecoration
import i.part.app.course.todo.core.util.ui.RoundedCornersTransformation
import java.util.*


class BoardRecyclerAdapter(
    private val context: Context,
    taskViews: List<TaskView>,
    picasso: Picasso
) :
    RecyclerView.Adapter<BoardRecyclerAdapter.ViewHolder>() {
    var avatarAdapter: RecyclerView.Adapter<*>? = null
    var avatarManager: RecyclerView.LayoutManager? = null
    val picasso: Picasso
    private val taskViews: List<TaskView>
    lateinit var v: View

    init {
        this.taskViews = taskViews
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
        val myAvatarViews: ArrayList<AvatarView> = ArrayList()
        if (position == 0) {
            val params = RelativeLayout.LayoutParams(v.layoutParams)
            params.setMargins(8.dp, 25.dp, 8.dp, 10.dp)
            v.layoutParams = params
        }
        holder.itemView.tag = taskViews[position]
        val t = taskViews[position]
        holder.name.text = t.name
        holder.todo.text = "${t.todo} to do (${t.totalTasks} task)"
        holder.remaningTasks.text = "${t.remaningTasks} remaining taskViews"
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
        picasso.load(t.imageUrl).transform(transformation).error(R.drawable.no_task_image).fit()
            .into(holder.iv_task)
        holder.rv_avatar.let { it.setHasFixedSize(true) }
        holder.iV_delete.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                val dialog = Dialog(context)
                dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
                dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                dialog.setContentView(R.layout.delete_page)
                dialog.setCanceledOnTouchOutside(false)
                val okButton = dialog.findViewById<TextView>(R.id.tv_ok_button)
                okButton.setOnClickListener {
                    //
                }
                val closeButton = dialog.findViewById<TextView>(R.id.tv_cancel_button)
                closeButton.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.show()
            }
        })
        val overlap: OverlapDecoration = OverlapDecoration()
        holder.rv_avatar.addItemDecoration(overlap)
        avatarManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        holder.rv_avatar.let { it.layoutManager = avatarManager }
        //start generating fake data
        myAvatarViews.add(AvatarView())
        myAvatarViews.add(AvatarView())
        myAvatarViews.add(AvatarView())
        myAvatarViews.add(AvatarView())
        myAvatarViews.add(AvatarView())
        myAvatarViews.add(AvatarView())
        //end
        avatarAdapter = AvatarRecyclerAdapter(context, myAvatarViews, picasso, false)
        holder.rv_avatar.let { it.adapter = avatarAdapter }


    }

    override fun getItemCount(): Int {
        return taskViews.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var name: TextView
        internal var todo: TextView
        internal var totalTasks: TextView? = null
        internal var remaningTasks: TextView
        internal var rv_avatar: RecyclerView
        internal var iv_task: ImageView
        internal var iV_delete: ImageView
        internal var tv_status_label: TextView

        init {
            name =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.tv_board_name) as TextView
            tv_status_label =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.tv_status_label) as TextView
            iv_task =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.iv_task) as ImageView
            iV_delete =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.iv_delete_board_item) as ImageView

            rv_avatar =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.rv_avatars) as RecyclerView
            todo =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.tv_todo_count) as TextView
            remaningTasks =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.tv_remaining_tasks) as TextView
            itemView.setOnClickListener { view ->
                view.findNavController().navigate(R.id.action_dashBoardFragment_to_board)

//                val myTask = view.tag as TaskView
//                Toast.makeText(view.context, myTask.name + " is " + myTask.todo, Toast.LENGTH_SHORT)
//                    .show()
            }
        }
    }

}