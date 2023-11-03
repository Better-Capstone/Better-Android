package com.ssu.better.data.util

class HttpException(val code: Int, private val errorMessage: String?) : RuntimeException() {
    override val message: String?
        get() = errorMessage
}
