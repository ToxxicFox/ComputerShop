package com.example.computershop.repositories

import com.example.computershop.network.ResultValue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

abstract class BaseRepository {

    suspend fun <T> safeApiCall(
        apiCall: suspend () -> T
    ) : ResultValue<T> {

        return withContext(Dispatchers.IO) {
            try {
                ResultValue.Success(apiCall.invoke())
            } catch (throwable: Throwable){
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