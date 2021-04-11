package com.ddd4.synesthesia.beer.util

import java.util.regex.Pattern

object RegexUtil {

    private val nickNameRegex = "^[가-힣ㄱ-ㅎa-zA-Z0-9_-]{2,}"

    fun isValidNickName(text: String): Boolean {
        return Pattern.matches(nickNameRegex, text)
    }
}