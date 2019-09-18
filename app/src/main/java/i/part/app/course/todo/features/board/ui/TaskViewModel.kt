package i.part.app.course.todo.features.board.ui


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class TaskViewModel : ViewModel() {
    private val _task = MutableLiveData<TaskView>()
    val task: LiveData<TaskView>
        get() = _task

    fun getTask() {
        _task.value = loadTask()
    }

    private fun loadTask(): TaskView {
        return TaskView("Mohammad Bahadori")
    }
}