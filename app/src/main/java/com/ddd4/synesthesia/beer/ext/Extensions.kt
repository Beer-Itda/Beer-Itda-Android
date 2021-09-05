package com.ddd4.synesthesia.beer.ext

fun Boolean?.orFalse(): Boolean = this ?: false
fun Boolean?.toggle(): Boolean = this?.not().orFalse()

fun String?.orDefault(default: String): String = if (this.isNullOrEmpty()) {
    default
} else {
    this
}