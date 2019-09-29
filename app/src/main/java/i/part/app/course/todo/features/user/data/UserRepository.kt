package i.part.app.course.todo.features.user.data

import androidx.lifecycle.MutableLiveData
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.core.api.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository {
    private val retrofit = RetrofitFactory.getRetrofit()
    private val userServices = retrofit?.create(UserServices::class.java)


    fun register(userParam: RegisterParam): MutableLiveData<Result<RegisterResponse?>> {
        var result = MutableLiveData<Result<RegisterResponse?>>()
        //result.value = Result.Loading<>
        val call: Call<RegisterResponse>? = userServices?.registerUser(userParam)
        call?.enqueue(object : Callback<RegisterResponse> {
            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = Result.Success(response.body())
                } else if (response.code() == 400) {
                    result.value = Result.Error("Exist")
                }
            }

        })
        return result
    }

    fun login(userParam: LoginParam): MutableLiveData<Result<LoginResponse?>> {
        var result = MutableLiveData<Result<LoginResponse?>>()
        //result.value = Result.Loading<>
        val call: Call<LoginResponse>? = userServices?.loginUser(userParam)
        call?.enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                result.value = Result.Error("ConnectionError", null)
            }

            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    result.value = Result.Success(response.body())
                } else if (response.code() == 400) {
                    if (response.message().contains("Bad Request")) {
                        result.value = Result.Error("Password Not Verified")
                    }
                } else if (response.code() == 404) {
                    if (response.message() == "Not Found") {
                        result.value = Result.Error("Not Found")
                    }
                }
            }

        })
        return result

    }
}