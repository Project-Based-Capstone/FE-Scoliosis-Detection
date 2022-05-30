package com.capstone.scoliolysis.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.capstone.scoliolysis.model.User
import com.capstone.scoliolysis.utils.UserPreference
import kotlinx.coroutines.launch

class MainViewModel(private val pref: UserPreference) : ViewModel() {
    fun getUser(): LiveData<User> {
        return pref.getUser().asLiveData()
    }

    fun logOutUser() {
        viewModelScope.launch {
            pref.logOutUser()
        }
    }
}