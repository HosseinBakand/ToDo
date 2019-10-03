package i.part.app.course.todo.core.api

import i.part.app.course.todo.MyApplication
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    companion object {
        private var retrofit: Retrofit? = null


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor { chain ->
                val request: Request = chain
                    .request()
                    .newBuilder()
                    .addHeader("Authorization", "Bearer ${MyApplication.TOKEN}")
                    .build()
                chain.proceed(request)
            }
            .build()

        fun getRetrofit(): Retrofit? {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            if (retrofit == null) {
                retrofit = Builder()
                    .baseUrl("http://cadet.todo.partdp.ir/api/")
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }


}