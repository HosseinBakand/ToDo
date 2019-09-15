package i.part.app.course.todo.features.board.ui

import android.content.Context
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.PicassoCircleTransformation

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
    private val picasso: Picasso

    init {
        this.avatarViews = avatarViews
        this.picasso = picasso
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (inputSize) {
            v = LayoutInflater.from(parent.context)
                .inflate(i.part.app.course.todo.R.layout.item_avatar_add_board, parent, false)
        } else {
            v = LayoutInflater.from(parent.context)
                .inflate(i.part.app.course.todo.R.layout.item_avatar, parent, false)
        }
        return ViewHolder(v)
    }

    val Int.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()
    val Float.dp: Int
        get() = (this * Resources.getSystem().displayMetrics.density + 0.5f).toInt()

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.tag = avatarViews[position]
        val t = avatarViews[position]
        val trsfrm = PicassoCircleTransformation()
        picasso.load(t.image_url).transform(trsfrm).error(R.drawable.ic_person_gray_24dp)

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
