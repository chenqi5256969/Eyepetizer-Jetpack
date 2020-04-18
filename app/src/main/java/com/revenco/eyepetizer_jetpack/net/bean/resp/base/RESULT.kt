package com.revenco.eyepetizer_jetpack.net.bean.resp.base


sealed class RESULT< out  T:Any> {

    data class OnSuccess<T:Any>(val data: T) : RESULT<T>()

    data class OnError (val errcode: String, val errMsg: String) : RESULT<Nothing>()
}