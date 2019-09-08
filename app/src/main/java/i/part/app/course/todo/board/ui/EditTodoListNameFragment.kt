package i.part.app.course.todo.board.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import com.google.android.material.button.MaterialButton
import i.part.app.course.todo.R

class EditTodoListNameFragment : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)
        var view = inflater.inflate(R.layout.edit_todolist_name, container, false)
        val bt_confirm = view.findViewById<MaterialButton>(R.id.bt_confirm)
        val et_boardName = view.findViewById<EditText>(R.id.et_boardName)
        bt_confirm.setOnClickListener {
            //navigation
        }
        return view
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AddMember3Fragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}

