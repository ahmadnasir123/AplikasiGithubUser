package com.sirdev.finalaplikasigithubuser.FavoriteActivity

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.sirdev.finalaplikasigithubuser.database.FavoritDao
import com.sirdev.finalaplikasigithubuser.database.FavoriteUser
import com.sirdev.finalaplikasigithubuser.database.UserFavDatabase

class FavoriteViewModel(application: Application): AndroidViewModel(application) {

    private var userDao: FavoritDao?
    private var db : UserFavDatabase?

    init {
        db = UserFavDatabase.getInstance(application)
        userDao = db?.favoritDao()
    }

    fun getFavUser(): LiveData<List<FavoriteUser>>? {
        return userDao?.getAllFavUser()
    }


}