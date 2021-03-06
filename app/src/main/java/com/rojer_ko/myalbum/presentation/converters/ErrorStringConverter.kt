package com.rojer_ko.myalbum.presentation.converters

import android.content.Context
import com.rojer_ko.myalbum.R
import com.rojer_ko.myalbum.utils.Errors

class ErrorStringConverter {
    companion object {

        fun convertToContainer(context: Context, error: Errors): String {
            return when (error) {
                Errors.BAD_RESPONSE -> context.getString(R.string.data_error)
                Errors.NETWORK_UNAVAILABLE -> context.getString(R.string.network_unavailable_error)
                Errors.UNKNOWN -> context.getString(R.string.unknown_error)
                else -> context.getString(R.string.unknown_error)
            }
        }
    }
}