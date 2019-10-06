package i.part.app.course.todo.features.board.ui

import android.content.Context
import android.content.res.Resources
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.OverlapDecoration
import i.part.app.course.todo.databinding.ItemBoardBinding
import java.util.*


private object Callback : DiffUtil.ItemCallback<BoardView>() {
    override fun areItemsTheSame(oldItem: BoardView, newItem: BoardView): Boolean {
        return (oldItem == newItem)
    }

    override fun areContentsTheSame(oldItem: BoardView, newItem: BoardView): Boolean {
        return (oldItem == newItem && oldItem.imageUrl == newItem.imageUrl)
    }
}


class BoardRecyclerAdapter(
    picasso: Picasso,
    val callback: MyCallback
) : ListAdapter<BoardView, BoardRecyclerAdapter.ViewHolder>(Callback) {
    var avatarAdapter: RecyclerView.Adapter<*>? = null
    var avatarManager: RecyclerView.LayoutManager? = null
    lateinit var mBinding: ItemBoardBinding
    val picasso: Picasso
    lateinit var context: Context
    lateinit var v: View

    init {
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
        holder.itemView.tag = getItem(position)
        val item = getItem(position)
        holder.holderBinding.myBoard = item
        holder.rv_avatar.let { it.setHasFixedSize(true) }
        holder.iV_delete.setOnClickListener {
            callback.deleteBoard(item)
        }
        holder.itemView.setOnClickListener { view ->
            val myBundle = Bundle()
            myBundle.putInt("boardID", item.id)
            view.findNavController().navigate(R.id.action_dashBoardFragment_to_board, myBundle)
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

    override fun submitList(list: MutableList<BoardView>?) {
        list?.sortBy { it.id }
        super.submitList(list)
    }

    inner class ViewHolder(binding: ItemBoardBinding) : RecyclerView.ViewHolder(binding.root) {
        internal var rv_avatar: RecyclerView
        internal var iV_delete: ImageView
        val holderBinding = binding

        init {
            iV_delete =
                itemView.findViewById<View>(R.id.iv_delete_board_item) as ImageView
            rv_avatar =
                itemView.findViewById<View>(R.id.rv_avatars) as RecyclerView

        }
    }

    interface MyCallback {
        fun deleteBoard(item: BoardView)
    }

}