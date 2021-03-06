package com.rojer_ko.myalbum.utils

sealed class DataResult<out T> {

    //данные получены
    data class Success<T>(val data: T): DataResult<T>()

    //данные в процессе получения
    object Process: DataResult<Nothing>()

    //получена ошибка
    data class Error(val error: Errors): DataResult<Nothing>()
}
