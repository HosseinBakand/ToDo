package i.part.app.course.todo.features.board.data


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import i.part.app.course.todo.MyApplication
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.core.api.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class BoardRepository {
    private val retrofit = RetrofitFactory.getRetrofit()
    private val boardServices = retrofit?.create(BoardServices::class.java)
    private val localDataSource = LocalDataSource()
    val boards = localDataSource.getBoards()
    val currentTodos = localDataSource.getAllBoardsTodos()

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

    fun getTodos(boardID: Int): LiveData<List<TodoListDto>>? {
        return localDataSource.getTodoLists(boardID)
    }

    fun loadTodoLists(boardID: Int): MutableLiveData<Result<String?>> {
        var result = MutableLiveData<Result<String?>>()
//        result.value = Result.Loading()
        val call: Call<ThisBoardTodoListResponse>? = boardServices?.getAllTodoList(boardID)
        call?.enqueue(object : Callback<ThisBoardTodoListResponse> {
            override fun onFailure(call: Call<ThisBoardTodoListResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<ThisBoardTodoListResponse>,
                entity: Response<ThisBoardTodoListResponse?>
            ) {
                if (entity.isSuccessful) {
                    entity.body()?.let {
                        localDataSource.insertTodoLists(it)
                        result.value = Result.Success("Success")
                    }
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

    fun getBoardMembersDB(boardID: Int): LiveData<List<MemberOfBoardEntity>>? {
        return localDataSource.getBoardUsers(boardID)
    }

    fun getBoardMembers(boardID: Int): MutableLiveData<Result<String>> {
        val result = MutableLiveData<Result<String>>()
        val call: Call<ListResponse<BoardMemberEntity>>? = boardServices?.getBoardMembers(boardID)
        call?.enqueue(object : Callback<ListResponse<BoardMemberEntity>> {
            override fun onFailure(call: Call<ListResponse<BoardMemberEntity>>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<ListResponse<BoardMemberEntity>>,
                entity: Response<ListResponse<BoardMemberEntity>>
            ) {
                when {
                    entity.isSuccessful -> {
                        result.value = Result.Success("Success")
                        localDataSource.insertBoardMember(entity.body()?.result, boardID)
                    }
                    else -> result.value = Result.Error("Invalid request")
                }
            }
        })
        return result
    }

    fun getAllUsersDB(): LiveData<List<BoardMemberEntity>>? {
        return localDataSource.getAllusers()
    }


    fun getAllUsers(): MutableLiveData<Result<String>> {
        val result = MutableLiveData<Result<String>>()
        val call: Call<List<BoardMemberEntity>>? = boardServices?.getAllUsers()
        call?.enqueue(object : Callback<List<BoardMemberEntity>> {
            override fun onFailure(call: Call<List<BoardMemberEntity>>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<List<BoardMemberEntity>>,
                entity: Response<List<BoardMemberEntity>>
            ) {
                when {
                    entity.isSuccessful -> {
                        result.value = Result.Success("Success")
                        localDataSource.insertUser(entity.body())
                    }
                    else -> result.value = Result.Error("Invalid request")
                }
            }
        })
        return result
    }

    fun getBoardsFromRemoteDataSource(): MutableLiveData<Result<String>> {
        val result = MutableLiveData<Result<String>>()
        if (MyApplication.isConnectedToNetwork) {
            val call: Call<List<BoardEntity>>? = boardServices?.getBoards()
            call?.enqueue(object : Callback<List<BoardEntity>> {
                override fun onFailure(call: Call<List<BoardEntity>>, t: Throwable) {
                    result.value = Result.Error("Connection error")
                }

                override fun onResponse(
                    call: Call<List<BoardEntity>>,
                    response: Response<List<BoardEntity>>
                ) {
                    when {
                        response.isSuccessful -> {
                            localDataSource.deleteAllBoards()
                            localDataSource.insertBoards(response.body() ?: mutableListOf())
                            result.value = Result.Success("Success")
                        }
                        response.code() == 401 -> result.value = Result.Error("Unauthorized")
                    }
                }
            })
        } else {
            result.value = Result.Error("No Internet connection")
        }
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
                    response.isSuccessful -> {
                        localDataSource.insertBoard(
                            BoardEntity(
                                response.body()?.data?.boardId,
                                newBoardParam.title,
                                newBoardParam.owner_name
                            )
                        )
                        result.value = Result.Success(response.body())
                    }
                    response.code() == 400 -> result.value =
                        Result.Error(response.body()?.message ?: "invalid request")
                    response.code() == 401 -> result.value =
                        Result.Error(response.body()?.message ?: "Unauthorized")
                }
            }
        })
        return result
    }

    fun getBoardByIdFromLocalDataSource(id: Int): LiveData<BoardDetailEntity> {
        return localDataSource.getBoardById(id)
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
                    response.isSuccessful -> {
                        localDataSource.updateBoardTitle(id, title)
                        result.value = Result.Success(response.body())
                    }
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
                    response.isSuccessful -> {
                        localDataSource.deleteBoardById(id)
                        result.value = Result.Success(response.body())
                    }
                    response.code() == 404 -> result.value =
                        Result.Error(response.message() ?: "Board not found")
                }
            }
        })
        return result
    }

    fun getCurrentTodos(): MutableLiveData<Result<String?>> {
        val result = MutableLiveData<Result<String?>>()
        val call: Call<List<TodoListDto>>? = boardServices?.getCurrentTodos()
        call?.enqueue(object : Callback<List<TodoListDto>> {
            override fun onFailure(call: Call<List<TodoListDto>>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<List<TodoListDto>>,
                entity: Response<List<TodoListDto>>
            ) {
                when {
                    entity.isSuccessful -> {
                        entity.body()?.let {
                            localDataSource.insertTodoLists(it)
                            result.value = Result.Success("Success")
                        }
                    }
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