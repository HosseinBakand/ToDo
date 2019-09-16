package i.part.app.course.todo.features.board.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import i.part.app.course.todo.R
import kotlinx.android.synthetic.main.fragment_add_member.*


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
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recyclerView = rv_add_member_2_new_members
        recyclerView.setHasFixedSize(true)
        layoutManager = LinearLayoutManager(context)
        recyclerView.layoutManager = layoutManager
        list = ArrayList()
        //Adding Data into ArrayList
        val url =
            "https://www.irreverentgent.com/wp-content/uploads/2018/03/Awesome-Profile-Pictures-for-Guys-look-away2.jpg"
        list.add(AddMemberItem(url, "1Project Manager"))
        list.add(AddMemberItem("Bradley Matthews", "2Senior Developer"))
        list.add(AddMemberItem(url, "3Lead Developer"))
        list.add(AddMemberItem("Gary Thompson", "4Lead Developer"))
        list.add(AddMemberItem("Corey Williamson", "5UI/UX Developer"))
        list.add(AddMemberItem("Samuel Jones", "6Front-End Developer"))
        list.add(AddMemberItem("Michael Read", "7Backend Developer"))
        list.add(AddMemberItem("Robert Phillips", "8Android Developer"))
        list.add(AddMemberItem("Albert Stewart", "9Web Developer"))
        list.add(AddMemberItem("Wayne Diaz", "10Junior Devafdsgeloper"))
        list.add(AddMemberItem("Todd Miller", "11Project Manageafdr"))
        list.add(AddMemberItem("Bradley Matthews", "12Senior Devefaloper"))
        list.add(AddMemberItem("Harley Gibson", "13Lead Developfaer"))
        list.add(AddMemberItem("Gary Thompson", "14Lead Developedfr"))
        list.add(AddMemberItem("Corey Williamson", "15UI/UX Develoabper"))
        list.add(AddMemberItem("Samuel Jones", "16Front-End Developeafr"))
        list.add(AddMemberItem("Michael Read", "17Backend Developxcvxcber"))
        list.add(AddMemberItem("Robert Phillips", "18Android Developeiyl,r"))
        list.add(AddMemberItem("Albert Stewart", "19Web Developesadgadsr"))
        list.add(AddMemberItem("Wayne Diaz", "20Junior Developerasfbafs"))
        list.add(AddMemberItem("Todd Miller", "21Project Manageradva"))
        list.add(AddMemberItem("Bradley Matthews", "22Senior Developsdgsder"))
        list.add(AddMemberItem("Harley Gibson", "23Lead Developer"))
        list.add(AddMemberItem("Gary Thompson", "24Lead Developer"))
        list.add(AddMemberItem("Corey Williamson", "25UI/UX Developer"))
        list.add(AddMemberItem("Samuel Jones", "26Front-End Developer"))
        list.add(AddMemberItem("Michael Read", "27Backend Developer"))
        list.add(AddMemberItem("Robert Phillips", "28Android Developer"))
        list.add(AddMemberItem("Albert Stewart", "29Web Developer"))
        list.add(AddMemberItem("Wayne Diaz", "30Junior Developer"))
        list.add(AddMemberItem("Todd Miller", "31Project Manager"))
        list.add(AddMemberItem("Bradley Matthews", "32Senior Developer"))
        list.add(AddMemberItem("Harley Gibson", "33Lead Developer"))
        list.add(AddMemberItem("Gary Thompson", "34Lead Developer"))
        list.add(AddMemberItem("Corey Williamson", "35UI/UX Developer"))
        list.add(AddMemberItem("Samuel Jones", "36Front-End Developer"))
        list.add(AddMemberItem("Michael Read", "37Backend Developer"))
        list.add(AddMemberItem("Robert Phillips", "38Android Developer"))
        list.add(AddMemberItem("Albert Stewart", "39Web Developer"))
        list.add(AddMemberItem("Wayne Diaz", "40Junior Developer"))
        context?.let {
            mAdapter = AddMemberAdapter(list)
        }
        recyclerView.adapter = mAdapter
        btn_add_member.setOnClickListener {
            this.findNavController().navigate(R.id.action_addMember2_to_addMember3Fragment)
        }
        mt_add_member_2.setNavigationOnClickListener {
            when (arguments?.getString("fragmentType")) {
                "add_board" -> this.findNavController().navigate(R.id.action_addMember2_to_add_board)
                "edit_board" -> this.findNavController().navigate(R.id.action_addMember2_to_edit_board)
                "add_task" -> this.findNavController().navigate(i.part.app.course.todo.R.id.action_addMember2_to_addTaskFragment)
                "board_detail" -> this.findNavController().navigate(i.part.app.course.todo.R.id.action_addMember2_to_board_detail)
            }
        }


    }
}
