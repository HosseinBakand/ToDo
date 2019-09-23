package i.part.app.course.todo.core.api

import retrofit2.Retrofit
import retrofit2.Retrofit.Builder
import retrofit2.converter.gson.GsonConverterFactory

class retrofitFactory {
    companion object {
        private var retrofit: Retrofit? = null

        fun getRetrofit(): Retrofit? {
            if (retrofit == null) {
                retrofit = Builder()
                    .baseUrl("http://cadet.todo.partdp.ir/api/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }


}