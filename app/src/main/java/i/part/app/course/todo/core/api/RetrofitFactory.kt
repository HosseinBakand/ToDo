package i.part.app.course.todo.core.api

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {
    companion object {
        private var retrofit: Retrofit? = null
        fun getRetrofit(): Retrofit? {
            if (retrofit == null) {
                val intercepter = HttpLoggingInterceptor()
                intercepter.level = HttpLoggingInterceptor.Level.BODY
                val okHttpClient = OkHttpClient.Builder()
                    .addInterceptor { chain ->
                        val request: Request = chain
                            .request()
                            .newBuilder()
//                            .addHeader("Authorization", "Bearer ${MainActivity.tokenLiveData.value}")
                            .build()
                        chain.proceed(request)
                    }
                    .addInterceptor(intercepter)
                    .build()
                retrofit = Builder()
                    .client(okHttpClient)
                    .baseUrl("http://cadet.todo.partdp.ir/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }


}