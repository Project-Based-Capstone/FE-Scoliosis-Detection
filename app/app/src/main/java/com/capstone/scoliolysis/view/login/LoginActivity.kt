package com.capstone.scoliolysis.view.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.scoliolysis.R
import com.capstone.scoliolysis.databinding.ActivityLoginBinding
import com.capstone.scoliolysis.utils.UserPreference
import com.capstone.scoliolysis.view.ViewModelFactory
import com.capstone.scoliolysis.view.main.MainActivity

class LoginActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivityLoginBinding
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        setupViewModel()
        binding.loginButton.setOnClickListener{
            loginUser()
        }
    }

    private fun setupViewModel(){
        loginViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[LoginViewModel::class.java]
    }

    private fun loginUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()

        loginViewModel.signInUser(email, password)
        loginViewModel.isLoading.observe(this){
            showLoading(it, binding.progressBar)
        }
        loginViewModel.response.observe(this) {
            if (it.error) {
                Toast.makeText(
                    this@LoginActivity,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Welcome!")
                    setMessage("Login is successful!")
                    setPositiveButton("Next") { _, _ ->
                        val intent = Intent(context, MainActivity::class.java)
                        intent.flags =
                            Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)
                        finish()
                    }
                    create()
                    show()
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }
}