package com.ddd4.synesthesia.beer.presentation.base.recyclerview2

/**
 * Adatper 의 Item List 를 제공하는 interface
 */
interface ItemListProvider<VM : Any> {
    fun getItemList(): List<VM>
}