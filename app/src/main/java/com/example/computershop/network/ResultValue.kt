package com.example.computershop.network

import okhttp3.ResponseBody

sealed class ResultValue<out T> {

    data class Success<out T>(val value: T) : ResultValue<T>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : ResultValue<Nothing>()
}