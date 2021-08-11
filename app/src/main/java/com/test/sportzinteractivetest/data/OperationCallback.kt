package com.test.sportzinteractivetest.data

import com.google.gson.JsonObject


interface OperationCallback<T> {
    fun onSuccess(data: JsonObject)
    fun onError(error: String?)
}