package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderResponse (
    @SerializedName("order")
    @Expose
    var order:Order,
    @SerializedName("msg")
    @Expose
    var msg:String
    )

