package i.part.app.course.todo.features.board.data

import i.part.app.course.todo.MyApplication
import retrofit2.Call
import retrofit2.http.*

interface BoardServices {
    @GET("/api/boards/todosof/{boardID}")
    fun getAllTodoList(
        @Path("boardID") boardID: Int,
        @Header("Authorization") token: String = MyApplication.TOKEN as String
    ): Call<ThisBoardTodoListResponse>

    @GET("todo/taskof/{id}")
    fun getTasks(
        @Path("id") id: Int,
        @Header("Authorization") token: String = MyApplication.TOKEN as String
    ): Call<List<TaskResponse>>

    @POST("todo/{boardID}")
    fun createTodoList(
        @Header("Authorization") token: String = MyApplication.TOKEN as String,
        @Path("boardID") boardID: Int,
        @Body title: AddTodoListParam
    ): Call<AddTodoListResponse>

    @PUT("todo/{id}")
    fun editTodoListName(
        @Path("id") id: Int,
        @Header("Authorization") token: String = MyApplication.TOKEN as String,
        @Body title: AddTodoListParam
    ): Call<EditTodoListResponse>

    @DELETE("todo/{id}")
    fun deleteTodoList(
        @Path("id") id: Int,
        @Header("Authorization") token: String = MyApplication.TOKEN as String
    ): Call<List<TaskResponse>>

    @GET("todo/{id}")
    fun getTodoList(
        @Path("id") id: Int,
        @Header("Authorization") token: String = MyApplication.TOKEN as String
    ): Call<List<TaskResponse>>

    @POST("tasks/{todoID}")
    fun addTask(
        @Path("todoID") id: Int,
        @Header("Authorization") token: String = MyApplication.TOKEN as String,
        @Body addTask: AddTaskParam
    ): Call<AddTaskResponse>

    @PUT("tasks/{id}")
    fun editTask(
        @Path("id") id: Int,
        @Header("Authorization") token: String = MyApplication.TOKEN as String,
        @Body addTask: AddTaskParam
    ): Call<EditTaskResponse>
}