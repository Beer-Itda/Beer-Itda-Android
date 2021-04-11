package com.ddd4.synesthesia.beer.presentation.ui.mypage.nickname.viewmodel

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import com.ddd4.synesthesia.beer.presentation.base.BaseViewModel
import com.ddd4.synesthesia.beer.presentation.ui.mypage.nickname.view.NickNameActivity

class NickNameViewModel @ViewModelInject constructor(
    @Assisted private val savedState: SavedStateHandle
) : BaseViewModel() {

    val nickName = MutableLiveData<String>(savedState.get(NickNameActivity.KEY_NICKNAME))

    private val _isValid = MutableLiveData<Boolean>(false)
    val isValid: LiveData<Boolean> get() = _isValid

    fun setIsValid(isValid: Boolean) {
        _isValid.value = isValid
    }

    fun clickClear() {
        nickName.value = ""
    }

    override fun onCleared() {
        super.onCleared()
    }
}