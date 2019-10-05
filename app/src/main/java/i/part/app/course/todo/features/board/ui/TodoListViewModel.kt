package i.part.app.course.todo.features.board.ui


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.ImageButton
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButton
import i.part.app.course.todo.R
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.features.board.data.*


class TodoListViewModel : ViewModel() {
    private var _addTodoListResponse = MutableLiveData<Result<AddTodoListResponse?>>()
    val addTodoListResponse: MutableLiveData<Result<AddTodoListResponse?>>
        get() = _addTodoListResponse

    private var _editTodoListResponse = MutableLiveData<Result<EditTodoListResponse?>>()
    val editTodoListResponse: MutableLiveData<Result<EditTodoListResponse?>>
        get() = _editTodoListResponse

    private var _todoLists = MutableLiveData<Result<ThisBoardTodoListResponse?>>()
    val todoLists: MutableLiveData<Result<ThisBoardTodoListResponse?>>
        get() = _todoLists

    private var _addTask = MutableLiveData<Result<AddTaskResponse?>>()
    val addTask: MutableLiveData<Result<AddTaskResponse?>>
        get() = _addTask

    private var _addTaskChanged = MutableLiveData<Boolean>()
    val addTaskChanged: MutableLiveData<Boolean>
        get() = _addTaskChanged

    val repository = BoardRepository()

    fun getTodoLists(boardID: Int) {
        _todoLists = repository.loadTodoLists(boardID)
    }

    fun editToDoListName(id: Int, newName: String) {
        _editTodoListResponse = repository.editTodoList(newName, id)
    }

    fun addTask(id: Int, addTaskParam: AddTaskParam) {
        _addTask = repository.addTask(id = id, addTaskParam = addTaskParam)
        addTaskChanged.value?.let {
            _addTaskChanged.value = !(it)
        }
    }

    fun editTask(id: Int, updateTaskParam: UpdateTaskParam) {
        repository.editTask(id = id, updateTaskParam = updateTaskParam)

    }

    fun addTodoList(name: String, boardId: Int) {
        _addTodoListResponse = repository.addTodoList(name, boardId)
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