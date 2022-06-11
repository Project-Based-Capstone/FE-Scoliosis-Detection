package com.capstone.scoliolysis.view.result

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
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
import com.capstone.scoliolysis.view.main.MainActivity
import com.capstone.scoliolysis.view.takeObject.PreviewActivity
import com.capstone.scoliolysis.view.welcome.WelcomeActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResultBinding
    private lateinit var resultViewModel: ResultViewModel
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
    private var token: String? = null
    private var userID: Int? = null

    companion object {
        const val EXTRA_DATA = "userData"
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val user = intent.getParcelableExtra<DataItem>(EXTRA_DATA)

        resultViewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[ResultViewModel::class.java]

        if(user!= null){
            with(binding) {
                Glide.with(this@ResultActivity)
                    .load(user.image)
                    .into(imageViewData)
                dateTextView.text = user.createdAt
                nameTextView.text = user.name
                ageTextView.text = user.dateOfBirth.toString() + " tahun"
                resultTextView.text = user.detection
                description.text = user.description
            }

            userID = user.id
        }
        resultViewModel.isLoading.observe(this) {
            showLoading(it, binding.progressBar)
        }

    }

    private fun deleteData() {
        resultViewModel.getUser().observe(this){
            token = it.accessToken
            resultViewModel.deleteEntry(token, userID)
        }
        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
        finish()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_detail, menu)
        return true
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