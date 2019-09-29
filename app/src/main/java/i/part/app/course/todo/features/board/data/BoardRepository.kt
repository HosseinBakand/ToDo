package i.part.app.course.todo.features.board.data


import androidx.lifecycle.MutableLiveData
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.core.api.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BoardRepository {
    private val retrofit = RetrofitFactory.getRetrofit()
    private val todoListServices = retrofit?.create(BoardServices::class.java)


    fun addTodoList(
        todoListName: String,
        boardId: Int
    ): MutableLiveData<Result<AddTodoListResponse?>> {
        val result = MutableLiveData<Result<AddTodoListResponse?>>()
        //result.value = Result.Loading<>
        val call: Call<AddTodoListResponse>? = todoListServices?.createTodoList(
            boardID = boardId,
            title = AddTodoListParam(todoListName)
        )
        call?.enqueue(object : Callback<AddTodoListResponse> {
            override fun onFailure(call: Call<AddTodoListResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<AddTodoListResponse>,
                response: Response<AddTodoListResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = Result.Success(response.body())
//                    (result.value as Result.Success).data?.todoListName = todoListName
                }
            }

        })
        return result
    }

    fun loadTodoLists(boardID: Int): MutableLiveData<Result<ThisBoardTodoListResponse?>> {
        var result = MutableLiveData<Result<ThisBoardTodoListResponse?>>()
//        result.value = Result.Loading()
        val call: Call<ThisBoardTodoListResponse>? = todoListServices?.getAllTodoList(boardID)
        call?.enqueue(object : Callback<ThisBoardTodoListResponse> {
            override fun onFailure(call: Call<ThisBoardTodoListResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<ThisBoardTodoListResponse>,
                response: Response<ThisBoardTodoListResponse?>
            ) {
                if (response.isSuccessful) {
                    result.value = Result.Success(response.body())
                }
            }

        })
        return result
    }


    fun getTasks(todoListId: Int): MutableLiveData<Result<List<TaskResponse>?>> {
        var result = MutableLiveData<Result<List<TaskResponse>?>>()
        //result.value = Result.Loading<>
        val call: Call<List<TaskResponse>>? =
            todoListServices?.getTasks(id = todoListId)//TODO board id shoud be give
        call?.enqueue(object : Callback<List<TaskResponse>> {
            override fun onFailure(call: Call<List<TaskResponse>>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<List<TaskResponse>>,
                response: Response<List<TaskResponse>>
            ) {
                if (response.isSuccessful) {
                    result.value = Result.Success(response.body())
                }
            }

        })
        return result
    }

    fun editTodoList(
        todoListName: String,
        id: Int
    ): MutableLiveData<Result<EditTodoListResponse?>> {
        val result = MutableLiveData<Result<EditTodoListResponse?>>()
        //result.value = Result.Loading<>
        val call: Call<EditTodoListResponse>? = todoListServices?.editTodoListName(
            id = id,
            title = AddTodoListParam(todoListName)
        )
        call?.enqueue(object : Callback<EditTodoListResponse> {
            override fun onFailure(call: Call<EditTodoListResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<EditTodoListResponse>,
                response: Response<EditTodoListResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = Result.Success(response.body())
                } else if (response.code() == 500) {
                    result.value = Result.Error("ServerError")
                }
            }

        })
        return result
    }

    fun addTask(
        addTaskParam: AddTaskParam,
        id: Int
    ): MutableLiveData<Result<AddTaskResponse?>> {
        val result = MutableLiveData<Result<AddTaskResponse?>>()
        //result.value = Result.Loading<>
        val call: Call<AddTaskResponse>? =
            todoListServices?.addTask(id = id, addTask = addTaskParam)
        call?.enqueue(object : Callback<AddTaskResponse> {
            override fun onFailure(call: Call<AddTaskResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<AddTaskResponse>,
                response: Response<AddTaskResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = Result.Success(response.body())
                }
            }

        })
        return result
    }

    fun editTask(
        addTaskParam: AddTaskParam,
        id: Int
    ): MutableLiveData<Result<EditTaskResponse?>> {
        val result = MutableLiveData<Result<EditTaskResponse?>>()
        //result.value = Result.Loading<>
        val call: Call<EditTaskResponse>? =
            todoListServices?.editTask(id = id, addTask = addTaskParam)
        call?.enqueue(object : Callback<EditTaskResponse> {
            override fun onFailure(call: Call<EditTaskResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<EditTaskResponse>,
                response: Response<EditTaskResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = Result.Success(response.body())
                } else if (response.code() == 500) {
                    result.value = Result.Error("ServerError")
                }
            }

        })
        return result
    }
}