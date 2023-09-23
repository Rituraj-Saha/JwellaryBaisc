package com.techmasan.jwellarybaisc.networkConfig.repositories

import com.techmasan.jwellarybaisc.networkConfig.ApiService
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenRequest
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.OrderRequest
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class OrderRequestRepository @Inject constructor(private val apiService: ApiService)  {
    suspend fun placeOrderRequest(orderRequest: OrderRequest,token:String) = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.placeOrder(orderRequest, token)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}