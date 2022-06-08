package com.capstone.scoliolysis.view.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.capstone.scoliolysis.model.RegisterResponse
import com.capstone.scoliolysis.services.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel(){
    private val _response = MutableLiveData<RegisterResponse>()
    val response: LiveData<RegisterResponse> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun signUpUser(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().register(email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _response.value = response.body()
                } else {
                    _isLoading.value = false
                    _response.value =
                        response.body()?.let {
                            RegisterResponse(
                                it.error,
                                it.message
                            )
                        }
                    Log.e("RegisterViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("RegisterViewModel", "onFailure: ${t.message}")
            }
        })
    }
}