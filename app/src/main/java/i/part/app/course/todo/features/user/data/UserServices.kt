package i.part.app.course.todo.features.user.data


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserServices {
    @POST("signup")
    fun registerUser(@Body userRegister: RegisterParam): Call<RegisterResponse>

    @POST("login")
    fun loginUser(@Body loginUser: LoginParam): Call<LoginResponse>
}