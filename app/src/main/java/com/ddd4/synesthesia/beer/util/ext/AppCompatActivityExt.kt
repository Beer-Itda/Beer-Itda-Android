package com.ddd4.synesthesia.beer.util.ext

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import com.ddd4.synesthesia.beer.R
import com.hjiee.core.Consts.DEF_REQUEST_PERMISSION_CODE
import com.hjiee.core.Consts.IS_ANIMATE_TRANSITION
import com.hjiee.core.util.log.L

inline fun <reified T> AppCompatActivity.start(
    intent: Intent = Intent(this, T::class.java),
    activityLauncher: ActivityResultLauncher<Intent>? = null,
    isAnimation: Boolean = IS_ANIMATE_TRANSITION
) {
    runCatching {
        activityLauncher?.launch(intent) ?: startActivity(intent)
    }.onSuccess {
        if (isAnimation) {
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
    }.onFailure {
        L.e(it)
    }
}

fun AppCompatActivity.permissonsCheck(
    neededPermissions: Array<String>,
    granted: (() -> Unit)? = null
) {
    applicationContext.permissonsCheck(
        neededPermissions = neededPermissions,
        granted = {
            granted?.invoke()
        },
        denied = {
            requestPermissions(neededPermissions, DEF_REQUEST_PERMISSION_CODE)
        }
    )


}
