package com.hjiee.presentation.base

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.hjiee.core.event.BaseEvent
import com.hjiee.core.event.IBaseEvent
import com.hjiee.core.event.SelectActionEventNotifier
import com.hjiee.core.event.entity.ActionEntity
import com.hjiee.core.event.entity.ItemClickEntity
import com.hjiee.core.event.setHandledValue
import com.hjiee.core.util.log.L
import com.hjiee.presentation.base.model.ErrorActionEntity
import com.hjiee.presentation.commom.entity.ThrowEntity
import com.hjiee.presentation.util.NetworkStatus
import kotlinx.coroutines.CoroutineExceptionHandler

abstract class BaseViewModel : ViewModel(), SelectActionEventNotifier {

    private val _throwable = MutableLiveData<ThrowEntity>()
    val throwable: LiveData<ThrowEntity> get() = _throwable

    private val _networkStatus = MutableLiveData(NetworkStatus.SUCCESS)
    val networkStatus: LiveData<NetworkStatus> get() = _networkStatus

    val errorHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        statusFailure()
        notifyActionEvent(ErrorActionEntity.ShowErrorMessage(throwable.message.orEmpty()))
        L.e(throwable)
    }

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

    protected fun throwMessage(message: String, isFinish: Boolean = false) {
        _event._throwable.setHandledValue(Pair(Throwable(message), isFinish))
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