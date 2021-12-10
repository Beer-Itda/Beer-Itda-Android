package com.hjiee.core.util.log.timber

import com.hjiee.core.util.log.L
import timber.log.Timber

/**
 * Firebase Crashlytics 상에서 표시되는 Stack Trace에 Timber 및 L 관련 내용 제거
 */
class StackTraceRecorder : Exception {
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)

    override fun fillInStackTrace(): Throwable {
        super.fillInStackTrace()
        val iterator = stackTrace.iterator()
        val fillterd = arrayListOf<StackTraceElement>()

        while (iterator.hasNext()) {
            val element = iterator.next()
            if (isIgnoreElement(element)) {
                break
            }
        }

        var isPassed = false
        while (iterator.hasNext()) {
            val element = iterator.next()
            if (!isPassed && isIgnoreElement(element)) {
                continue
            }
            isPassed = true
            fillterd.add(element)
        }

        stackTrace = fillterd.toTypedArray()
        return this
    }

    private fun isIgnoreElement(element: StackTraceElement): Boolean {
        return when (element.className) {
            Timber::class.java.name,
            L::class.java.name -> {
                true
            }
            else -> {
                false
            }
        }
    }
}