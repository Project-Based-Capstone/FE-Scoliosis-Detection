package com.capstone.scoliolysis.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(

    @field:SerializedName("access_token")
    val accessToken: String,

    @field:SerializedName("refresh_token")
    val refreshToken: String,

    @field:SerializedName("user_id")
    val userId: String,

    @field:SerializedName("email")
    val email: String,

    @field:SerializedName("isLogin")
    val isLogin: Boolean
) : Parcelable