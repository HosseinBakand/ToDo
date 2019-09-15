package i.part.app.course.todo.features.board.ui

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.ItemAvatarAddBoardBinding
import i.part.app.course.todo.databinding.ItemAvatarBinding

//input size is for choose item // true for big and false for small
class AvatarRecyclerAdapter(
    private val context: Context,
    avatarViews: List<AvatarView>,
    picasso: Picasso,
    val inputSize: Boolean
) :
    RecyclerView.Adapter<AvatarRecyclerAdapter.ViewHolder>() {
    private val avatarViews: List<AvatarView>
    lateinit var v: View
    lateinit var mBindingforAddBoard: ItemAvatarAddBoardBinding
    lateinit var mBinding: ItemAvatarBinding
    private val picasso: Picasso

    init {
        this.avatarViews = avatarViews
        this.picasso = picasso
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        if (inputSize) {
            mBindingforAddBoard =
                DataBindingUtil.inflate(inflater, R.layout.item_avatar_add_board, parent, false)
            val myBindedView = mBindingforAddBoard.root
            return ViewHolder(myBindedView)
        } else {
            mBinding = DataBindingUtil.inflate(inflater, R.layout.item_avatar, parent, false)
            val myBindedView = mBinding.root
            return ViewHolder(myBindedView)
        }


    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    val Float.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = avatarViews[position]
        val t = avatarViews[position]
        if (inputSize) {
            mBindingforAddBoard.myAvatar = t
        } else {
            mBinding.myAvatar = t
        }
//        val trsfrm = PicassoCircleTransformation()
//        picasso.load(t.image_url).transform(trsfrm).error(R.drawable.ic_person_gray_24dp)
        //holder.avatarImageView.setBackgroundResource(R.drawable.ic_avatar)
    }

    override fun getItemCount(): Int {
        return avatarViews.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        internal var avatarImageView: ImageView

        init {
            avatarImageView = itemView.findViewById<View>(R.id.im_avatar) as ImageView
            itemView.setOnClickListener { view ->
                val myAvatar = view.tag as AvatarView
                Toast.makeText(view.context, myAvatar.image_url, Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

}
