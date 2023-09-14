package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OtpResponse(
    @SerializedName("otp")
    @Expose
    val otp:String
)
