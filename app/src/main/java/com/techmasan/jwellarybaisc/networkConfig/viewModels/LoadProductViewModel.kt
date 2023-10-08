package com.techmasan.jwellarybaisc.networkConfig.viewModels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.RetrivedProduct

import com.techmasan.jwellarybaisc.networkConfig.repositories.LoadProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoadProductViewModel@Inject constructor(
    private val loadProductRepository: LoadProductRepository
) : ViewModel() {
    private var _loadNewProductResponse = MutableLiveData<NetworkResult<List<RetrivedProduct>>>()
    val loadNewProductResponse: LiveData<NetworkResult<List<RetrivedProduct>>> = _loadNewProductResponse
    suspend fun loadNewProduct(pageNo: Int) {
        Log.d("loadProduct","executed")
        viewModelScope.launch {
            loadProductRepository.loadNewProduct(pageNo).collect {
                _loadNewProductResponse.postValue(it)
            }
        }
    }

    private var _loadNewFocusedProductResponse = MutableLiveData<NetworkResult<List<RetrivedProduct>>>()
    val loadNewFocusedProductResponse: LiveData<NetworkResult<List<RetrivedProduct>>> = _loadNewFocusedProductResponse
    suspend fun loadNewFocusedProduct() {
        Log.d("loadProduct","executed")
        viewModelScope.launch {
            loadProductRepository.loadNewFocusedProduct().collect {
                _loadNewFocusedProductResponse.postValue(it)
            }
        }
    }
}