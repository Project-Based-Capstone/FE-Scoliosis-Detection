package com.capstone.scoliolysis.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class DataItem(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("detection")
    val detection: String? = null,

    @field:SerializedName("updated_at")
    val updatedAt: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("created_at")
    val createdAt: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("dateOfBirth")
    val dateOfBirth: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null
) : Parcelable
