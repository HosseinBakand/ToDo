package i.part.app.course.todo.features.board.data

import i.part.app.course.todo.features.user.data.RegisterParam
import i.part.app.course.todo.features.user.data.RegisterResponse
import retrofit2.Call
import retrofit2.http.*

interface BoardServices {

    @GET("/api/todo")
    fun getCurrentTodos(
    ): Call<List<TodoSpecification>>

    @GET("/api/boards/todosof/{boardID}")
    fun getAllTodoList(
        @Path("boardID") boardID: Int
    ): Call<ThisBoardTodoListResponse>

    @GET("todo/taskof/{id}")
    fun getTasks(
        @Path("id") id: Int
    ): Call<List<TaskEntity>>

    @POST("todo/{boardID}")
    fun createTodoList(
        @Path("boardID") boardID: Int,
        @Body title: AddTodoListParam
    ): Call<AddTodoListResponse>

    @PUT("todo/{id}")
    fun editTodoListName(
        @Path("id") id: Int,
        @Body title: AddTodoListParam
    ): Call<EditTodoListResponse>

    @DELETE("todo/{id}")
    fun deleteTodoList(
        @Path("id") id: Int
    ): Call<List<TaskEntity>>

    @GET("todo/{id}")
    fun getTodoList(
        @Path("id") id: Int
    ): Call<List<TaskEntity>>

    @POST("tasks/{todoID}")
    fun addTask(
        @Path("todoID") id: Int,
        @Body addTask: AddTaskParam
    ): Call<AddTaskResponse>

    @PUT("tasks/{id}")
    fun editTask(
        @Path("id") id: Int,
        @Body updateTaskParam: UpdateTaskParam
    ): Call<EditTaskResponse>

    @GET("tasks/assignee/{memberID}")
    fun getAssignee(@Path("memberID") taskId: Int, @Body userRegister: RegisterParam): Call<RegisterResponse>

    @GET("boards/membersof/{boardID}")
    fun getBoardMembers(@Path("boardID") boardID: Int): Call<ListResponse<BoardMemberResponse>>

    @POST("boards/addmemberto/{boardID}")
    fun addUserToBoard(@Path("boardID") boardID: Int, @Body addUserParam: AddUserParam): Call<AddUserResponse>

    @GET("users")
    fun getAllUsers(): Call<List<BoardMemberResponse>>


    @POST("todo/{boardID}")
    fun addMemberToTas(
        @Path("boardID") boardID: Int,
        @Body title: AddTodoListParam
    ): Call<AddTodoListResponse>


    @GET("boards")
    fun getBoards(
    ): Call<List<BoardResponse>>

    @POST("boards")
    fun createBoard(
        @Body newBoardParam: NewBoardParam
    ): Call<NewBoardResponse>

    @GET("boards/{id}")
    fun getBoardById(
        @Path("id") id: Int
    ): Call<BoardDetailResponse>

    @PUT("boards/{id}")
    fun updateBoardTitle(
        @Path("id") id: Int,
        @Body updateBoardParam: UpdateBoardParam
    ): Call<UpdateBoardResponse>

    @DELETE("boards/{id}")
    fun deleteBoardById(
        @Path("id") id: Int
    ): Call<DeleteBoardResponse>


    @DELETE("boards/removeMember/{boardID}-{username}")
    fun removeMemberfromBoard(
        @Path("boardID") boardID: Int, @Path("username") username: String
    ): Call<RemoveMemberResponse>
}