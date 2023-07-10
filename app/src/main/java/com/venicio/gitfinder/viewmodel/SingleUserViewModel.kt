package com.venicio.gitfinder.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.venicio.gitfinder.data.model.Repositorie
import com.venicio.gitfinder.data.model.User
import com.venicio.gitfinder.data.repository.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SingleUserViewModel(private val rp: Repository) : ViewModel() {

    private val _singleUserData = MutableLiveData<User>()
    val singleUserData: LiveData<User> get() = _singleUserData

    private val _dataRepo = MutableLiveData<Array<Repositorie>>()
    val dataRepo: LiveData<Array<Repositorie>> get() = _dataRepo

    private val _notFound = MutableLiveData<Boolean>()
    val notFound: LiveData<Boolean> get() = _notFound

    private val _userDataFail = MutableLiveData<Boolean>()
    val userDataFail: LiveData<Boolean> get() = _userDataFail

    private val _progressBar = MutableLiveData<Boolean>()
    val progressBar: LiveData<Boolean> get() = _progressBar


    fun getSingleUserData(login: String?) {
        viewModelScope.launch(Dispatchers.IO) {

            try {
                val requestSingleUser = login?.let { rp.getSingleUserData(it) }

                requestSingleUser?.enqueue(object : Callback<User> {
                    override fun onResponse(call: Call<User>, response: Response<User>) {
                        if (response.isSuccessful && response.code() == 200) {
                            _singleUserData.postValue(response.body())
                            requestRepos(login)

                        } else {
                            if (response.code() == 404) {
                                _notFound.value = true
                            }
                        }
                    }

                    override fun onFailure(call: Call<User>, t: Throwable) {
                        //tratamento generico aqui
                        _userDataFail.value = true
                    }
                })
            } catch (e: Exception) {
                //tratar exececao
            }

        }
    }

    private fun requestRepos(login: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val requestRepo = rp.getReposData(login)
            requestRepo.enqueue(object : Callback<Array<Repositorie>> {
                override fun onResponse(
                    call: Call<Array<Repositorie>>,
                    response: Response<Array<Repositorie>>
                ) {
                    if (response.isSuccessful) {
                        _dataRepo.postValue(response.body())
                        _progressBar.value = true

                    }
                }

                override fun onFailure(call: Call<Array<Repositorie>>, t: Throwable) {
                    //tratamento generico aqui
                    _userDataFail.value = true
                }
            })
        }
    }

    fun notFoundReset() {
        _notFound.postValue(false)
    }

    fun failSingleUserDataReset() {
        _userDataFail.postValue(false)
    }
}