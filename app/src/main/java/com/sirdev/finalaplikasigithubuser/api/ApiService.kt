package com.sirdev.finalaplikasigithubuser.api

import com.sirdev.finalaplikasigithubuser.model.DetailuserModel
import com.sirdev.finalaplikasigithubuser.model.User
import com.sirdev.finalaplikasigithubuser.model.UserModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: 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")
    fun getUser(
        @Query("q") query: String
    ): Call<UserModel>


    @GET("users/{username}")
    @Headers("Authorization: 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")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailuserModel>


    @GET("users/{username}/followers")
    @Headers("Authorization: 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")
    fun getUserFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>


    @GET("users/{username}/following")
    @Headers("Authorization: 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")
    fun getUserFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}