package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class GenerateTokenRequest(
    @SerializedName("username")
    @Expose
    val username:String,
    @SerializedName("password")
    @Expose
    val password:String,
)
