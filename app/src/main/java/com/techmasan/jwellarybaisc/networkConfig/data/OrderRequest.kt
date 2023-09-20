package com.techmasan.jwellarybaisc.networkConfig.data

data class OrderRequest(
    var phoneNumber:String,
    var orderValue:String,
    var totalPrice:String,
    var address:String,
    var email:String
)
