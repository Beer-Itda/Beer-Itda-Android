package com.ddd4.synesthesia.beer.ext

fun Boolean?.orFalse(): Boolean = this ?: false
fun Boolean?.toggle(): Boolean = this?.not().orFalse()