package com.techmasan.jwellarybaisc.networkConfig.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenRequest
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenResponse
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult

import com.techmasan.jwellarybaisc.networkConfig.repositories.GenerateTokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

import javax.inject.Inject
@HiltViewModel
class GenerateTokenViewModel@Inject constructor(
    private val generateTokenRepository: GenerateTokenRepository,
) : ViewModel() {
    private var _generateTokenResponse = MutableLiveData<NetworkResult<GenerateTokenResponse>>()
    val generateTokenResponse: LiveData<NetworkResult<GenerateTokenResponse>> = _generateTokenResponse
    suspend fun sendTokenRequest(generateTokenRequest: GenerateTokenRequest) {
        viewModelScope.launch {
            generateTokenRepository.requestToken(generateTokenRequest).collect {
                _generateTokenResponse.postValue(it)
            }
        }
    }

}