package com.techmasan.jwellarybaisc.networkConfig.viewModels

import androidx.lifecycle.ViewModel
import com.techmasan.jwellarybaisc.networkConfig.repositories.UserRepositories
import javax.inject.Inject

class UserViewModel  @Inject constructor(
    private val jantramRepostory: UserRepositories,
) : ViewModel() {
}