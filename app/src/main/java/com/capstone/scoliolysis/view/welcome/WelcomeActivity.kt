package com.capstone.scoliolysis.view.welcome

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.capstone.scoliolysis.R
import com.capstone.scoliolysis.databinding.ActivityWelcomeBinding
import com.capstone.scoliolysis.view.login.LoginActivity
import com.capstone.scoliolysis.view.register.RegisterActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWelcomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAction()
    }

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        binding.registButton.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}