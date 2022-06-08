package com.capstone.scoliolysis.model

import com.google.gson.annotations.SerializedName


data class LoginResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("user")
	val user: User?

)


