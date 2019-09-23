package i.part.app.course.todo.features.board.ui


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ImageButton
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButton
import i.part.app.course.todo.R


class TodoListViewModel : ViewModel() {
    private val _todolists = MutableLiveData<List<TodoListView>>()
    val todolists: LiveData<List<TodoListView>>
        get() = _todolists

    fun getTodoLists() {
        _todolists.value = loadTodoLists()
    }

    fun editToDoListName(myTodoListView: TodoListView, newName: String) {
        todolists.value?.indexOf(myTodoListView)?.let { index ->
            var firstHalf = todolists.value?.slice(0..index - 1)
            var listSize = (todolists.value?.size)
            listSize?.let { listSize ->
                var secondHalf = todolists.value?.slice(index + 1..listSize - 1)
                myTodoListView.todoListName = newName
                firstHalf = firstHalf?.plus(myTodoListView)
                var finalList = mutableListOf<TodoListView>()
                firstHalf?.let {
                    finalList.addAll(it)
                    secondHalf?.let { ss ->
                        finalList.addAll(ss)
                        _todolists.value = finalList
                    }
                }
            }
            //index.let { todolists.value?.get(it)?.todoListName = newName }
        }
    }

    fun addTask(myTodoListViewPosition: Int, mySubTaskView: SubTaskView) {
        myTodoListViewPosition.let { index ->
            var firstHalf = todolists.value?.slice(0..index - 1)
            var listSize = (todolists.value?.size)
            listSize?.let { listSize ->
                var secondHalf = todolists.value?.slice(index + 1..listSize - 1)
                //myTodoListView.subtasks. = newName
                var temp = todolists.value?.get(myTodoListViewPosition)
                temp?.subtasks?.add(mySubTaskView)
                temp?.let { t -> firstHalf = firstHalf?.plus(t) }
                var finalList = mutableListOf<TodoListView>()
                firstHalf?.let {
                    finalList.addAll(it)
                    secondHalf?.let { ss ->
                        finalList.addAll(ss)
                        _todolists.value = finalList
                    }
                }
            }
            //index.let { todolists.value?.get(it)?.todoListName = newName }
        }
    }

    fun addTodoList(name: String) {
        val buttonTemp =
            TodoListView(TodoListType.ADD_TODOLIST_BUTTON, "button", mutableListOf())
        _todolists.value?.let { list ->
            _todolists.value = list.minus(list.get(list.size - 1))
        }

        _todolists.value = _todolists.value?.plus(
            TodoListView(
                TodoListType.TODOLIST,
                name,
                mutableListOf()
            )
        )
        _todolists.value = _todolists.value?.plus(buttonTemp)
        //notifyItemInserted(position-1)
        //notifyItemRangeChanged(position-1,itemCount)
        //dialog.dismiss()
        //recyclerView.scrollToPosition(position)

    }

    private fun loadTodoLists(): MutableList<TodoListView> {
        val fakeLink =
            "https://cdn.business2community.com/wp-content/uploads/2017/08/blank-profile-picture-973460_640.png"
        val todoListViews: ArrayList<TodoListView> = ArrayList()
        todoListViews.apply {
            add(
                TodoListView(
                    TodoListType.TODOLIST,
                    "poker",
                    mutableListOf(
                        SubTaskView("this is a good subtask", fakeLink),
                        SubTaskView("this is a nasty subtask", fakeLink),
                        SubTaskView("todo", fakeLink),
                        SubTaskView("nice subtask!", fakeLink)
                    )
                )
            )
            add(
                TodoListView(
                    TodoListType.TODOLIST,
                    "joker",
                    mutableListOf(
                        SubTaskView("this is a good subtask", fakeLink),
                        SubTaskView("todo", fakeLink),
                        SubTaskView("nice subtask!", fakeLink)
                    )
                )
            )
            add(
                TodoListView(
                    TodoListType.TODOLIST,
                    "stalker",
                    mutableListOf(
                        SubTaskView("this is a good subtask", fakeLink),
                        SubTaskView("this is a nasty subtask", fakeLink),
                        SubTaskView("todo", fakeLink)
                    )
                )
            )
            add(
                TodoListView(
                    TodoListType.TODOLIST,
                    "walker",
                    mutableListOf(
                        SubTaskView("this is a good subtask", fakeLink),
                        SubTaskView("nice subtask!", fakeLink)
                    )
                )
            )
            add(
                TodoListView(
                    TodoListType.TODOLIST,
                    "nasty jobs todolist",
                    mutableListOf(
                        SubTaskView("this is a good subtask", fakeLink),
                        SubTaskView("this is a nasty subtask", fakeLink),
                        SubTaskView("Good one", fakeLink),
                        SubTaskView("harsh one", fakeLink),
                        SubTaskView("hard one", fakeLink),
                        SubTaskView("impossible one", fakeLink),
                        SubTaskView("nice subtask!", fakeLink)
                    )
                )
            )
            add(
                TodoListView(
                    TodoListType.TODOLIST,
                    "kill bill",
                    mutableListOf(
                        SubTaskView("this is a good subtask", fakeLink),
                        SubTaskView("this is a nasty subtask", fakeLink),
                        SubTaskView("todo", fakeLink),
                        SubTaskView("nice subtask!", fakeLink)
                    )
                )
            )
            add(
                TodoListView(
                    TodoListType.ADD_TODOLIST_BUTTON,
                    "button",
                    mutableListOf()
                )
            )
        }
        return todoListViews
    }

    fun onEditClicked(dialog: Dialog) {
        dialog.window?.requestFeature(Window.FEATURE_NO_TITLE)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_edit_todolist_name)
        dialog.setCanceledOnTouchOutside(false)
        val okButton = dialog.findViewById<MaterialButton>(R.id.btn_edit_todolist_confirm)
        okButton?.setOnClickListener {
            dialog.dismiss()
        }
        val closeButton = dialog.findViewById<ImageButton>(R.id.ib_edit_todolist_close)
        closeButton?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }
}