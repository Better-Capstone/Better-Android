package com.ssu.better.util

import com.google.gson.Gson
import com.ssu.better.data.util.HttpException
import com.ssu.better.entity.error.NotFoundErrorBody
import com.ssu.better.entity.error.ValidationErrorBody

fun String.convertToNotFoundError(): NotFoundErrorBody {
    return Gson().fromJson(this, NotFoundErrorBody::class.java)
}

fun String.convertToValidationError(): ValidationErrorBody {
    return Gson().fromJson(this, ValidationErrorBody::class.java)
}

fun HttpException.getHttpErrorMsg(): String {
    val unknownErrorMsg = "알 수 없는 오류"
    try {
        return when (this.code) {
            404 -> this.message?.convertToNotFoundError()?.data ?: unknownErrorMsg
            403 -> this.message?.convertToValidationError()?.data.toString()
            415 -> this.message?.convertToValidationError()?.error.toString()
            400 -> this.message?.convertToNotFoundError()?.data.toString()
            else -> unknownErrorMsg
        }
    } catch (e: Exception) {
        return unknownErrorMsg
    }
}
