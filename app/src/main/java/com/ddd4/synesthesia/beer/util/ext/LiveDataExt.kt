package com.ddd4.synesthesia.beer.util.ext

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.hjiee.core.event.LiveHandledData

/**
 * 이벤트 중복 발생을 방지하는 SingleContentEvent를 사용하는 이벤트를 구독할 때 사용 한다
 */
inline fun <T> LifecycleOwner.observeHandledEvent(
    data: LiveHandledData<T>,
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
    data: LiveHandledData<T>,
    crossinline observer: (T) -> Unit
) {
    data.observe(this, Observer {
        it?.peekContent()?.let { value ->
            observer(value)
        }
    })
}