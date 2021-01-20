package com.ddd4.synesthesia.beer.presentation.base

import androidx.lifecycle.ViewModel
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity
import com.ddd4.synesthesia.beer.presentation.base.event.BaseEvent
import com.ddd4.synesthesia.beer.presentation.base.event.IBaseEvent
import com.ddd4.synesthesia.beer.presentation.base.event.SelectActionEventNotifier
import com.ddd4.synesthesia.beer.presentation.base.event.setHandledValue

abstract class BaseViewModel : ViewModel(), SelectActionEventNotifier {

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

    override fun onCleared() {
        super.onCleared()
    }
}