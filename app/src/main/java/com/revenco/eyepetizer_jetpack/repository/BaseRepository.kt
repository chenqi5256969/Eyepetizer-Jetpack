package com.revenco.eyepetizer_jetpack.repository

import com.revenco.eyepetizer_jetpack.net.bean.resp.base.RESULT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope

open class BaseRepository {
    suspend fun <T : Any> safeApiCall(call: suspend () -> RESULT<T>): RESULT<T> {
        return try {
            call()
        } catch (e: Exception) {
            RESULT.OnError("4", e.message.toString())
        }
    }

    suspend fun <T : Any> handleResponse(
        response: T,
        successBlock: (suspend CoroutineScope.() -> Unit)? = null
    ): RESULT<T> {
        return coroutineScope {
            successBlock?.let {
                it()
            }
            RESULT.OnSuccess(response)
        }
    }



}