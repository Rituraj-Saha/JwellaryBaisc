package com.techmasan.jwellarybaisc.networkConfig.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmasan.jwellarybaisc.networkConfig.data.Config
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenRequest
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenResponse
import com.techmasan.jwellarybaisc.networkConfig.data.Helpline
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.repositories.ConfigRepo
import com.techmasan.jwellarybaisc.networkConfig.repositories.GenerateTokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConfigViewModel @Inject constructor(
    private val configRepo: ConfigRepo
) : ViewModel(){
    private var _configResponse = MutableLiveData<NetworkResult<Config>>()
    val configResponse: LiveData<NetworkResult<Config>> = _configResponse
    suspend fun getConfig() {
        viewModelScope.launch {
            configRepo.requestConfig().collect {
                _configResponse.postValue(it)
            }
        }
    }

    private var _helpLineResponse = MutableLiveData<NetworkResult<Helpline>>()
    val helpLineResponse: LiveData<NetworkResult<Helpline>> = _helpLineResponse
    suspend fun requestHelpline() {
        viewModelScope.launch {
            configRepo.requestHelpline().collect {
                _helpLineResponse.postValue(it)
            }
        }
    }
}