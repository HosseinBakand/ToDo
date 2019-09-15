package i.part.app.course.todo.core.util.ui

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R

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

@BindingAdapter("imageUrlForCirular")
fun loadCirularImage(view: ImageView, imageUrl: String) {
    val transform = PicassoCircleTransformation()
    Picasso.get()
        .load(imageUrl)
        .transform(transform)
        .into(view)
}

@BindingAdapter("labelStatus")
fun setLabelStatus(view: TextView, status: String) {
    if (status == "todo") {
        view.setBackgroundResource(R.drawable.round_red_label)
        view.text = "Todo"
    } else {
        view.setBackgroundResource(R.drawable.round_green_label)
        view.text = "Done"
    }

}