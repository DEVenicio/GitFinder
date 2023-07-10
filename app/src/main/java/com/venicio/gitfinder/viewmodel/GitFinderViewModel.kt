package com.venicio.gitfinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venicio.gitfinder.data.model.User
import com.venicio.gitfinder.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GitFinderViewModel(private val rp: Repository) : ViewModel() {

    private val _usersData = MutableLiveData<Array<User>>()
    val usersData: LiveData<Array<User>> get() = _usersData

    private val _userDataFail = MutableLiveData<Boolean>()
    val userDataFail: LiveData<Boolean> get() = _userDataFail

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> get() = _progressBar


    init {
        getUsersData()
    }

    private fun getUsersData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val requestUsers = rp.getUsersData()
                requestUsers.enqueue(object : Callback<Array<User>> {
                    override fun onResponse(call: Call<Array<User>>, response: Response<Array<User>>) {
                        if (response.isSuccessful && response.code() == 200) {
                            _usersData.postValue(response.body())
                            _progressBar.value = true

                        } else {
                            //tratamento generico aqui
                            _progressBar.value = true
                            failUserReset()
                        }
                    }

                    override fun onFailure(call: Call<Array<User>>, t: Throwable) {
                        //tratamento generico aqui
                        failUserReset()
                    }
                })
            } catch (e: Exception) {
                // tratamento de exceção
            }
        }
            }


     fun failUserReset() {
        _userDataFail.postValue(true)
    }

}