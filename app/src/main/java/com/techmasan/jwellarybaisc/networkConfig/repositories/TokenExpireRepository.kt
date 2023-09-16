package com.techmasan.jwellarybaisc.networkConfig.repositories

import com.techmasan.jwellarybaisc.networkConfig.ApiService
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class TokenExpireRepository@Inject constructor(private val apiService: ApiService) {
    suspend fun chekTokenExpire(token: String) = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.chekTokenExpire(token)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}