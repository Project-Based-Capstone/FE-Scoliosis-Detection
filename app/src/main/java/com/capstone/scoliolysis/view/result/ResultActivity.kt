package com.capstone.scoliolysis.view.result

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.annotation.StringRes
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
               /** dateofbirthTextView.text = user.dateOfBirth **/
                resultTextView.text = user.detection
                description.text = user.description
            }
        }
        /** detailViewModel.isLoading.observe(this) {
            showLoading(it, binding.)
        } **/

        val userIntent = intent.getParcelableExtra<DataItem>(EXTRA_USER)
        if (userIntent != null) {
            userIntent.id?.let { resultViewModel.getDetailUser(it) }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_delete -> {
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