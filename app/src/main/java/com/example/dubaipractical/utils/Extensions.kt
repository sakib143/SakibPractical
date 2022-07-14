package com.example.dubaipractical.utils

import android.content.Context
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment


/**
 * Inflates view
 */
fun android.view.ViewGroup.inflate(layoutId: Int): View {
    return android.view.LayoutInflater.from(context).inflate(layoutId, this, false)
}

/**
 * Creates an [AutoClearedValue] associated with this fragment.
 */
fun <T : Any> Fragment.autoCleared() = AutoClearedValue<T>(this)

/**
 * Show Visibility of a [View]
 */
fun View.show() {
    this.visibility = View.VISIBLE
}

/**
 * Hide Visibility of a [View]
 */
fun View.hide() {
    this.visibility = View.GONE
}

/**
 * Invisible Visibility of a [View]
 */
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Context.toast(message:String) {
    val spannableStringBuilder = SpannableStringBuilder(message);
    spannableStringBuilder.setSpan(RelativeSizeSpan(1.35f), 0, message.length, 0)
    Toast.makeText(this,spannableStringBuilder, Toast.LENGTH_LONG).show()
}