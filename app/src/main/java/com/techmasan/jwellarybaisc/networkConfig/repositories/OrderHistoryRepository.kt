package com.techmasan.jwellarybaisc.networkConfig.repositories

import com.techmasan.jwellarybaisc.networkConfig.ApiService
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderHistoryRepository@Inject constructor(private val apiService: ApiService) {
    suspend fun requestOrderHistory(phoneNumber: String,token:String) = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.findOrderHistory(phoneNumber,token)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }



}