package com.venicio.gitfinder.data.network

import com.google.gson.Gson
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GitFinderClient {
    private val gson = Gson() .newBuilder().create()

    fun getInstanceRetrofit() : GitFinderService {
        val retrofit = Retrofit.Builder()
            .baseUrl(GitFinderService.GITHUB_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(OkHttpClient.Builder().build())
            .build()

        return retrofit.create(GitFinderService::class.java)
    }
}
