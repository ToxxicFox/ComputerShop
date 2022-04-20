package com.example.computershop.repositories

import android.util.Log
import com.example.computershop.network.ResultValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

private const val TAG = "API"

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : ResultValue<T> {

        return withContext(Dispatchers.IO) {
            try {
                ResultValue.Success(apiCall.invoke())
            } catch (throwable: Throwable){
                Log.d(TAG, throwable.toString())
                when(throwable) {
                    is HttpException -> {
                        ResultValue.Failure(
                            false,
                            throwable.code(),
                            throwable.response()?.errorBody())
                    }
                    else -> {
                        ResultValue.Failure(
                            true,
                            null,
                            null
                        )
                    }
                }
            }
        }
    }
}