package i.part.app.course.todo.features.board.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.core.util.ui.OverlapDecoration
import i.part.app.course.todo.databinding.DialogEditBoardBinding
import kotlinx.android.synthetic.main.dialog_edit_board.*
import java.util.*

class EditBoardDialogFragment : DialogFragment() {
    private var avatarManager: RecyclerView.LayoutManager? = null
    private var avatarAdapter: RecyclerView.Adapter<*>? = null
    lateinit var myView: View
    lateinit var binding: DialogEditBoardBinding
    private val boardViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity).get(DashBoardViewModel::class.java)
        }
    }
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_edit_board, container, false)
        myView = binding.root
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        var editBoardViewModel = EditBoardView(
            arguments?.getString("boardName") ?: "",
            arguments?.getString("ownerName") ?: "",
            "https://img.freepik.com/free-vector/colorful-watercolor-background_79603-99.jpg?size=626&ext=jpg",
            "HosseiNImage.jpg"
        )
        binding.boardEditing = editBoardViewModel

        ib_edit_board_close.setOnClickListener {
            this.dismiss()
        }

        ib_edit_board_plus.setOnClickListener {
            val fragmentType: String? = "edit_board"
            val myBundle = bundleOf("fragmentType" to fragmentType)
            this.findNavController().navigate(R.id.action_edit_board_to_addMember2, myBundle)
        }

        btn_edit_board_confirm.setOnClickListener {
            if (et_edit_board_name != null && et_edit_board_name.text.isNotEmpty()) {
                boardViewModel?.updateBoardTitle(
                    BoardView(
                        id = arguments?.getInt("boardId") ?: -1,
                        title = et_edit_board_name.text.toString()
                    )
                )
                boardViewModel?.updateBoardTitleLivaData?.observe(this, Observer {
                    when (it) {
                        is Result.Success -> {
                            val myBundle = Bundle()
                            myBundle.putInt("boardId", arguments?.getInt("boardId") ?: -1)
                            this.findNavController()
                                .navigate(R.id.action_edit_board_to_board, myBundle)
                        }
                        is Result.Error -> {
                            val myBundle = Bundle()
                            myBundle.putInt("boardId", arguments?.getInt("boardId") ?: -1)
                            this.findNavController()
                                .navigate(R.id.action_edit_board_to_board, myBundle)
                            showSnackBar(
                                myView,
                                it.message,
                                Snackbar.LENGTH_INDEFINITE,
                                "ERROR"
                            )
                        }
                        is Result.Loading -> {
                            Toast.makeText(context, "Loading...", Toast.LENGTH_SHORT).show()
                        }
                    }
                })
            }
        }

        val myAvatarViews: ArrayList<AvatarView> = ArrayList()

        rv_edit_board_avatars?.setHasFixedSize(true)
        val overlap = OverlapDecoration()
        rv_edit_board_avatars.addItemDecoration(overlap)
        avatarManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        rv_edit_board_avatars.layoutManager = avatarManager

        //start generating fake data
        val fakeLink =
            "https://www.shareicon.net/download/2016/05/24/770136_man_512x512.png"
        myAvatarViews.add(AvatarView(fakeLink))
        myAvatarViews.add(AvatarView(fakeLink))
        myAvatarViews.add(AvatarView(fakeLink))
        myAvatarViews.add(AvatarView(fakeLink))
        myAvatarViews.add(AvatarView(fakeLink))
        myAvatarViews.add(AvatarView(fakeLink))
        //end
        val picasso = Picasso.get()
        context?.let { avatarAdapter = AvatarRecyclerAdapter(myAvatarViews, picasso, true) }
        rv_edit_board_avatars?.let { it.adapter = avatarAdapter }
        super.onActivityCreated(savedInstanceState)
    }

    private fun showSnackBar(view: View, message: String, duration: Int, type: String) {
        val snackBar = Snackbar.make(view, message, duration)
        snackBar.setActionTextColor(Color.YELLOW)
        snackBar.setAction("Refresh") {
            boardViewModel?.getBoards()
        }
        snackBar.show()
    }
}
