package com.sirdev.finalaplikasigithubuser.mainActivity

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.switchmaterial.SwitchMaterial
import com.sirdev.finalaplikasigithubuser.FavoriteActivity.FavoriteActivity
import com.sirdev.finalaplikasigithubuser.R
import com.sirdev.finalaplikasigithubuser.databinding.ActivityMainBinding
import com.sirdev.finalaplikasigithubuser.detailActivity.DetailUserActivity
import com.sirdev.finalaplikasigithubuser.model.User
import com.sirdev.finalaplikasigithubuser.themeActivity.SettingPreferences
import com.sirdev.finalaplikasigithubuser.themeActivity.ThemeActivity
import com.sirdev.finalaplikasigithubuser.themeActivity.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel
    private lateinit var adapter: UserAdapter


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val switchTheme = findViewById<SwitchMaterial>(R.id.switch_theme)

        val pref = SettingPreferences.getInstance(dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref)).get(MainViewModel::class.java)
        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                switchTheme?.let {
                    it.setChecked(true)
                } ?: run {
                    // handle null object reference here
                }
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                switchTheme?.let {
                    it.setChecked(false)
                } ?: run {
                    // handle null object reference here
                }
            }
        }


        val actionbar = supportActionBar
        actionbar?.title = "Search User"



        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {

            override fun onItemClicked(user: User) {
                val intent = Intent(this@MainActivity, DetailUserActivity::class.java)
                with(intent) {
                    putExtra(DetailUserActivity.EXTRA_USERNAME, user.login)
                    putExtra(DetailUserActivity.EXTRA_ID, user.id)
                    putExtra(DetailUserActivity.EXTRA_URL, user.avatar_url)
                    putExtra(DetailUserActivity.EXTRA_URL2, user.url)
                }
                startActivity(intent)
            }

        })

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        binding.apply {

            rvUsers.layoutManager = LinearLayoutManager(this@MainActivity)
            rvUsers.setHasFixedSize(true)
            rvUsers.adapter = adapter

            imgBtnSearch.setOnClickListener {

                searchUser()
            }

            etQuery.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
                if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                    searchUser()

                    val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
                    imm?.hideSoftInputFromWindow(v?.windowToken, 0)

                    return@OnKeyListener true
                }
                false
            })
        }
        viewModel.getListUsers().observe(this) {
            if (it != null) {
                adapter.setList(it)
                showLoading(false)
            }
        }


    }

    private fun searchUser() {
        binding.apply {
            val searchText = etQuery.text.toString()
            if (searchText.isEmpty()) return
            showLoading(true)
            viewModel.findUsers(searchText)
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.menu_favorite -> {
                Intent(this, FavoriteActivity::class.java).also {
                    startActivity(it)
                }
            }

            R.id.menu_theme -> {
                val intent = Intent(this, ThemeActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }


}