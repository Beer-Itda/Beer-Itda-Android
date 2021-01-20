package com.ddd4.synesthesia.beer.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import com.ddd4.synesthesia.beer.presentation.base.event.SingleContentEvent

/**
 * 이벤트 중복 발생을 방지하는 SingleContentEvent를 사용하는 이벤트를 구독할 때 사용 한다
 */
inline fun <T> LifecycleOwner.observeHandledEvent(
    data: LiveData<SingleContentEvent<T>>,
    crossinline observer: (T) -> Unit
) {
    data.observe(this, Observer {
        it?.getContentIfNotHandled()?.let { value ->
            observer(value)
        }
    })
}

/**
 * SingleContentEvent 핸들링여부에 상관없이 무조건 data를 수신받고 싶을 때 사용 한다
 */
inline fun <T> LifecycleOwner.observeUnHandledEvent(
    data: LiveData<SingleContentEvent<T>>,
    crossinline observer: (T) -> Unit
) {
    data.observe(this, Observer {
        it?.peekContent()?.let { value ->
            observer(value)
        }
    })
}