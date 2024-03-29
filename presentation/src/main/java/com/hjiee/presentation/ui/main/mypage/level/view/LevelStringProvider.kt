package com.hjiee.presentation.ui.main.mypage.level.view

import android.content.Context
import androidx.core.text.HtmlCompat
import androidx.core.text.buildSpannedString
import com.hjiee.presentation.R
import com.hjiee.core.provider.IStringResourceProvider
import com.hjiee.domain.entity.DomainEntity
import javax.inject.Inject

class LevelStringProvider @Inject constructor(
    val context: Context
) : IStringResourceProvider {


    fun getMyLevelMessage(myLevel: DomainEntity.MyLevel?): CharSequence {
        return buildSpannedString {
            append(
                HtmlCompat.fromHtml(
                    context.getString(
                        R.string.level_guide_message,
                        myLevel?.currentLevel,
                        myLevel?.needToReviewCount,
                        myLevel?.nextLevel
                    ),
                    HtmlCompat.FROM_HTML_MODE_LEGACY
                )
            )
        }
    }

    override fun getStringRes(resId: Int): String {
        return context.getString(resId)
    }
}