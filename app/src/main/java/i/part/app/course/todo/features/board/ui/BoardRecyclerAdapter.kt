package i.part.app.course.todo.features.board.ui

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
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.OverlapDecoration
import i.part.app.course.todo.databinding.ItemBoardBinding
import java.util.*


class BoardRecyclerAdapter(
    taskViews: List<TaskView>,
    picasso: Picasso
) :
    RecyclerView.Adapter<BoardRecyclerAdapter.ViewHolder>() {
    var avatarAdapter: RecyclerView.Adapter<*>? = null
    var avatarManager: RecyclerView.LayoutManager? = null
    lateinit var mBinding: ItemBoardBinding
    val picasso: Picasso
    lateinit var context: Context
    private val taskViews: List<TaskView>
    lateinit var v: View

    init {
        this.taskViews = taskViews
        this.picasso = picasso
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        context = parent.context
        mBinding = DataBindingUtil.inflate(inflater, R.layout.item_board, parent, false)
        val myBindedView = mBinding
        return ViewHolder(myBindedView)
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    val Float.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val myAvatarViews: ArrayList<AvatarView> = ArrayList()
        holder.itemView.tag = taskViews[position]
        val t = taskViews[position]
        holder.holderBinding.myBoard = t
        holder.rv_avatar.let { it.setHasFixedSize(true) }
        holder.iV_delete.setOnClickListener {
            val dialog = Dialog(context)
            dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
            dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.setContentView(R.layout.dialog_delete_page)
            dialog.setCanceledOnTouchOutside(false)
            val okButton = dialog.findViewById<TextView>(R.id.tv_ok_button)
            okButton.setOnClickListener {
                dialog.dismiss()
            }
            val closeButton = dialog.findViewById<TextView>(R.id.tv_cancel_button)
            closeButton.setOnClickListener {
                dialog.dismiss()
            }
            dialog.show()
        }
        val overlap: OverlapDecoration = OverlapDecoration()
        holder.rv_avatar.addItemDecoration(overlap)
        avatarManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        holder.rv_avatar.let { it.layoutManager = avatarManager }
        //start generating fake data
        val fakeLink: String =
            "https://www.shareicon.net/download/2016/05/24/770136_man_512x512.png"
        myAvatarViews.add(AvatarView(fakeLink))
        myAvatarViews.add(AvatarView(fakeLink))
        myAvatarViews.add(AvatarView(fakeLink))
        myAvatarViews.add(AvatarView(fakeLink))
        myAvatarViews.add(AvatarView(fakeLink))
        myAvatarViews.add(AvatarView(fakeLink))
        //end
        avatarAdapter = AvatarRecyclerAdapter(myAvatarViews, picasso, false)
        holder.rv_avatar.let { it.adapter = avatarAdapter }


    }

    override fun getItemCount(): Int {
        return taskViews.size
    }

    inner class ViewHolder(binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        internal var rv_avatar: RecyclerView
        internal var iV_delete: ImageView
        val holderBinding = binding

        init {
            iV_delete =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.iv_delete_board_item) as ImageView
            rv_avatar =
                itemView.findViewById<View>(i.part.app.course.todo.R.id.rv_avatars) as RecyclerView
            itemView.setOnClickListener { view ->
                view.findNavController().navigate(R.id.action_dashBoardFragment_to_board)
            }
        }
    }

}