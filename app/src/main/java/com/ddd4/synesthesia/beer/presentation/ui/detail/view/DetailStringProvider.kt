package com.ddd4.synesthesia.beer.presentation.ui.detail.view

import com.ddd4.synesthesia.beer.util.provider.IStringResourceProvider
import com.ddd4.synesthesia.beer.util.provider.StringProvider
import javax.inject.Inject

class DetailStringProvider @Inject constructor(
    private val stringProvider : StringProvider
) : IStringResourceProvider {

    override fun getError() : String = stringProvider.getError()

    override fun getStringRes(resId: Int): String {
        return stringProvider.getStringRes(resId)
    }
}