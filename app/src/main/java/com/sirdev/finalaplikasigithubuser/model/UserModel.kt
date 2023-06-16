package com.sirdev.finalaplikasigithubuser.model

import com.google.gson.annotations.SerializedName

data class  UserModel(
    @field:SerializedName("items")
    val items : ArrayList<User>


)