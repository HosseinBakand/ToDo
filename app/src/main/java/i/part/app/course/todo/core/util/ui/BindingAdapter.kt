package i.part.app.course.todo.core.util.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso

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