package com.hyden.ext

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.ddd4.synesthesia.beer.ext.permissonsCheck
import com.ddd4.synesthesia.beer.ext.start
import com.ddd4.synesthesia.beer.util.Consts.DEF_REQUEST_PERMISSION_CODE

fun Fragment.start(
    intent: Intent
) {
    context?.start(intent = intent)
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