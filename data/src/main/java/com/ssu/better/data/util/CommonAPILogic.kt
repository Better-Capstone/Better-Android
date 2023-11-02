package com.ssu.better.data.util

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

object CommonAPILogic {
    suspend fun <T> checkError(response: Response<T>): Flow<T> {
        return flow {
            if(response.isSuccessful) {
                response.body()?.let {
                    emit(it)
                }
            } else {
                val code = response.code()
                val errorMessage = response.errorBody()?.string()
                throw HttpException(code, errorMessage)
            }
        }
    }
}
