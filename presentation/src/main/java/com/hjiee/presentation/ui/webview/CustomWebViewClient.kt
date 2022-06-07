package com.hjiee.presentation.ui.webview

import android.graphics.Bitmap
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.hjiee.presentation.ui.webview.model.WebViewActionEntity
import com.hjiee.core.event.ActionEventNotifier

class CustomWebViewClient(
    private val eventNotifier: ActionEventNotifier
) : WebViewClient() {

    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        return super.shouldOverrideUrlLoading(view, request)
    }

    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
        super.onPageStarted(view, url, favicon)
    }




    override fun onPageFinished(view: WebView?, url: String?) {
        eventNotifier.notifyActionEvent(WebViewActionEntity.PageLoadFinished)
        super.onPageFinished(view, url)
    }
}