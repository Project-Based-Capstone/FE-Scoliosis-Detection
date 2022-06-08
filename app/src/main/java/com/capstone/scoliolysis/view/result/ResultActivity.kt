package com.capstone.scoliolysis.view.result

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.capstone.scoliolysis.R
import com.capstone.scoliolysis.databinding.ActivityResultBinding
import com.capstone.scoliolysis.model.DataItem
import com.capstone.scoliolysis.utils.UserPreference
import com.capstone.scoliolysis.view.ViewModelFactory
import com.capstone.scoliolysis.view.welcome.WelcomeActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var resultViewModel: ResultViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private var token: String? = null
    private var userID: Int? = null

    companion object {
        const val EXTRA_USER = "userDetail"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        resultViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ResultViewModel::class.java]

        resultViewModel.userDetail.observe(this) { user ->
            with(binding) {
                Glide.with(this@ResultActivity)
                    .load(user.image)
                    .circleCrop()
                    .into(imageViewData)
                dateTextView.text = user.createdAt
                nameTextView.text = user.name
                ageTextView.text = user.dateOfBirth.toString() + " years old"
                resultTextView.text = user.detection
                description.text = user.description
            }
        }

        resultViewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

        val userIntent = intent.getParcelableExtra<DataItem>(EXTRA_USER)
        if (userIntent != null) {
            userIntent.id?.let {
                userID = it
            }
        }

        resultViewModel.getUser().observe(this) {
            if (it.isLogin) {
                token = it.accessToken
                userID?.let { it1 -> resultViewModel.getDetailUser(token!!, it1) }
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }
    }

    private fun deleteData() {
        token?.let { userID?.let { it1 -> resultViewModel.deleteEntry(it, it1) } }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
                deleteData()
                true
            }
            else -> true
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