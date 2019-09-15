package i.part.app.course.todo.features.board.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.OverlapDecoration
import i.part.app.course.todo.core.util.ui.RoundedCornersTransformation
import java.util.*
class Edit_board : DialogFragment() {
    var avatarManager: RecyclerView.LayoutManager? = null
    var avatarAdapter: RecyclerView.Adapter<*>? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        val view = inflater.inflate(R.layout.fragment_edit_board, container, false)
        val closeButton = view.findViewById<ImageButton>(R.id.ib_edit_board_close)
        closeButton.setOnClickListener {
            this.dismiss()
        }


        view?.let {
            it.findViewById<ImageButton>(R.id.ib_edit_board_plus)?.setOnClickListener {
                val fragmentType: String? = "edit_board"
                val myBundle = bundleOf("fragmentType" to fragmentType)
                this.findNavController().navigate(R.id.action_edit_board_to_addMember2, myBundle)
            }
        }
        view?.let {
            it.findViewById<MaterialButton>(R.id.btn_edit_board_confirm)?.setOnClickListener {
                this.findNavController().navigate(R.id.action_edit_board_to_board)
            }
        }
        val rv_avatar = view.findViewById<RecyclerView>(R.id.rv_edit_board_avatars)
        val myAvatarViews: ArrayList<AvatarView> = ArrayList()

        rv_avatar.let { it.setHasFixedSize(true) }
        val overlap: OverlapDecoration = OverlapDecoration()
        rv_avatar.addItemDecoration(overlap)
        avatarManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        rv_avatar.let { it.layoutManager = avatarManager }

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
        val picasso = Picasso.get()
        context?.let { avatarAdapter = AvatarRecyclerAdapter(it, myAvatarViews, picasso, true) }


        val image = view.findViewById<ImageView>(R.id.iv_edit_board_preview)
        val url =
            "https://img.freepik.com/free-vector/colorful-watercolor-background_79603-99.jpg?size=626&ext=jpg"
        val radius = 8
        val margin = 0
        val transformation = RoundedCornersTransformation(radius, margin)
        picasso.load(url).transform(transformation).error(R.drawable.person_empty)
            .fit().into(image)
        rv_avatar.let { it.adapter = avatarAdapter }

        val editText = view.findViewById<EditText>(R.id.et_edit_board_name)
        editText.setSelection(0, editText.text.toString().length)
        return view
    }
}
