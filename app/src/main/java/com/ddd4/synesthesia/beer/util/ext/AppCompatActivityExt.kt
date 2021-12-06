package com.ddd4.synesthesia.beer.util.ext

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.hjiee.core.Consts.DEF_REQUEST_PERMISSION_CODE
import com.hjiee.core.R

inline fun <reified T> AppCompatActivity.start(
    isFinish: Boolean? = false,
    bundle: Bundle? = null,
    isAnimation: Boolean? = false
) {
    Intent(this, T::class.java).apply {
        bundle?.let {
            putExtras(bundle)
        }
        startActivity(this)
        if (isAnimation == true) {
//            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }
        if (isFinish == true) {
            finish()
        }
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
