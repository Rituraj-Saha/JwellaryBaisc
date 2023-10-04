package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UpiConfig(
    @SerializedName("upiVpa")
    @Expose
    var upiVpa:String,
    @SerializedName("upiName")
    @Expose
    var upiName:String,
    @SerializedName("upiMerchantCode")
    @Expose
    var upiMerchantCode:String
)
