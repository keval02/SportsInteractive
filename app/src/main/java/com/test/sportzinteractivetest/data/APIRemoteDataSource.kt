package com.test.sportzinteractivetest.data

import android.util.Log
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.test.sportzinteractivetest.model.APIDataSource
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class APIRemoteDataSource(apiClient: ApiClient) : APIDataSource {

    private var call: Call<JsonObject>? = null
    private val service = apiClient.build()


    override fun retrieveAndroidVersions(callback: OperationCallback<APIResponse>) {

        call = service?.apiCall()
        call?.enqueue(object : Callback<JsonObject> {
            override fun onFailure(call: Call<JsonObject>, t: Throwable) {
                callback.onError(t.message)
            }

            override fun onResponse(
                call: Call<JsonObject>,
                response: Response<JsonObject>
            ) {
                response.body()?.let {
                    if (response.isSuccessful) {
                        callback.onSuccess(it)
                    } else {
                        callback.onError("Something went wrong!")
                    }
                }
            }
        })
    }

    override fun cancel() {
        call?.let {
            it.cancel()
        }
    }
}