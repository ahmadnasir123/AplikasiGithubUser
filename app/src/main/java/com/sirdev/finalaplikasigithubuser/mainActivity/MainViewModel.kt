package com.sirdev.finalaplikasigithubuser.mainActivity

import android.util.Log
import androidx.lifecycle.*
import com.sirdev.finalaplikasigithubuser.api.ApiConfig
import com.sirdev.finalaplikasigithubuser.model.User
import com.sirdev.finalaplikasigithubuser.model.UserModel
import com.sirdev.finalaplikasigithubuser.themeActivity.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback

class MainViewModel (private val pref: SettingPreferences) : ViewModel() {

    val listUsers = MutableLiveData<ArrayList<User>>()

    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }

    fun findUsers(query: String) {
        ApiConfig.apiInstance
            .getUser(query)
            .enqueue(object : retrofit2.Callback<UserModel> {
                override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                    if (response.isSuccessful) {
                        listUsers.postValue(response.body()?.items)

                        Log.e("debug", listUsers.value.toString())

                    }
                }

                override fun onFailure(call: Call<UserModel>, t: Throwable) {
                    Log.e("onFailure", t.message.toString())
                }

            })
    }

    fun getListUsers(): LiveData<ArrayList<User>> {
        return listUsers
    }




}