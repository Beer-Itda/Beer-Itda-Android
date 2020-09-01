package com.ddd4.synesthesia.beer.data.utils

import android.util.TypedValue
import android.view.View
import androidx.databinding.BindingAdapter
import com.ddd4.synesthesia.beer.R
import com.ddd4.synesthesia.beer.data.source.local.InfomationsType
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

@BindingAdapter("app:addChip")
fun makeChips(view: View, flavor: List<String>) {
    for (i in flavor) {
        val chip = Chip(view.context).apply {
            text = i
            setTextSize(TypedValue.COMPLEX_UNIT_SP, 14f)
            setTextColor(resources.getColor(R.color.black, null))
            chipBackgroundColor = resources.getColorStateList(R.color.white, null)

        }
        (view as ChipGroup).addView(chip)
    }
}

@BindingAdapter(value = ["forgroundSelected"])
fun forgroundSelected(view : View, type : InfomationsType) = if(type == InfomationsType.HEADER) {
    view.foreground = null
} else {
    val typeValue = TypedValue()
    view.context.theme.resolveAttribute(android.R.attr.selectableItemBackground,typeValue,true)
    view.foreground = view.context.getDrawable(typeValue.resourceId)
}