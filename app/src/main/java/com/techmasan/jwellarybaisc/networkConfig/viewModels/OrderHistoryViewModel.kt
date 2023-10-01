package com.techmasan.jwellarybaisc.networkConfig.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.OrderHistoryResponse
import com.techmasan.jwellarybaisc.networkConfig.data.OrderRequest
import com.techmasan.jwellarybaisc.networkConfig.data.OrderResponse
import com.techmasan.jwellarybaisc.networkConfig.repositories.OrderHistoryRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val orderHistoryRepository: OrderHistoryRepository
) : ViewModel(){
    private var _orderHistoryResponse = MutableLiveData<NetworkResult<List<OrderHistoryResponse>>>()
    val orderHistoryResponse: LiveData<NetworkResult<List<OrderHistoryResponse>>> = _orderHistoryResponse
    suspend fun orderHistoryResponse(phoneNumber: String, token:String) {
        viewModelScope.launch {
            orderHistoryRepository.requestOrderHistory(phoneNumber,token).collect {
                _orderHistoryResponse.postValue(it)
            }
        }
    }
}