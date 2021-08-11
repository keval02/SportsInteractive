package com.test.sportzinteractivetest.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.JsonObject
import com.test.sportzinteractivetest.data.APIResponse
import com.test.sportzinteractivetest.data.OperationCallback
import com.test.sportzinteractivetest.model.APIRepository
import okhttp3.ResponseBody

class APIViewModel(private val repository: APIRepository) : ViewModel() {

    private val _teamDetails = MutableLiveData<JsonObject>()
    val teams: LiveData<JsonObject> = _teamDetails

    private val _isViewLoading = MutableLiveData<Boolean>()
    val isViewLoading: LiveData<Boolean> = _isViewLoading

    private val _onMessageError = MutableLiveData<Any>()
    val onMessageError: LiveData<Any> = _onMessageError

    private val _isEmptyList = MutableLiveData<Boolean>()
    val isEmptyList: LiveData<Boolean> = _isEmptyList

    init {
        loadVersions()
    }

    fun loadVersions() {
        _isViewLoading.value = true
        repository.fetchVersions(object : OperationCallback<APIResponse> {
            override fun onError(error: String?) {
                _isViewLoading.value = false
                _onMessageError.value = error
            }

            override fun onSuccess(data: JsonObject) {
                _isViewLoading.value = false
                _teamDetails.value = data

            }
        })
    }
}