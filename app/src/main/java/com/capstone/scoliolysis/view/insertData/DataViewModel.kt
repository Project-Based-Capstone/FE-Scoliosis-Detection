package com.capstone.scoliolysis.view.insertData

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.scoliolysis.model.AddDataResponse
import com.capstone.scoliolysis.model.DataResponse
import com.capstone.scoliolysis.model.User
import com.capstone.scoliolysis.services.ApiConfig
import com.capstone.scoliolysis.utils.UserPreference
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Multipart

class DataViewModel(private val pref: UserPreference) : ViewModel() {

    private val _response = MutableLiveData<AddDataResponse>()
    val response: LiveData<AddDataResponse> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun addResult(
        token: String,
        name: RequestBody,
        dateOfBirth: RequestBody?,
        imageMultipart: MultipartBody.Part
    ) {
        _isLoading.value = true
        val client = ApiConfig.getApiService()
            .addData("Bearer $token", name, dateOfBirth, imageMultipart)
        client.enqueue(object : Callback<AddDataResponse> {
            override fun onResponse(
                call: Call<AddDataResponse>,
                response: Response<AddDataResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _response.value = response.body()
                } else {
                    _isLoading.value = false
//                    val jsonObject = JSONObject(response.errorBody()!!.charStream().readText())
//                    _response.value = response.body()
                    Log.e("DataActivityss", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<AddDataResponse>, t: Throwable) {
                Log.e("DataActivity", "onFailure: ${t.message.toString()}")
            }
        })
    }

}