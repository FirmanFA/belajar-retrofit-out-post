package com.example.dummyapi.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    // BASE_URL merupakan URL default untuk mengkoneksikan aplikasi dengan endpoint pada API
    const val BASE_URL = "https://binar-gdd-cc8.herokuapp.com/api/v1/"

    private val logging: HttpLoggingInterceptor
        get() {
            val httpLoggingInterceptor = HttpLoggingInterceptor()
            return httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
//        .addInterceptor {
//            val request = it.request()
//            val queryBuild =  request.url
//                .newBuilder()
//                .addQueryParameter("api_key","5374e8eba1107b24236cc30d17d5aa11").build()
//            return@addInterceptor it.proceed(request.newBuilder().url(queryBuild).build())
//        }
        .build()


    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        retrofit.create(ApiService::class.java)
    }
}
