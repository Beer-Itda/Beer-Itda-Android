package com.hjiee.core.observer

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.hjiee.core.manager.Change
import com.hjiee.core.manager.DataChangeManager
import com.hjiee.core.util.log.L
import javax.inject.Inject

class AromaChangeObserver @Inject constructor(
    lifecycleOwner: LifecycleOwner,
    private val callback: () -> Unit
) : DefaultLifecycleObserver {

    private var initStatus = toggleStatus
    private val toggleStatus
        get() = DataChangeManager.getStatus(Change.AROMA)

    init {
        lifecycleOwner.lifecycle.addObserver(this)
    }

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        // pause 될때의 값과 resume 될때의 값이 다르면 정보가 변경되었다고 판단
        if (initStatus != toggleStatus) {
            L.d(owner.javaClass.simpleName, AROMA_MESSAGE)
            callback()
        }
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        initStatus = toggleStatus
    }

    companion object {
        private const val AROMA_MESSAGE = "향 정보 변경"
    }
}

fun LifecycleOwner.observeChangeTheSelectedAroma(callback: () -> Unit) {
    AromaChangeObserver(
        lifecycleOwner = this,
        callback = callback
    )
}