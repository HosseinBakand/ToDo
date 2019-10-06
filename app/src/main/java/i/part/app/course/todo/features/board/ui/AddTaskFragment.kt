package i.part.app.course.todo.features.board.ui

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
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
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.github.razir.progressbutton.attachTextChangeAnimator
import com.github.razir.progressbutton.bindProgressButton
import com.github.razir.progressbutton.showDrawable
import com.github.razir.progressbutton.showProgress
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import i.part.app.course.todo.R
import i.part.app.course.todo.databinding.DialogAddTaskBinding
import i.part.app.course.todo.features.board.data.AddTaskParam
import kotlinx.android.synthetic.main.dialog_add_task.*

class AddTaskFragment : DialogFragment() {
    var avatarManager: RecyclerView.LayoutManager? = null
    var avatarAdapter: RecyclerView.Adapter<*>? = null
    private lateinit var myView: View
    var selectedUserName: String = ""
    lateinit var binding: DialogAddTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.dialog_add_task, container, false)
        myView = binding.root
        return binding.root
    }

    private val taskViewModel by lazy {
        activity.let {
            ViewModelProviders.of(activity as FragmentActivity).get(TodoListViewModel::class.java)
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        binding.lifecycleOwner = this
        dialog?.setTitle("Add BoardDetailFragment")
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.setCanceledOnTouchOutside(false)
        super.onActivityCreated(savedInstanceState)
        //iv_add_task_avatar
        arguments?.let {
            if (it.get("userName") != null) {
                selectedUserName = it.get("userName") as String
                dialog?.et_add_task_task_name?.setText(it.get("todoListName") as String)
                iv_add_task_avatar.visibility = View.VISIBLE
                Picasso.get().load(it.get("userImageUrl") as String).error(R.drawable.person_empty)
                    .fit().into(iv_add_task_avatar)
            }
        }


        //taskViewModel.getTask()
        //binding.ownerName = taskViewModel.task.value
        //taskViewModel.task.observe(this, androidx.lifecycle.Observer { binding.ownerName = it })
        ib_add_task_close.setOnClickListener {
            this.findNavController().navigate(R.id.action_addTaskFragment_to_board)
        }

        btn_add_task_confirm.setOnClickListener {
            if (dialog?.et_add_task_task_name?.text.toString() == "") {
                Toast.makeText(context, "your task should have name", Toast.LENGTH_SHORT).show()

            } else if (selectedUserName == "") {
                Toast.makeText(context, "your task should have assignee", Toast.LENGTH_SHORT).show()

            } else {
                val btn = myView.findViewById<MaterialButton>(R.id.btn_add_task_confirm)
                bindProgressButton(btn)
                btn.showProgress {
                    progressColor = Color.BLACK
                }

                Handler().postDelayed({

                    context?.let {
                        val animatedDrawable =
                            ContextCompat.getDrawable(context as Context, R.drawable.animated_check)
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
                        arguments?.let {
                            taskViewModel.addTask(
                                it.getInt("TaskID"),
                                AddTaskParam(
                                    done = false,
                                    description = dialog?.et_add_task_task_name?.text.toString(),
                                    assignee = selectedUserName
                                )
                            )
                            taskViewModel.addTaskChanged.value = true
                        }
                        this.dismiss()
                    }, 600)
                }, 1200)
            }
        }


        ib_add_task_plus.setOnClickListener {
            val fragmentType: String? = "add_task"
            val myBundle = bundleOf("fragmentType" to fragmentType)
            arguments?.let {
                myBundle.putInt("boardID", it.getInt("boardID"))
                myBundle.putInt("TaskID", it.getInt("TaskID"))
                myBundle.putString("todoListName", dialog?.et_add_task_task_name?.text.toString())
            }

            this.findNavController()
                .navigate(
                    R.id.action_addTaskFragment_to_selectMemberForTaskDialogFragment,
                    myBundle
                )
        }
        val fakeLink: String =
            "https://www.shareicon.net/download/2016/05/24/770136_man_512x512.png"
        val picasso = Picasso.get()
        val mohammad: TaskView = TaskView("Mohammad bahadori")
        binding.ownerName = mohammad
    }
}
