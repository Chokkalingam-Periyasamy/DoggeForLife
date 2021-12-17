package com.example.doggeforlife

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @Headers("Content-Type: application/json")
    @POST("login")
    fun postData(@Body users:UserData): Call<Login>

}

interface PetsApiService {

    @GET("pets")
    suspend fun GetPets(@Header("Authorization") token: String): Response<AllPets>
}
interface RegisterApiInterface {
    @Headers("Content-Type: application/json")
    @POST("register")
    fun postRegData(@Body reg:UserData): Call<Login>
}
interface ChangeEmailApiService{
    @Headers("Content-Type: application/json")
    @POST("/doggeforlife-mobile/users/me/email")
    fun ChangeEmail(@Header("Authorization") token: String, @Body user:EmailUpdate ):Call<Void>

}
interface  DeleteEmail{

    @DELETE("users/me")
    fun deleteUser(@Header("Authorization") token: String):Call<Void>
}
