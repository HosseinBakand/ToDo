package i.part.app.course.todo.features.board.ui

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.util.ui.OverlapDecoration
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class AddTaskFragment : DialogFragment() {
    var avatarManager: RecyclerView.LayoutManager? = null
    var avatarAdapter: RecyclerView.Adapter<*>? = null

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.setTitle("Add BoardDetailFragment")
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_edge)
        dialog?.setCanceledOnTouchOutside(false)
        val view = inflater.inflate(R.layout.fragment_add_task, container, false)
        val closeButton = view.findViewById<ImageButton>(R.id.ib_add_task_close)
        closeButton.setOnClickListener { this.dismiss() }

        view.findViewById<MaterialButton>(R.id.btn_add_task_confirm)?.let {
            it.setOnClickListener {
                this.findNavController().navigate(R.id.action_addTaskFragment_to_board)
                this.dismiss()
            }
        }

        view.findViewById<ImageButton>(R.id.ib_add_task_plus)?.let {
            it.setOnClickListener {
                val fragmentType: String? = "add_task"
                val myBundle = bundleOf("fragmentType" to fragmentType)
                this.findNavController()
                    .navigate(R.id.action_addTaskFragment_to_addMember2, myBundle)
            }
        }

        //recycle
        val rv_avatar = view.findViewById<RecyclerView>(R.id.rv_add_task_avatars)
        val myAvatarViews: ArrayList<AvatarView> = ArrayList()

        rv_avatar.let { it.setHasFixedSize(true) }
        val overlap: OverlapDecoration = OverlapDecoration()
        rv_avatar.addItemDecoration(overlap)
        avatarManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        rv_avatar.let { it.layoutManager = avatarManager }

        //start generating fake data
        myAvatarViews.add(AvatarView())
        myAvatarViews.add(AvatarView())
        myAvatarViews.add(AvatarView())
        myAvatarViews.add(AvatarView())
        myAvatarViews.add(AvatarView())
        myAvatarViews.add(AvatarView())
        //end
        val picasso = Picasso.get()
        context?.let { avatarAdapter = AvatarRecyclerAdapter(it, myAvatarViews, picasso, true) }

        rv_avatar.let { it.adapter = avatarAdapter }


        return view
    }

    companion object {

        fun newInstance(title: String): AddTaskFragment {
            val frag = AddTaskFragment()
            val args = Bundle()

            args.putString("Add TaskView", title)
            frag.arguments = args
            return frag
        }
    }
}
