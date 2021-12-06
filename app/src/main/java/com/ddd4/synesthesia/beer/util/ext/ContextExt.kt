package com.ddd4.synesthesia.beer.util.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.ddd4.synesthesia.beer.R
import com.hjiee.core.Consts.DEFAULT_REQ_CODE
import com.hjiee.core.ext.orFalse
import com.hjiee.core.ext.orZero

fun Context.versionName(): String =
    packageManager.getPackageInfo(packageName, 0).versionName


fun Context.showKeyboard(view: View) {
    view.run {
        when (view) {
            is EditText -> {
                isFocusable = true
                isFocusableInTouchMode = true
                view.requestFocus()
                (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.showSoftInput(
                    this,
                    InputMethodManager.SHOW_IMPLICIT
                )
                view.setSelection(view.length())

            }
        }
    }
}

fun Context.hideKeyboard(view: View) {
    (getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager)?.hideSoftInputFromWindow(
        view.windowToken,
        0
    )
}

fun Context.permissonsCheck(
    neededPermissions: Array<String>,
    granted: (() -> Unit)? = null,
    denied: (() -> Unit)? = null
) {
    for (permission in neededPermissions) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            denied?.invoke()
            return
        }
    }
    granted?.invoke()
}

fun Context.showToast(message: String, duration: Int? = null) {
    Toast.makeText(this, message, duration ?: Toast.LENGTH_SHORT).show()
}

fun Context.showSimpleDialog(
    title: String? = getString(R.string.app_name),
    message: String,
    result: (() -> Unit)? = null
) {
    AlertDialog.Builder(this, R.style.Dialog).apply {
        setTitle(title)
        setMessage("$message")
        setPositiveButton(getString(R.string.ok)) { _, _ ->
            result?.invoke()
        }
        setNegativeButton(getString(R.string.cancel)) { _, _ ->
        }
    }.show()
}

fun Context.showNoticeDialog(
    title: String? = null,
    message: String,
    result: (() -> Unit)? = null
) {
    AlertDialog.Builder(this, R.style.Dialog).apply {
        setTitle(title)
        setMessage("$message")
        setPositiveButton(getString(R.string.ok)) { _, _ ->
            result?.invoke()
        }
    }.show()
}

fun Context.start(
    intent: Intent,
    requestCode: Int? = DEFAULT_REQ_CODE,
    isFinish: Boolean? = false,
    isAnimation: Boolean? = true
) {


    when (this) {
        is Activity -> {
            startActivityForResult(intent, requestCode.orZero())
            if (isAnimation.orFalse()) {
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
            if (isFinish.orFalse()) {
                finish()
            }
        }
        else -> {
            startActivity(intent)
        }
    }
}