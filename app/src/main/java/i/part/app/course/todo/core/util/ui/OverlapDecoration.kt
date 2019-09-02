package i.part.app.course.todo.core.util.ui

import android.graphics.Rect
import android.view.View

import androidx.recyclerview.widget.RecyclerView

class OverlapDecoration : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        val itemPosition = parent.getChildAdapterPosition(view)
        if (itemPosition == 0) {
            return
        }
        outRect.set(0, 0, horzOverlap, 0)
    }

    companion object {
        private val horzOverlap = -20
    }
}
