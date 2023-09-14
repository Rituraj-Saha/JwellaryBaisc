package com.techmasan.jwellarybaisc.networkConfig.repositories

import com.techmasan.jwellarybaisc.networkConfig.ApiService
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenRequest
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GenerateTokenRepository@Inject constructor(private val apiService: ApiService) {

    suspend fun requestToken(generateTokenRequest: GenerateTokenRequest) = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.requestToken(generateTokenRequest)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}