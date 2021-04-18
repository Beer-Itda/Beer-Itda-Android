package com.ddd4.synesthesia.beer.presentation.ui.common.filter

import kotlinx.coroutines.flow.Flow
import kotlin.properties.ReadWriteProperty

interface IFilterProvider<T, V> : ReadWriteProperty<T, V> {

    var data: V?
    fun getFlow(): Flow<V>
    fun getNames(): List<String>?
    fun getDefaultValue(): V?
}