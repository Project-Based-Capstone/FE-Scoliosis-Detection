package com.capstone.scoliolysis.view.register

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.capstone.scoliolysis.R
import com.capstone.scoliolysis.databinding.ActivityRegisterBinding
import com.capstone.scoliolysis.databinding.ActivityWelcomeBinding
import com.capstone.scoliolysis.utils.UserPreference
import com.capstone.scoliolysis.view.ViewModelFactory

class RegisterActivity : AppCompatActivity() {
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var registerViewModel: RegisterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        binding.registButton.setOnClickListener {
            registerUser()
        }
    }

    private fun setupViewModel() {
        registerViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[RegisterViewModel::class.java]
    }

    private fun registerUser() {
        val email = binding.emailEditText.text.toString()
        val password = binding.passwordEditText.text.toString()
        val confirmPassword = binding.confirmPasswordEditText.text.toString()

        registerViewModel.signUpUser(email, password)
        registerViewModel.isLoading.observe(this) {
//            showLoading(it, binding.viewLoading)
        }
        registerViewModel.response.observe(this) {
            if (it.error) {
                Toast.makeText(
                    this@RegisterActivity,
                    it.message,
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                AlertDialog.Builder(this).apply {
                    setTitle("Yeah!")
                    setMessage("Your account is ready, please login")
                    setPositiveButton("Next") { _, _ ->
                        finish()
                    }
                    create()
                    show()
                }
            }
        }
    }

}