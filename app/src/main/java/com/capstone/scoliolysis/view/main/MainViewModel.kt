package com.capstone.scoliolysis.view.main

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.capstone.scoliolysis.model.DataItem
import com.capstone.scoliolysis.model.DataResponse
import com.capstone.scoliolysis.model.User
import com.capstone.scoliolysis.services.ApiConfig
import com.capstone.scoliolysis.utils.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: UserPreference) : ViewModel() {

    var isLinear: Boolean = true

    private val _listData = MutableLiveData<List<DataItem>>()
    val listData: LiveData<List<DataItem>> = _listData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun loadData(token: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().loadAll("Bearer $token")
        client.enqueue(object : Callback<DataResponse> {
            override fun onResponse(
                call: Call<DataResponse>,
                response: Response<DataResponse>
            ) {
                if(response.isSuccessful){
                    _listData.value = response.body()?.data!!
                } else {
                    Log.e("Error", "message: Error")
                }
            }
            override fun onFailure(call: Call<DataResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("MainViewModel", "onFailure: ${t.message.toString()}")
            }

        }
        )
    }

    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }


    fun logOutUser() {
        viewModelScope.launch {
            pref.logOutUser()
        }
    }
}