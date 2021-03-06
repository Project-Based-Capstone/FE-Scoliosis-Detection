package com.capstone.scoliolysis.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.scoliolysis.utils.UserPreference
import com.capstone.scoliolysis.view.insertData.DataViewModel
import com.capstone.scoliolysis.view.login.LoginViewModel
import com.capstone.scoliolysis.view.main.MainViewModel
import com.capstone.scoliolysis.view.register.RegisterViewModel
import com.capstone.scoliolysis.view.result.ResultViewModel

class ViewModelFactory(private val pref: UserPreference, private val context: Context? = null) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(MainViewModel::class.java) -> {
                MainViewModel(pref) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(pref) as T
            }
            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel() as T
            }
            modelClass.isAssignableFrom(DataViewModel::class.java) -> {
                DataViewModel(pref) as T
            }
            modelClass.isAssignableFrom(ResultViewModel::class.java) -> {
                ResultViewModel(pref) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }
}