package com.ddd4.synesthesia.beer.util.ext

import android.content.Intent
import androidx.fragment.app.Fragment
import com.ddd4.synesthesia.beer.R
import com.hjiee.core.Consts.DEF_REQUEST_PERMISSION_CODE
import com.hjiee.core.Consts.IS_ANIMATE_TRANSITION
import com.hjiee.core.util.log.L

inline fun <reified T> Fragment.start(
    intent: Intent? = null,
    isAnimation: Boolean = IS_ANIMATE_TRANSITION
) {
    runCatching {
        intent?.let {
            context?.startActivity(intent)
        } ?: context?.startActivity(Intent(context, T::class.java))
    }.onSuccess {
        if (isAnimation) {
            activity?.overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }.onFailure {
        L.e(it)
    }
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