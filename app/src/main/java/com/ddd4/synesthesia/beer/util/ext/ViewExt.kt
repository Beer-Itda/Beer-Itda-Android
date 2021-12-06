package com.ddd4.synesthesia.beer.util.ext

import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
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
fun View.setInVisible(show: Boolean) {
    if (isVisible() != show) {
        visibility = if (show) View.VISIBLE else View.INVISIBLE
    }
}

fun ViewGroup.createView(@LayoutRes layoutId: Int): View =
    LayoutInflater.from(context).inflate(layoutId, this, false)