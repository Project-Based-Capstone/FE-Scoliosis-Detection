package com.capstone.scoliolysis.view.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.capstone.scoliolysis.model.LoginResponse
import com.capstone.scoliolysis.services.ApiConfig
import com.capstone.scoliolysis.utils.UserPreference
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val pref: UserPreference): ViewModel() {
    private val _response = MutableLiveData<LoginResponse>()
    val response: LiveData<LoginResponse> = _response

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun signInUser(email: String, password: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().login(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if (response.isSuccessful) {
                    _isLoading.value = false
                    _response.value = response.body()
                    viewModelScope.launch {
                        response.body()?.user?.let {
                           pref.setUser(it)
                        }
                    }
                } else {
                    _isLoading.value = false
                    _response.value =
                        LoginResponse(
                            response.body()!!.error,
                            response.body()!!.message,
                            null
                        )
                    Log.e("LoginViewModel", "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e("LoginViewModel", "onFailure: ${t.message}")
            }
        })
    }


}