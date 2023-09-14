package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenerateTokenResponse(
    @SerializedName("token")
    @Expose
    val token:String,
    @SerializedName("userDetails")
    @Expose
    val user:User
)
