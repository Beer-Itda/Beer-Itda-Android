package com.ddd4.synesthesia.beer.util

import android.content.Intent
import android.text.Selection
import android.text.Spannable
import android.text.method.MovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.view.KeyEvent
import android.view.MotionEvent
import android.widget.TextView
import com.ddd4.synesthesia.beer.presentation.ui.webview.view.WebViewActivity
import com.ddd4.synesthesia.beer.presentation.ui.webview.view.WebViewActivity.Companion.WEBVIEW_URL
import com.hjiee.core.ext.orZero


class HyperLinkMovement : MovementMethod {
    override fun initialize(p0: TextView?, p1: Spannable?) {
    }

    override fun onKeyDown(p0: TextView?, p1: Spannable?, p2: Int, p3: KeyEvent?): Boolean {
        return false
    }

    override fun onKeyUp(p0: TextView?, p1: Spannable?, p2: Int, p3: KeyEvent?): Boolean {
        return false
    }

    override fun onKeyOther(p0: TextView?, p1: Spannable?, p2: KeyEvent?): Boolean {
        return false
    }

    override fun onTakeFocus(p0: TextView?, p1: Spannable?, p2: Int) {
    }

    override fun onTrackballEvent(p0: TextView?, p1: Spannable?, p2: MotionEvent?): Boolean {
        return false
    }

    override fun onTouchEvent(widget: TextView?, buffer: Spannable?, event: MotionEvent?): Boolean {
        val action = event?.action

        if (action == MotionEvent.ACTION_UP || action == MotionEvent.ACTION_DOWN) {
            var x = event.x
            var y = event.y
            x -= widget?.totalPaddingLeft.orZero()
            y -= widget?.totalPaddingTop.orZero()
            x += widget?.scrollX.orZero()
            y += widget?.scrollY.orZero()
            val layout = widget?.layout
            val line = layout?.getLineForVertical(y.orZero().toInt())
            val off = layout?.getOffsetForHorizontal(line.orZero(), x)
            val link = buffer?.getSpans(off.orZero(), off.orZero(), ClickableSpan::class.java)


            if (link?.size != 0) {
                if (action == MotionEvent.ACTION_UP) {
                    // do whatever else you want here on link being clicked
                    widget?.context?.run {
                        (link?.get(0) as? URLSpan)?.url?.let { url ->
                            startActivity(
                                WebViewActivity.getIntent(
                                    context = this,
                                    url = url
                                )
                            )
                        }
                    }
                }
                Selection.removeSelection(buffer)
            } else if (action == MotionEvent.ACTION_DOWN) {
                Selection.setSelection(
                    buffer,
                    buffer?.getSpanStart(link?.get(0)).orZero(),
                    buffer?.getSpanEnd(link?.get(0)).orZero()
                )
            }
            return true
        } else {
            Selection.removeSelection(buffer)
        }
        return false
    }


    override fun onGenericMotionEvent(p0: TextView?, p1: Spannable?, p2: MotionEvent?): Boolean {
        return false
    }

    override fun canSelectArbitrarily(): Boolean {
        return false
    }
}