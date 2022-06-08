package com.capstone.scoliolysis.model

import com.google.gson.annotations.SerializedName

data class AddDataResponse(

	@field:SerializedName("data")
	val data: List<DataItem?>? = null,

	@field:SerializedName("message")
	val message: String
)

