package com.techmasan.jwellarybaisc.networkConfig.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.OtpRequest
import com.techmasan.jwellarybaisc.networkConfig.data.OtpResponse
import com.techmasan.jwellarybaisc.networkConfig.repositories.LoginRepository
import com.techmasan.jwellarybaisc.networkConfig.repositories.UserRepositories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepostory: LoginRepository,
) : ViewModel() {
    private var _otpResponse = MutableLiveData<NetworkResult<OtpResponse>>()
    val otpResponse: LiveData<NetworkResult<OtpResponse>> = _otpResponse
    suspend fun sendotpRequest(otpRequest: OtpRequest) {
        viewModelScope.launch {
            loginRepostory.requestOtp(otpRequest).collect {
                _otpResponse.postValue(it)
            }
        }
    }
}