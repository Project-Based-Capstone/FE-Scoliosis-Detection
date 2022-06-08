package com.capstone.scoliolysis.services

import com.capstone.scoliolysis.model.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiServices {
    @FormUrlEncoded
    @POST("auth/register")
    fun register(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("auth/login")
    fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("records")
    fun loadAll(
        @Header("Authorization") token: String
    ) : Call<DataResponse>


    @GET("records/{id}")
    fun loadDetail(
        @Path("id") id: Int
    ) : Call<DataItem>

    @Multipart
    @POST("record/")
    fun addData(
        @Header("Authorization") token: String,
        @Part("name") name: RequestBody,
        @Part("dateOfBirth") dateOfBirth: RequestBody?,
        @Part file: MultipartBody.Part
    ): Call<AddDataResponse>
}