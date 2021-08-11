package com.test.sportzinteractivetest.model

import com.test.sportzinteractivetest.data.APIResponse
import com.test.sportzinteractivetest.data.OperationCallback
import okhttp3.ResponseBody

interface APIDataSource {

    fun retrieveAndroidVersions(callback: OperationCallback<APIResponse>)
    fun cancel()
}