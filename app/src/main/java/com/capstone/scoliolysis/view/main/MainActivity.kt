package com.capstone.scoliolysis.view.main

import android.content.Context
import android.content.Intent
import android.content.Intent.EXTRA_USER
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.scoliolysis.R
import com.capstone.scoliolysis.databinding.ActivityMainBinding
import com.capstone.scoliolysis.model.DataItem
import com.capstone.scoliolysis.utils.UserPreference
import com.capstone.scoliolysis.view.ViewModelFactory
import com.capstone.scoliolysis.view.result.ResultActivity
import com.capstone.scoliolysis.view.takeObject.PreviewActivity
import com.capstone.scoliolysis.view.takeObject.TakeObjectActivity
import com.capstone.scoliolysis.view.welcome.WelcomeActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private val data = ArrayList<DataItem>()
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        binding.fabAdd.setOnClickListener{
            val i = Intent(this, PreviewActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    private fun setupViewModel() {
        val pref = UserPreference.getInstance(dataStore)
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(pref, this)
        )[MainViewModel::class.java]

        mainViewModel.getUser().observe(this) {
            if (it.isLogin) {
                mainViewModel.loadData(it.accessToken)
                setRecyclerData(data)
            } else {
                startActivity(Intent(this, WelcomeActivity::class.java))
                finish()
            }
        }

    }

    private fun setRecyclerData(data: List<DataItem>) {
        binding.recyclerView.visibility = View.VISIBLE
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        val listData = ArrayList<DataItem>()
        for (list in data) {
            listData.clear()
            listData.addAll(data)
        }

        val adapter = MainAdapter(listData)
        binding.recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback {
            override fun onItemClicked(data: DataItem) {
                Intent(this@MainActivity, ResultActivity::class.java).apply {
                    putExtra(ResultActivity.EXTRA_USER, data)
                    startActivity(this)
                }
            }
        })
    }

    private fun showLoading(isLoading: Boolean, view: View) {
        if (isLoading) {
            view.visibility = View.VISIBLE
        } else {
            view.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu, menu)
        return true
    }

    private fun changeLayout(){
        if (binding.recyclerView.layoutManager == LinearLayoutManager(this)){
            binding.recyclerView.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.recyclerView.layoutManager = LinearLayoutManager(this)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_change_layout -> {
                changeLayout()
                true
            }
            R.id.menu_delete -> {
                true
            }
            R.id.menu_logout -> {
                mainViewModel.logOutUser()
                val i = Intent(this, WelcomeActivity::class.java)
                startActivity(i)
                finish()
                true
            }
            else -> true
        }
    }
}