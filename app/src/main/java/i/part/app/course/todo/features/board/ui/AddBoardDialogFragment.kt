package i.part.app.course.todo.features.board.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import com.google.android.material.button.MaterialButton
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.core.util.ui.OverlapDecoration
import i.part.app.course.todo.databinding.DialogAddBoardBinding
import i.part.app.course.todo.features.board.data.AddUserParam
import kotlinx.android.synthetic.main.dialog_add_board.*
import java.util.*

class AddBoardDialogFragment : DialogFragment() {
    var avatarManager: RecyclerView.LayoutManager? = null
    var avatarAdapter: RecyclerView.Adapter<*>? = null
    lateinit var myView: View
    val myAvatarViews: ArrayList<AvatarView> = ArrayList()
    lateinit var assigneeList: List<String>
    lateinit var binding: DialogAddBoardBinding
    private val boardViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity).get(DashBoardViewModel::class.java)
        }
    }


    private val addMemberViewModel by lazy {
        activity?.let {
            ViewModelProviders.of(activity as FragmentActivity).get(AddMemberViewModel::class.java)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_board, container, false)
        myView = binding.root
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        observAddMember()
        ib_add_board_close.setOnClickListener {
            this.dismiss()
        }
        ib_add_board_plus.setOnClickListener {
            val fragmentType: String? = "add_board"
            val myBundle = bundleOf("fragmentType" to fragmentType)
            myBundle.putString("boardName", et_add_board_name.text.toString())
            this.findNavController().navigate(R.id.action_add_board_to_addMember2, myBundle)
        }

        btn_add_board_confirm.setOnClickListener {
            val boardName = et_add_board_name.text.toString()
            if (boardName.isNotEmpty()) {
                val board = BoardView(title = boardName)
                boardViewModel?.addBoard(board)
                observeAddBoard()
            } else {
                Toast.makeText(context, "Board should have name", Toast.LENGTH_SHORT).show()
            }
        }
        //recycle
        rv_avatars.setHasFixedSize(true)
        val overlap = OverlapDecoration()
        rv_avatars.addItemDecoration(overlap)
        avatarManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, true)
        rv_avatars.layoutManager = avatarManager

        //start generating fake data
        //end
        val picasso = Picasso.get()
        context?.let { avatarAdapter = AvatarRecyclerAdapter(myAvatarViews, picasso, true) }


        val addBoardViewModel = AddBoardView(
            "Vahid Safari",
            "https://img.freepik.com/free-vector/colorful-watercolor-background_79603-99.jpg?size=626&ext=jpg",
            "vahidImage.jpg"
        )
        binding.newBoardDetail = addBoardViewModel
        rv_avatars.adapter = avatarAdapter
        super.onActivityCreated(savedInstanceState)
    }

    private fun observAddMember() {
        addMemberViewModel?.selectedMembers?.observe(viewLifecycleOwner, Observer {
            assigneeList = it
            val fakeLink =
                "https://www.shareicon.net/download/2016/05/24/770136_man_512x512.png"
            for (i in assigneeList) {
                myAvatarViews.add(AvatarView(fakeLink))
            }
            val picasso = Picasso.get()
            context?.let { avatarAdapter = AvatarRecyclerAdapter(myAvatarViews, picasso, true) }
            arguments?.let {
                if (it.getString("boardName") != null) {
                    et_add_board_name.text =
                        Editable.Factory.getInstance().newEditable(it.getString("boardName"))
                }
            }
        })
    }

    private fun observeAddBoard() {
        boardViewModel?.addBoardLiveData?.observe(this, Observer {
            //TODO: addMemberViewModel?.selectedMembers?.value
            when (it) {
                is Result.Success -> {
                    it.data?.let {
                        it.data.let {
                            try {
                                var tempParam = AddUserParam(assigneeList)
                                addMemberViewModel?.addUsersToBoard(it.boardId, tempParam)
                            } catch (ex: Exception) {
                                Log.e("", "")
                            }
                        }
                    }

                    val btn = myView.findViewById<MaterialButton>(R.id.btn_add_board_confirm)
                    bindProgressButton(btn)
                    btn.showProgress {
                        progressColor = Color.BLACK
                    }

                    Handler().postDelayed({

                        context?.let {
                            val animatedDrawable = ContextCompat.getDrawable(
                                context as Context,
                                R.drawable.animated_check
                            )
                            animatedDrawable?.setBounds(0, 0, 75, 75)
                            animatedDrawable?.let { drawable ->
                                btn.showDrawable(drawable)
                            }
                        }

                        btn.attachTextChangeAnimator {
                            fadeOutMills = 100
                            fadeInMills = 100
                        }
                        val h = Handler()
                        h.postDelayed({
                            this.dismiss()
                            boardViewModel?.updateBoardStatus()
                        }, 400)
                    }, 1000)
                }
                is Result.Error -> {
                    this.dismiss()
                    showSnackBar(
                        myView,
                        it.message,
                        Snackbar.LENGTH_LONG,
                        "ERROR"
                    )
                }
                is Result.Loading -> {
                }
            }
        })
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
