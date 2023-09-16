package com.techmasan.jwellarybaisc.networkConfig.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult

import com.techmasan.jwellarybaisc.networkConfig.repositories.TokenExpireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TokenExpireViewModel @Inject constructor(
    private val tokenExpireRepository: TokenExpireRepository
) : ViewModel() {
    private var _tokenExpireResponse = MutableLiveData<NetworkResult<Boolean>>()
    val tokenExpireResponse: LiveData<NetworkResult<Boolean>> = _tokenExpireResponse
    suspend fun chekTokenExpire(token:String){
        viewModelScope.launch {
            tokenExpireRepository.chekTokenExpire(token).collect {
                _tokenExpireResponse.postValue(it)
            }
        }
    }
}