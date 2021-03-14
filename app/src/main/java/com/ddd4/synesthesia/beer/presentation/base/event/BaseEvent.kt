package com.ddd4.synesthesia.beer.presentation.base.event

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.ddd4.synesthesia.beer.presentation.base.entity.ActionEntity
import com.ddd4.synesthesia.beer.presentation.base.entity.ItemClickEntity

typealias LiveHandledData<T> = LiveData<SingleContentEvent<T>>
typealias MutableLiveHandledData<T> = MutableLiveData<SingleContentEvent<T>>

fun <T> MutableLiveHandledData<T>.setHandledValue(newValue: T) {
    value = SingleContentEvent(newValue)
}

fun <T> MutableLiveHandledData<T>.postHandledValue(newValue: T) {
    postValue(SingleContentEvent(newValue))
}

interface IBaseEvent {
    /** action event ( toast, dialog ... ) */
    val action: LiveHandledData<ActionEntity>

    /** 화면 요소 선택 이벤트 (= click, touch) */
    var select: LiveHandledData<ItemClickEntity>

    /** error */
    var throwable: LiveHandledData<Pair<Throwable, Boolean>>
}

@Suppress("PropertyName")
open class BaseEvent : IBaseEvent {
    val _action: MutableLiveHandledData<ActionEntity> by lazy { MutableLiveHandledData<ActionEntity>() }
    val _select: MutableLiveHandledData<ItemClickEntity> by lazy { MutableLiveHandledData<ItemClickEntity>() }
    val _throwable: MutableLiveHandledData<Pair<Throwable, Boolean>> by lazy { MutableLiveHandledData<Pair<Throwable, Boolean>>() }

    override val action: LiveHandledData<ActionEntity> get() = _action

    override var select: LiveHandledData<ItemClickEntity> = _select

    override var throwable: LiveHandledData<Pair<Throwable, Boolean>> = _throwable
}
