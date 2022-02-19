package com.ddd4.synesthesia.beer.presentation.ui.webview

import android.webkit.WebChromeClient
import android.webkit.WebView
import com.ddd4.synesthesia.beer.presentation.ui.webview.model.WebViewActionEntity
import com.hjiee.core.event.ActionEventNotifier

class CustomWebChromeClient(
    private val eventNotifier: ActionEventNotifier
) : WebChromeClient() {

    override fun onProgressChanged(view: WebView?, newProgress: Int) {
        super.onProgressChanged(view, newProgress)
        eventNotifier.notifyActionEvent(WebViewActionEntity.ProgressChanged(newProgress))
    }
}