package i.part.app.course.todo.features.board.ui

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R

class AddMember3Fragment : DialogFragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<*>
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var list: ArrayList<AddMember3Item>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog?.setCanceledOnTouchOutside(false)
        var view = inflater.inflate(R.layout.fragment_add_member_3, container, false)
        recyclerView = view.findViewById(R.id.rv_add_member_3) as RecyclerView
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
        context?.let {
            mAdapter = AddMember3Adapter(it, list)
        }
        recyclerView.adapter = mAdapter
        val closeButton = view.findViewById<ImageButton>(R.id.ib_add_member_3_close)
        closeButton.setOnClickListener {
            this.findNavController().navigate(R.id.action_addMember3Fragment_to_addMember2)
        }
        return view
    }
}
