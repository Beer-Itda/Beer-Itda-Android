package com.ddd4.synesthesia.beer.data.utils

interface ItemClickListener {
    fun <T> onItemClick(item : T)
}

interface ItemLongClickListener {
    fun <T> onItemLongClick(item : T) : Boolean?
}