package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import com.hjiee.core.provider.IStringResourceProvider
import com.hjiee.core.provider.StringProvider
import javax.inject.Inject

class BeerDetailStringProvider @Inject constructor(
    private val stringProvider: StringProvider
) : IStringResourceProvider {

    override fun getError(): String = stringProvider.getError()

    override fun getStringRes(resId: Int): String {
        return stringProvider.getStringRes(resId)
    }
}