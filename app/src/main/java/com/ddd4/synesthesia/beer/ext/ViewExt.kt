package com.ddd4.synesthesia.beer.ext

import android.graphics.Typeface
import android.view.View
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.google.android.material.snackbar.Snackbar


fun View.showSnackBar(message: String, duration: Int? = null) {
    Snackbar.make(this, message, duration ?: Snackbar.LENGTH_SHORT).also { snackbar ->
        snackbar.view.apply {
            val view = findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            view.textAlignment = View.TEXT_ALIGNMENT_CENTER
            snackbar.show()
        }
    }
}

fun TextView.updateTypeface(options: Pair<Typeface?, Int>) =
    this.apply {
        typeface = options.first
        setTextColor(options.second)
    }

fun View.isVisible() = visibility == View.VISIBLE
fun View.setVisible(show: Boolean) {
    if (isVisible() != show) {
        visibility = if (show) View.VISIBLE else View.GONE
    }
}