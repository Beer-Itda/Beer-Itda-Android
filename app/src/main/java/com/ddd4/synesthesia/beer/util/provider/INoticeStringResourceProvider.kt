package com.ddd4.synesthesia.beer.util.provider

import com.ddd4.synesthesia.beer.R

interface INoticeStringResourceProvider : IStringResourceProvider{
    enum class Code {
        USE_NOTICE_VERSION,
        NOTICE,
        PRIVACY_POLICY,
        TERMS_OF_USE,
        RELEASE_NOTE
        ;
    }

    fun getString(code: Code): String {
        return when (code) {
            Code.USE_NOTICE_VERSION -> { getStringRes(R.string.use_notice_version) }
            Code.NOTICE -> { getStringRes(R.string.notice) }
            Code.PRIVACY_POLICY -> { getStringRes(R.string.privacy_policy) }
            Code.TERMS_OF_USE -> { getStringRes(R.string.terms_of_use) }
            Code.RELEASE_NOTE -> { getStringRes(R.string.release_note) }
        }
    }
}