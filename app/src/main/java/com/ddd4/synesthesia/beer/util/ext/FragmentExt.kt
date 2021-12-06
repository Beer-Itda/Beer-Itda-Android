package com.ddd4.synesthesia.beer.util.ext

import android.content.Intent
import androidx.fragment.app.Fragment
import com.hjiee.core.Consts.DEFAULT_REQ_CODE
import com.hjiee.core.Consts.DEF_REQUEST_PERMISSION_CODE
import com.ddd4.synesthesia.beer.util.ext.permissonsCheck
import com.ddd4.synesthesia.beer.util.ext.start

fun Fragment.start(
    intent: Intent,
    requestCode: Int? = DEFAULT_REQ_CODE
) {
    context?.start(
        intent = intent,
        requestCode = requestCode
    )
}


fun Fragment.permissonsCheck(
    neededPermissions: Array<String>,
    granted: (() -> Unit)? = null
) {
    context?.permissonsCheck(
        neededPermissions = neededPermissions,
        granted = {
            granted?.invoke()
        },
        denied = {
            requestPermissions(neededPermissions, DEF_REQUEST_PERMISSION_CODE)
        }
    )
}