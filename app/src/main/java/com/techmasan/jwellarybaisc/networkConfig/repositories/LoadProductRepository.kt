package com.techmasan.jwellarybaisc.networkConfig.repositories

import android.util.Log
import com.techmasan.jwellarybaisc.networkConfig.ApiService
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult

import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadProductRepository@Inject constructor(private val apiService: ApiService) {

    suspend fun loadNewProduct(pageNo:Int,token:String) = flow {
        emit(NetworkResult.Loading(true))
        Log.d("loadProduct","executed")
        val response = apiService.loadProduct(pageNo,token)
        Log.d("loadProduct",response.toString())
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}