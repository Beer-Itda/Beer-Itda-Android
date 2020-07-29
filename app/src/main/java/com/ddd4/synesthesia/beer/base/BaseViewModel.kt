package com.ddd4.synesthesia.beer.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddd4.synesthesia.beer.data.utils.NetworkStatus

abstract class BaseViewModel : ViewModel() {

    val loading = MutableLiveData<Boolean>()
    val loadingStatus = MutableLiveData<NetworkStatus>()

    init {
        loading.value = (false)
    }

    override fun onCleared() {
        super.onCleared()
        hideLoading()
    }

    fun showLoading() {
        loading.value = (true)
    }

    fun hideLoading() {
        loading.value = (false)
    }
}