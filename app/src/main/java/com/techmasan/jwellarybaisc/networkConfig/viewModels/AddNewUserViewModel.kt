package com.techmasan.jwellarybaisc.networkConfig.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenRequest
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenResponse
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.OtpResponse
import com.techmasan.jwellarybaisc.networkConfig.data.User
import com.techmasan.jwellarybaisc.networkConfig.repositories.AddNewUserRepository
import com.techmasan.jwellarybaisc.networkConfig.repositories.GenerateTokenRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddNewUserViewModel@Inject constructor(
    private val addNewUserRepository: AddNewUserRepository
) : ViewModel() {
    private var _addNewUser = MutableLiveData<NetworkResult<OtpResponse>>()
    val addNewUser: LiveData<NetworkResult<OtpResponse>> = _addNewUser
    suspend fun sendAddNewRequest(user: User) {
        viewModelScope.launch {
            addNewUserRepository.requestAddNewUser(user).collect {
                _addNewUser.postValue(it)
            }
        }
    }
}