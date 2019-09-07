package i.part.app.course.todo.board.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class AddMember3Fragment : DialogFragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<*>
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var list: ArrayList<AddMember3Item>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        dialog?.setCanceledOnTouchOutside(false)
        var view = inflater.inflate(R.layout.fragment_add_member_3, container, false)

        recyclerView = view.findViewById(R.id.add_member_3_recycle) as RecyclerView
        recyclerView.setHasFixedSize(true)

        layoutManager = LinearLayoutManager(context)

        recyclerView.layoutManager = layoutManager

        list = ArrayList()
        val url =
            "https://www.irreverentgent.com/wp-content/uploads/2018/03/Awesome-Profile-Pictures-for-Guys-look-away2.jpg"
        //Adding Data into ArrayList
        list.add(AddMember3Item(url, "Project Manager", false))
        list.add(AddMember3Item("Bradley Matthews", "Senior Developer", false))
        list.add(AddMember3Item(url, "Lead Developer", false))
        list.add(AddMember3Item("Gary Thompson", "Lead Developer", false))
        list.add(AddMember3Item("Corey Williamson", "UI/UX Developer", false))
        list.add(AddMember3Item("Samuel Jones", "Front-End Developer", false))
        list.add(AddMember3Item("Michael Read", "Backend Developer", false))
        list.add(AddMember3Item("Robert Phillips", "Android Developer", false))
        list.add(AddMember3Item("Albert Stewart", "Web Developer", false))
        list.add(AddMember3Item("Wayne Diaz", "Junior Developer", false))
        list.add(AddMember3Item("Todd Miller", "Project Manager", false))
        list.add(AddMember3Item("Bradley Matthews", "Senior Developer", false))
        list.add(AddMember3Item("Harley Gibson", "Lead Developer", false))
        list.add(AddMember3Item("Gary Thompson", "Lead Developer", false))
        list.add(AddMember3Item("Corey Williamson", "UI/UX Developer", false))
        list.add(AddMember3Item("Samuel Jones", "Front-End Developer", false))
        list.add(AddMember3Item("Michael Read", "Backend Developer", false))
        list.add(AddMember3Item("Robert Phillips", "Android Developer", false))
        list.add(AddMember3Item("Albert Stewart", "Web Developer", false))
        list.add(AddMember3Item("Wayne Diaz", "Junior Developer", false))
        list.add(AddMember3Item("Todd Miller", "Project Manager", false))
        list.add(AddMember3Item("Bradley Matthews", "Senior Developer", false))
        list.add(AddMember3Item("Harley Gibson", "Lead Developer", false))
        list.add(AddMember3Item("Gary Thompson", "Lead Developer", false))
        list.add(AddMember3Item("Corey Williamson", "UI/UX Developer", false))
        list.add(AddMember3Item("Samuel Jones", "Front-End Developer", false))
        list.add(AddMember3Item("Michael Read", "Backend Developer", false))
        list.add(AddMember3Item("Robert Phillips", "Android Developer", false))
        list.add(AddMember3Item("Albert Stewart", "Web Developer", false))
        list.add(AddMember3Item("Wayne Diaz", "Junior Developer", false))
        list.add(AddMember3Item("Todd Miller", "Project Manager", false))
        list.add(AddMember3Item("Bradley Matthews", "Senior Developer", false))
        list.add(AddMember3Item("Harley Gibson", "Lead Developer", false))
        list.add(AddMember3Item("Gary Thompson", "Lead Developer", false))
        list.add(AddMember3Item("Corey Williamson", "UI/UX Developer", false))
        list.add(AddMember3Item("Samuel Jones", "Front-End Developer", false))
        list.add(AddMember3Item("Michael Read", "Backend Developer", false))
        list.add(AddMember3Item("Robert Phillips", "Android Developer", false))
        list.add(AddMember3Item("Albert Stewart", "Web Developer", false))
        list.add(AddMember3Item("Wayne Diaz", "Junior Developer", false))


//        list.add(AddMember3Item(url, "1",false))
//        list.add(AddMember3Item("", "2",true))
//        list.add(AddMember3Item(url, "3",false))
//        list.add(AddMember3Item("", "4",false))
//        list.add(AddMember3Item("", "5",false))
//        list.add(AddMember3Item("", "6",false))
//        list.add(AddMember3Item("", "7",false))
//        list.add(AddMember3Item("", "8",false))
//        list.add(AddMember3Item("", "9",false))
//        list.add(AddMember3Item("", "10",false))
//        list.add(AddMember3Item("", "11",false))
//        list.add(AddMember3Item("", "12",false))
//        list.add(AddMember3Item("", "13",false))
//        list.add(AddMember3Item("", "14",false))
//        list.add(AddMember3Item("", "15",false))
//        list.add(AddMember3Item("", "16",false))
//        list.add(AddMember3Item("", "17",false))
//        list.add(AddMember3Item("", "18",false))
//        list.add(AddMember3Item("", "19",false))
//        list.add(AddMember3Item("", "20",false))
//        list.add(AddMember3Item("", "21",false))
//        list.add(AddMember3Item("", "22",false))
//        list.add(AddMember3Item("", "23",false))
//        list.add(AddMember3Item("", "24",false))
//        list.add(AddMember3Item("", "25",false))
//        list.add(AddMember3Item("", "26",false))
//        list.add(AddMember3Item("", "27",false))
//        list.add(AddMember3Item("", "28",false))
        context?.let {
            mAdapter = AddMember3Adapter(it, list)
        }

        recyclerView.adapter = mAdapter
//        val button = view.findViewById<Button>(R.id.b_add_member)
//        button.setOnClickListener {
//            val fm = fragmentManager
//            val editNameDialogFragment = AddMember3Fragment.newInstance()
//            editNameDialogFragment.show(fm!! , "fragment_edit_name")
//        }
//        val width = getResources().getDimensionPixelSize(R.dimen.popup_width);
//        val height = getResources().getDimensionPixelSize(R.dimen.popup_height);

        val closeButton = view.findViewById<ImageButton>(R.id.add_member_3_close)
        closeButton.setOnClickListener {
            this.dismiss()
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
