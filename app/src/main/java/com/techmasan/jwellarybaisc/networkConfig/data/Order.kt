package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.Date

data class Order(
    @SerializedName("oid")
    @Expose
    var oid:Long,
    @SerializedName("phoneNumber")
    @Expose
    var phoneNumber:String,
    @SerializedName("orderValue")
    @Expose
    var orderValue:String,
    @SerializedName("totalPrice")
    @Expose
    var totalPrice:Double,
    @SerializedName("orderDate")
    @Expose
    var orderDate:String,
    @SerializedName("address")
    @Expose
    var address:String,
    @SerializedName("email")
    @Expose
    var email:String,
    @SerializedName("status")
    @Expose
    var status:String,
    @SerializedName("feedback")
    @Expose
    var feedback:String?,
    @SerializedName("paymentMethod")
    @Expose
    var paymentMethod:String,
    @SerializedName("paymentStatus")
    @Expose
    var paymentStatus:String?
)
