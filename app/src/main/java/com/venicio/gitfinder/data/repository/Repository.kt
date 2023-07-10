package com.venicio.gitfinder.data.repository

import com.venicio.gitfinder.data.network.GitFinderClient

class Repository {

    private val service = GitFinderClient.getInstanceRetrofit()

    fun getUsersData() = service.fetchUsersData()

    fun getSingleUserData(login: String) = service.fetchSingleUserData(login)

    fun getReposData(repoUser: String) = service.fetchReposUser(repoUser)
}