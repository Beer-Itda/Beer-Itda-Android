package com.hjiee.presentation.ui.webview.model

import com.hjiee.core.event.entity.ActionEntity

sealed class WebViewActionEntity : ActionEntity() {
    class ProgressChanged(val progress: Int) : WebViewActionEntity()
    object PageLoadFinished : WebViewActionEntity()
}