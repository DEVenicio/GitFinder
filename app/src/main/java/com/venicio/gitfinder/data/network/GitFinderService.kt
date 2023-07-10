package com.venicio.gitfinder.data.network

import com.venicio.gitfinder.data.model.Repositorie
import com.venicio.gitfinder.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface GitFinderService {

    @Headers("Authorization:${TOKEN}")
    @GET("users")
    fun fetchUsersData() : Call<Array<User>>

    @Headers("Authorization:${TOKEN}")
    @GET("users/{login}")
    fun fetchSingleUserData(@Path("login") login : String) : Call<User>

    @Headers("Authorization:${TOKEN}")
    @GET("users/{login}/repos")
    fun fetchReposUser(@Path("login") login: String) : Call<Array<Repositorie>>

    companion object {
        const val GITHUB_BASE_URL = "https://api.github.com/"
        const val TOKEN = "Bearer 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"
    }
}