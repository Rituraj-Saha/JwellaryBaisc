package com.techmasan.jwellarybaisc.networkConfig.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.OrderRequest
import com.techmasan.jwellarybaisc.networkConfig.data.OrderResponse
import com.techmasan.jwellarybaisc.networkConfig.data.OtpRequest

import com.techmasan.jwellarybaisc.networkConfig.repositories.OrderRequestRepository
import dagger.hilt.android.lifecycle.HiltViewModel

import kotlinx.coroutines.launch
import javax.inject.Inject
@HiltViewModel
class OrderViewModel @Inject constructor(
    private val orderRequestRepository: OrderRequestRepository
) : ViewModel() {
    private var _orderResponse = MutableLiveData<NetworkResult<OrderResponse>>()
    val orderResponse: LiveData<NetworkResult<OrderResponse>> = _orderResponse
    suspend fun sendOrderRequest(orderRequest: OrderRequest,token:String) {
        viewModelScope.launch {
            orderRequestRepository.placeOrderRequest(orderRequest,token).collect {
                _orderResponse.postValue(it)
            }
        }
    }
}