package com.ddd4.synesthesia.beer.presentation.base.recyclerview2

interface IViewTypeGetter<VT : Enum<VT>> {
    fun getViewType(): VT

    fun getViewTypeOrdinal(): Int {
        return getViewType().ordinal
    }

    fun getViewTypeName(): String {
        return getViewType().name
    }
}
