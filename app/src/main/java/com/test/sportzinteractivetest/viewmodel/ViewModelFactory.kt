package com.test.sportzinteractivetest.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.test.sportzinteractivetest.model.APIRepository

class ViewModelFactory(private val repository: APIRepository) :
    ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return APIViewModel(repository) as T
    }
}