package com.techmasan.jwellarybaisc.networkConfig.repositories

import com.techmasan.jwellarybaisc.networkConfig.ApiService
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.UserUpdate
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UpdateRepository@Inject constructor(private val apiService: ApiService) {
    suspend fun updateUser(userUpdate: UserUpdate,token:String) = flow {
        emit(NetworkResult.Loading(true))
        val response = apiService.updateUser(userUpdate,token)
        emit(NetworkResult.Success(response))
    }.catch { e ->
        emit(NetworkResult.Failure(e.message ?: "Unknown Error"))
    }
}