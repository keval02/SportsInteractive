package com.test.sportzinteractivetest.model

import com.test.sportzinteractivetest.data.APIResponse
import com.test.sportzinteractivetest.data.OperationCallback
import okhttp3.ResponseBody


class APIRepository(private val versionDataSource: APIDataSource) {
    fun fetchVersions(callback: OperationCallback<APIResponse>) {
        versionDataSource.retrieveAndroidVersions(callback)
    }
}