package i.part.app.course.todo.board

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import i.part.app.course.todo.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class add_board : DialogFragment() {

    @SuppressLint("ResourceAsColor")
    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.setTitle("Add Board")
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        //dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        //dialog?.window?.setBackgroundDrawableResource(R.drawable.dialog_edge)
        dialog?.setCanceledOnTouchOutside(false)
        val view = inflater.inflate(R.layout.fragment_add_board, container, false)
        val closeButton = view.findViewById<ImageButton>(R.id.addBoardCloseButton)
        closeButton.setOnClickListener {
            this.dismiss()
        }

        return view
    }

    companion object {

        fun newInstance(title: String): add_board {
            val frag = add_board()
            val args = Bundle()

            args.putString("Add Board", title)
            frag.arguments = args
            return frag
        }
    }
}
