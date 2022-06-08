package com.capstone.scoliolysis.view.result

import android.util.Log
import android.view.MenuItem
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.capstone.scoliolysis.R
import com.capstone.scoliolysis.model.DataItem
import com.capstone.scoliolysis.model.DataResponse
import com.capstone.scoliolysis.model.User
import com.capstone.scoliolysis.services.ApiConfig.getApiService
import com.capstone.scoliolysis.utils.UserPreference
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResultViewModel(private val pref: UserPreference) : ViewModel() {

    private val _userDetail = MutableLiveData<DataItem>()
    val userDetail: LiveData<DataItem> = _userDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    internal fun getDetailUser(token: String, id: Int) {
        _isLoading.value = true
        val client = getApiService().loadDetail(token, id)
        client.enqueue(object : Callback<DataItem> {
            override fun onResponse(call: Call<DataItem>, response: Response<DataItem>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DataItem>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message}")
            }

        })
    }

    fun deleteEntry(token: String, id: Int) {
        _isLoading.value = true
        val client = getApiService().loadDetail(token, id)
        client.enqueue(object : Callback<DataItem> {
            override fun onResponse(call: Call<DataItem>, response: Response<DataItem>) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _userDetail.value = response.body()
                } else {
                    Log.e("DetailViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DataItem>, t: Throwable) {
                _isLoading.value = false
                Log.e("DetailViewModel", "onFailure: ${t.message}")
            }

        })
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

}