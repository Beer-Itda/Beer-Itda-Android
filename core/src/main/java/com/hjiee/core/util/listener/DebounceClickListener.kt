package com.hjiee.core.util.listener

import android.os.SystemClock
import android.view.View
import androidx.databinding.BindingAdapter
import com.hjiee.core.ext.orZero

/**
 * Debounce Click Listener
 */
class DebounceClickListener(
    private val listener: (View?) -> Unit,
    private val interval: Long = DEFAULT_INTERVAL_TIME
) : View.OnClickListener {
    private var lastClickTime: Long = 0

    override fun onClick(v: View?) {
        performClick {
            listener(v)
        }
    }

    private fun performClick(callback: () -> Unit) {
        SystemClock.elapsedRealtime().let { time ->
            if ((time - lastClickTime) < interval) {
                return
            }
            lastClickTime = time
            callback()
        }
    }
}

private const val DEFAULT_INTERVAL_TIME = 500L

fun View.setOnDebounceClickListener(
    listener: View.OnClickListener
) {
    setOnClickListener(
        DebounceClickListener({
            listener.onClick(it)
        })
    )
}

fun View.setOnDebounceClickListener(
    listener: (View?) -> Unit,
    interval: Long = DEFAULT_INTERVAL_TIME
) {
    setOnClickListener(
        DebounceClickListener(
            listener,
            interval
        )
    )
}

@BindingAdapter(value = ["onDebouncedClick", "interval"], requireAll = false)
fun View.setOnDebouncedClick(listener: (() -> Unit)? = null, interval: Long? = null) {
    setOnDebounceClickListener(
        listener = { listener?.invoke() },
        interval = if (interval.orZero() > 0) interval.orZero() else DEFAULT_INTERVAL_TIME
    )
}