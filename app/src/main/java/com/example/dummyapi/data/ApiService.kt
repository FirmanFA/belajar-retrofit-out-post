package com.example.dummyapi.data

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {

    @POST("auth/register")
    fun postRegister(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("auth/login")
    fun postLogin(@Body request: LoginRequest): Call<LoginResponse>

    @GET("auth/me")
    fun authMe(@Header("Authorization") token:String): Call<AuthResponse>

    @Multipart
    @PUT("users")
    fun putUser(@Header("Authorization") token: String,
                @Part("username") username:RequestBody,
                @Part("email") email: RequestBody,
                @Part("photo") photo: MultipartBody.Part)

}