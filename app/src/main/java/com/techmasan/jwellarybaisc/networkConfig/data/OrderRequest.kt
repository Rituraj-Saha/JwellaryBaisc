package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class OrderRequest(
    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber:String,

    @SerializedName("orderValue")
    @Expose
    var orderValue:List<ProductRequestForOrder>,

    @SerializedName("totalPrice")
    @Expose
    var totalPrice:Double,

    @SerializedName("address")
    @Expose
    var address:String,

    @SerializedName("email")
    @Expose
    var email:String,

    @SerializedName("paymentMethod")
    @Expose
    var paymentMethod:String,

    @SerializedName("paymentStatus")
    @Expose
    var paymentStatus:String
)
