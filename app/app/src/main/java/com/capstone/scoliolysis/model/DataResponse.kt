package com.capstone.scoliolysis.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataResponse(

	@field:SerializedName("data")
	val data: List<DataItem>? = null
) : Parcelable

