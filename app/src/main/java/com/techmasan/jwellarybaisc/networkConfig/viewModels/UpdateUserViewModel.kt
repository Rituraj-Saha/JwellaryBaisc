package com.techmasan.jwellarybaisc.networkConfig.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techmasan.jwellarybaisc.networkConfig.data.NetworkResult
import com.techmasan.jwellarybaisc.networkConfig.data.User
import com.techmasan.jwellarybaisc.networkConfig.data.UserUpdate

import com.techmasan.jwellarybaisc.networkConfig.repositories.UpdateRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UpdateUserViewModel@Inject constructor(
    private val userUpdateRepository: UpdateRepository
) : ViewModel(){
    private var _userUpdateResponse = MutableLiveData<NetworkResult<User>>()
    val userUpdateResponse: LiveData<NetworkResult<User>> = _userUpdateResponse
    suspend fun updateUser(userUpdate: UserUpdate,token:String){
        viewModelScope.launch {
            userUpdateRepository.updateUser(userUpdate,token).collect {
                _userUpdateResponse.postValue(it)
            }
        }
    }
}