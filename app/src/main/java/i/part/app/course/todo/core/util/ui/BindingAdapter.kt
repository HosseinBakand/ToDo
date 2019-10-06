package i.part.app.course.todo.core.util.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.features.board.ui.BoardStatusEnum

@BindingAdapter("imageUrlForRoundCorner")
fun loadRoundCornerImage(view: ImageView, imageUrl: String) {
    val radius = 8
    val margin = 0
    val transformation = RoundedCornersTransformation(radius, margin)
    Picasso.get()
        .load(imageUrl)
        .transform(transformation)
        .fit()
        .into(view)
}

@BindingAdapter("imageUrlForCircular")
fun loadCirularImage(view: ImageView, imageUrl: String) {
    val transform = PicassoCircleTransformation()
//    Picasso.get()
//        .load(imageUrl)
//        .transform(transform)
//        .error(R.drawable.person_empty)
//        .fit()
//        .into(view)
}

@BindingAdapter("labelStatus")
fun setLabelStatus(view: TextView, status: BoardStatusEnum) {
    if (status == BoardStatusEnum.ToDo) {
        view.setBackgroundResource(R.drawable.round_red_label)
        view.text = BoardStatusEnum.ToDo.name
    } else {
        view.setBackgroundResource(R.drawable.round_green_label)
        view.text = BoardStatusEnum.Done.name
    }

}