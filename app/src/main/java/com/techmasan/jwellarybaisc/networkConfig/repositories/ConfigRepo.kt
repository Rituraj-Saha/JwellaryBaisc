package com.techmasan.jwellarybaisc.networkConfig.repositories

import com.techmasan.jwellarybaisc.networkConfig.ApiService
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenRequest
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ConfigRepo@Inject constructor(private val apiService: ApiService) {
    suspend fun requestConfig() = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getConfig()
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }

    suspend fun requestHelpline() = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.getHelpline()
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}