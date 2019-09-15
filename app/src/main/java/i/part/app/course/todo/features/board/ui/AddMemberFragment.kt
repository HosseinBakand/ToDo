package i.part.app.course.todo.features.board.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import i.part.app.course.todo.R

class AddMember : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var mAdapter: RecyclerView.Adapter<*>
    lateinit var layoutManager: RecyclerView.LayoutManager
    lateinit var list: ArrayList<AddMemberItem>
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_add_member, container, false)
        recyclerView = view.findViewById(R.id.rv_add_member_2_new_members) as RecyclerView
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        list = ArrayList()
        //Adding Data into ArrayList
        val url =
            "https://www.irreverentgent.com/wp-content/uploads/2018/03/Awesome-Profile-Pictures-for-Guys-look-away2.jpg"
        list.add(AddMemberItem(url, "Project Manager"))
        list.add(AddMemberItem("Bradley Matthews", "Senior Developer"))
        list.add(AddMemberItem(url, "Lead Developer"))
        list.add(AddMemberItem("Gary Thompson", "Lead Developer"))
        list.add(AddMemberItem("Corey Williamson", "UI/UX Developer"))
        list.add(AddMemberItem("Samuel Jones", "Front-End Developer"))
        list.add(AddMemberItem("Michael Read", "Backend Developer"))
        list.add(AddMemberItem("Robert Phillips", "Android Developer"))
        list.add(AddMemberItem("Albert Stewart", "Web Developer"))
        list.add(AddMemberItem("Wayne Diaz", "Junior Devafdsgeloper"))
        list.add(AddMemberItem("Todd Miller", "Project Manageafdr"))
        list.add(AddMemberItem("Bradley Matthews", "Senior Devefaloper"))
        list.add(AddMemberItem("Harley Gibson", "Lead Developfaer"))
        list.add(AddMemberItem("Gary Thompson", "Lead Developedfr"))
        list.add(AddMemberItem("Corey Williamson", "UI/UX Develoabper"))
        list.add(AddMemberItem("Samuel Jones", "Front-End Developeafr"))
        list.add(AddMemberItem("Michael Read", "Backend Developxcvxcber"))
        list.add(AddMemberItem("Robert Phillips", "Android Developeiyl,r"))
        list.add(AddMemberItem("Albert Stewart", "Web Developesadgadsr"))
        list.add(AddMemberItem("Wayne Diaz", "Junior Developerasfbafs"))
        list.add(AddMemberItem("Todd Miller", "Project Manageradva"))
        list.add(AddMemberItem("Bradley Matthews", "Senior Developsdgsder"))
        list.add(AddMemberItem("Harley Gibson", "Lead Developer"))
        list.add(AddMemberItem("Gary Thompson", "Lead Developer"))
        list.add(AddMemberItem("Corey Williamson", "UI/UX Developer"))
        list.add(AddMemberItem("Samuel Jones", "Front-End Developer"))
        list.add(AddMemberItem("Michael Read", "Backend Developer"))
        list.add(AddMemberItem("Robert Phillips", "Android Developer"))
        list.add(AddMemberItem("Albert Stewart", "Web Developer"))
        list.add(AddMemberItem("Wayne Diaz", "Junior Developer"))
        list.add(AddMemberItem("Todd Miller", "Project Manager"))
        list.add(AddMemberItem("Bradley Matthews", "Senior Developer"))
        list.add(AddMemberItem("Harley Gibson", "Lead Developer"))
        list.add(AddMemberItem("Gary Thompson", "Lead Developer"))
        list.add(AddMemberItem("Corey Williamson", "UI/UX Developer"))
        list.add(AddMemberItem("Samuel Jones", "Front-End Developer"))
        list.add(AddMemberItem("Michael Read", "Backend Developer"))
        list.add(AddMemberItem("Robert Phillips", "Android Developer"))
        list.add(AddMemberItem("Albert Stewart", "Web Developer"))
        list.add(AddMemberItem("Wayne Diaz", "Junior Developer"))
        context?.let {
            mAdapter = AddMemberAdapter(list)
        }
        recyclerView.adapter = mAdapter
        val button = view.findViewById<Button>(R.id.btn_add_member)
        button.setOnClickListener {
            this.findNavController().navigate(R.id.action_addMember2_to_addMember3Fragment)
        }
        val toolbar = view.findViewById<MaterialToolbar>(R.id.mt_add_member_2)
        toolbar.setNavigationOnClickListener {
            when (arguments?.getString("fragmentType")) {
                "add_board" -> this.findNavController().navigate(R.id.action_addMember2_to_add_board)
                "edit_board" -> this.findNavController().navigate(R.id.action_addMember2_to_edit_board)
                "add_task" -> this.findNavController().navigate(R.id.action_addMember2_to_addTaskFragment)
                "board_detail" -> this.findNavController().navigate(R.id.action_addMember2_to_board_detail)
            }
        }
        return view
    }
}
