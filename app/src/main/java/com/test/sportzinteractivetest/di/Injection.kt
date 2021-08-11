package com.test.sportzinteractivetest.di

import androidx.lifecycle.ViewModelProvider
import com.test.sportzinteractivetest.data.APIRemoteDataSource
import com.test.sportzinteractivetest.data.ApiClient
import com.test.sportzinteractivetest.model.APIDataSource
import com.test.sportzinteractivetest.model.APIRepository
import com.test.sportzinteractivetest.viewmodel.ViewModelFactory


object Injection {

    private val apiDataSource: APIDataSource =
        APIRemoteDataSource(ApiClient)
    private val repository = APIRepository(apiDataSource)
    private val viewModelFactory = ViewModelFactory(repository)

    fun provideViewModelFactory(): ViewModelProvider.Factory {
        return viewModelFactory
    }
}