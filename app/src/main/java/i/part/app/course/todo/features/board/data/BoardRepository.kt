package i.part.app.course.todo.features.board.data


import androidx.lifecycle.MutableLiveData
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.core.api.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BoardRepository {
    private val retrofit = RetrofitFactory.getRetrofit()
    val boardServices = retrofit?.create(BoardServices::class.java)


    fun addTodoList(
        todoListName: String,
        boardId: Int
    ): MutableLiveData<Result<AddTodoListResponse?>> {
        val result = MutableLiveData<Result<AddTodoListResponse?>>()
        val call: Call<AddTodoListResponse>? = boardServices?.createTodoList(
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
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
                }
            }

        })
        return result
    }

    fun loadTodoLists(boardID: Int): MutableLiveData<Result<ThisBoardTodoListResponse?>> {
        var result = MutableLiveData<Result<ThisBoardTodoListResponse?>>()
//        result.value = Result.Loading()
        val call: Call<ThisBoardTodoListResponse>? = boardServices?.getAllTodoList(boardID)
        call?.enqueue(object : Callback<ThisBoardTodoListResponse> {
            override fun onFailure(call: Call<ThisBoardTodoListResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<ThisBoardTodoListResponse>,
                response: Response<ThisBoardTodoListResponse?>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
                }
            }

        })
        return result
    }

    fun setUserToBoard(
        boardID: Int,
        addUserParam: AddUserParam
    ): MutableLiveData<Result<AddUserResponse?>> {
        val result = MutableLiveData<Result<AddUserResponse?>>()
        var call: Call<AddUserResponse>? =
            boardServices?.addUserToBoard(boardID = boardID, addUserParam = addUserParam)
        result.value = Result.Error(" before request")
        call?.enqueue(object : Callback<AddUserResponse> {
            override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<AddUserResponse>,
                response: Response<AddUserResponse>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
                }
            }

        })
        return result
    }


    fun getTasks(todoListId: Int): MutableLiveData<Result<List<TaskEntity>?>> {
        var result = MutableLiveData<Result<List<TaskEntity>?>>()
        //result.value = Result.Loading<>
        val call: Call<List<TaskEntity>>? =
            boardServices?.getTasks(id = todoListId)
        call?.enqueue(object : Callback<List<TaskEntity>> {
            override fun onFailure(call: Call<List<TaskEntity>>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<List<TaskEntity>>,
                response: Response<List<TaskEntity>>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
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
        val call: Call<EditTodoListResponse>? = boardServices?.editTodoListName(
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
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
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
            boardServices?.addTask(id = id, addTask = addTaskParam)
        call?.enqueue(object : Callback<AddTaskResponse> {
            override fun onFailure(call: Call<AddTaskResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<AddTaskResponse>,
                response: Response<AddTaskResponse>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
                }
            }

        })
        return result
    }

    fun editTask(
        updateTaskParam: UpdateTaskParam,
        id: Int
    ): MutableLiveData<Result<EditTaskResponse?>> {
        val result = MutableLiveData<Result<EditTaskResponse?>>()
        //result.value = Result.Loading<>
        val call: Call<EditTaskResponse>? =
            boardServices?.editTask(id = id, updateTaskParam = updateTaskParam)
        call?.enqueue(object : Callback<EditTaskResponse> {
            override fun onFailure(call: Call<EditTaskResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<EditTaskResponse>,
                response: Response<EditTaskResponse>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
                }
            }

        })
        return result
    }

    fun getBoardMembers(boardID: Int): MutableLiveData<Result<ListResponse<BoardMemberResponse>?>> {
        val result = MutableLiveData<Result<ListResponse<BoardMemberResponse>?>>()
        val call: Call<ListResponse<BoardMemberResponse>>? = boardServices?.getBoardMembers(boardID)
        call?.enqueue(object : Callback<ListResponse<BoardMemberResponse>> {
            override fun onFailure(call: Call<ListResponse<BoardMemberResponse>>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<ListResponse<BoardMemberResponse>>,
                response: Response<ListResponse<BoardMemberResponse>>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
                }

            }

        })
        return result
    }


    fun getAllUsers(): MutableLiveData<Result<List<BoardMemberResponse>?>> {
        val result = MutableLiveData<Result<List<BoardMemberResponse>?>>()
        val call: Call<List<BoardMemberResponse>>? = boardServices?.getAllUsers()
        call?.enqueue(object : Callback<List<BoardMemberResponse>> {
            override fun onFailure(call: Call<List<BoardMemberResponse>>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<List<BoardMemberResponse>>,
                response: Response<List<BoardMemberResponse>>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
                }

            }

        })
        return result
    }


    fun getBoards(): MutableLiveData<Result<List<BoardResponse>?>> {
        val result = MutableLiveData<Result<List<BoardResponse>?>>()
        val call: Call<List<BoardResponse>>? = boardServices?.getBoards()
        result.value = Result.Loading(null)
        call?.enqueue(object : Callback<List<BoardResponse>> {
            override fun onFailure(call: Call<List<BoardResponse>>, t: Throwable) {
                result.value = Result.Error("Connection error")
            }

            override fun onResponse(
                call: Call<List<BoardResponse>>,
                response: Response<List<BoardResponse>>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    response.code() == 401 -> result.value = Result.Error("Unauthorized")
                }
            }
        })
        return result
    }

    fun createBoard(newBoardParam: NewBoardParam): MutableLiveData<Result<NewBoardResponse?>> {
        val result = MutableLiveData<Result<NewBoardResponse?>>()
        val call: Call<NewBoardResponse>? =
            boardServices?.createBoard(newBoardParam = newBoardParam)
        call?.enqueue(object : Callback<NewBoardResponse> {
            override fun onFailure(call: Call<NewBoardResponse>, t: Throwable) {
                result.value = Result.Error("Connection error", null)
            }

            override fun onResponse(
                call: Call<NewBoardResponse>,
                response: Response<NewBoardResponse>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    response.code() == 400 -> result.value =
                        Result.Error(response.body()?.message ?: "invalid request")
                    response.code() == 401 -> result.value =
                        Result.Error(response.body()?.message ?: "Unauthorized")
                }
            }
        })
        return result
    }

    fun getBoardById(id: Int): MutableLiveData<Result<BoardDetailResponse?>> {
        val result = MutableLiveData<Result<BoardDetailResponse?>>()
        val call: Call<BoardDetailResponse>? = boardServices?.getBoardById(id = id)
        call?.enqueue(object : Callback<BoardDetailResponse> {
            override fun onFailure(call: Call<BoardDetailResponse>, t: Throwable) {
                result.value = Result.Error("Connection error", null)
            }

            override fun onResponse(
                call: Call<BoardDetailResponse>,
                response: Response<BoardDetailResponse>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    response.code() == 404 -> result.value =
                        Result.Error(response.message() ?: "Board not found")
                    response.code() == 401 -> result.value =
                        Result.Error(response.message() ?: "Unauthorized")
                }
            }
        })
        return result
    }

    fun updateBoardTitle(id: Int, title: String): MutableLiveData<Result<UpdateBoardResponse?>> {
        val result = MutableLiveData<Result<UpdateBoardResponse?>>()
        val call: Call<UpdateBoardResponse>? =
            boardServices?.updateBoardTitle(id = id, updateBoardParam = UpdateBoardParam(title))
        call?.enqueue(object : Callback<UpdateBoardResponse> {
            override fun onFailure(call: Call<UpdateBoardResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<UpdateBoardResponse>,
                response: Response<UpdateBoardResponse>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    response.code() == 400 -> result.value =
                        Result.Error(response.message() ?: "Body is not valid")
                    response.code() == 404 -> result.value =
                        Result.Error(response.message() ?: "Board not found")
                    response.code() == 500 -> result.value =
                        Result.Error(response.message() ?: "Internal server error")
                }
            }
        })
        return result
    }

    fun deleteBoardById(id: Int): MutableLiveData<Result<DeleteBoardResponse?>> {
        val result = MutableLiveData<Result<DeleteBoardResponse?>>()
        val call: Call<DeleteBoardResponse>? = boardServices?.deleteBoardById(id = id)
        call?.enqueue(object : Callback<DeleteBoardResponse> {
            override fun onFailure(call: Call<DeleteBoardResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<DeleteBoardResponse>,
                response: Response<DeleteBoardResponse>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    response.code() == 404 -> result.value =
                        Result.Error(response.message() ?: "Board not found")
                }
            }
        })
        return result
    }

    fun getCurrentTodos(): MutableLiveData<Result<List<TodoSpecification>?>> {
        val result = MutableLiveData<Result<List<TodoSpecification>?>>()
        val call: Call<List<TodoSpecification>>? = boardServices?.getCurrentTodos()
        call?.enqueue(object : Callback<List<TodoSpecification>> {
            override fun onFailure(call: Call<List<TodoSpecification>>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<List<TodoSpecification>>,
                response: Response<List<TodoSpecification>>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
                }
            }
        })
        return result
    }

    fun removeMemberFromBoard(
        boardID: Int,
        userName: String
    ): MutableLiveData<Result<RemoveMemberResponse?>> {
        val result = MutableLiveData<Result<RemoveMemberResponse?>>()
        val call: Call<RemoveMemberResponse>? =
            boardServices?.removeMemberfromBoard(boardID = boardID, username = userName)
        call?.enqueue(object : Callback<RemoveMemberResponse> {
            override fun onFailure(call: Call<RemoveMemberResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<RemoveMemberResponse>,
                response: Response<RemoveMemberResponse>
            ) {
                when {
                    response.isSuccessful -> result.value = Result.Success(response.body())
                    else -> result.value = Result.Error("Invalid request")
                }
            }

        })
        return result
    }

}