package com.capstone.scoliolysis.view.result

import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.scoliolysis.R
import com.capstone.scoliolysis.model.*
import com.capstone.scoliolysis.services.ApiConfig.getApiService
import com.capstone.scoliolysis.utils.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel(private val pref: UserPreference) : ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _response = MutableLiveData<DeleteItemResponse>()
    val response: LiveData<DeleteItemResponse> = _response


    fun deleteEntry(token: String?, id: Int?) {
        _isLoading.value = true
        val client = getApiService().deleteEntry(token, id)
        client.enqueue(object : Callback<DeleteItemResponse> {
            override fun onResponse(call: Call<DeleteItemResponse>, response: Response<DeleteItemResponse>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _response.value =
                        DeleteItemResponse(
                            "Successfully deleted"
                        )
                } else {
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DeleteItemResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message}")
            }

        })
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

}