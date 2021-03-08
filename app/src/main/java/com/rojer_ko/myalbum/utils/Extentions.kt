package com.rojer_ko.myalbum.utils

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String) {
    Toast.makeText(this.context, message, Toast.LENGTH_LONG).show()
}

fun String?.cut(maxLength: Int, addDots: Boolean): String {
    this?.apply {
        val string = this.subSequence(0, getMaxLength(this, maxLength)).toString()
        val dots = "..."
        return if (addDots) {
            "$string$dots"
        } else {
            string
        }
    }
    return ""
}

private fun getMaxLength(string: String, maxLength: Int): Int {
    return string.length.takeIf {
        it < maxLength
    } ?: maxLength
}