package com.ddd4.synesthesia.beer.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.base.event.BaseEvent
import com.ddd4.synesthesia.beer.presentation.base.event.IBaseEvent
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.base.event.setHandledValue
import com.ddd4.synesthesia.beer.presentation.commom.entity.ThrowEntity
import com.ddd4.synesthesia.beer.util.NetworkStatus

abstract class BaseViewModel : ViewModel(), SelectActionEventNotifier {

    private val _throwable = MutableLiveData<ThrowEntity>()
    val throwable : LiveData<ThrowEntity> get() = _throwable

    private val _networkStatus = MutableLiveData<NetworkStatus>()
    val networkStatus : LiveData<NetworkStatus> get() = _networkStatus

    protected open val _event: BaseEvent = BaseEvent()
    open val event: IBaseEvent
        get() = _event


    protected open fun handleActionEvent(entity: ActionEntity) {}
    override fun notifyActionEvent(entity: ActionEntity) {
        handleActionEvent(entity)
        _event._action.setHandledValue(entity)
    }

    protected open fun handleSelectEvent(entity: ItemClickEntity) {}
    override fun notifySelectEvent(entity: ItemClickEntity) {
        handleSelectEvent(entity)
        _event._select.setHandledValue(entity)
    }

    protected fun throwMessage(message: String, isFinish : Boolean) {
        _throwable.value = ThrowEntity(message,isFinish)
    }

    protected fun statusLoading() {
        _networkStatus.value = NetworkStatus.LOADING
    }

    protected fun statusSuccess() {
        _networkStatus.value = NetworkStatus.SUCCESS
    }

    protected fun statusFailure() {
        _networkStatus.value = NetworkStatus.FAILURE
    }

    override fun onCleared() {
        super.onCleared()
    }
}