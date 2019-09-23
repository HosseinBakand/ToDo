package i.part.app.course.todo.features.user.data

import androidx.lifecycle.MutableLiveData
import i.part.app.course.todo.core.api.Result
import i.part.app.course.todo.core.api.retrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class UserRepository {
    val retrofit = retrofitFactory.getRetrofit()
    val userServices = retrofit?.create(UserServices::class.java)


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
}