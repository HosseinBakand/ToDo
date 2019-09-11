package i.part.app.course.todo.core.util.ui

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R

@BindingAdapter("imageUrlForCircular")
fun loadCirularImage(view: ImageView, imageUrl: String) {
    val trsfrm = PicassoCircleTransformation()
    Picasso.get().load(imageUrl).transform(trsfrm).error(R.drawable.ic_person_gray_24dp)
}

@BindingAdapter("imageUrlForRoundCorner")
fun loadRoundCornerImage(view: ImageView, imageUrl: String) {
    val trsfrm = RoundedCornersTransformation(8, 0)
    Picasso.get().load(imageUrl).transform(trsfrm).error(R.drawable.ic_person_gray_24dp)
}