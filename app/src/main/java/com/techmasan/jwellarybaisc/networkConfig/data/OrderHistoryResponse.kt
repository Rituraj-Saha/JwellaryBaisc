package com.techmasan.jwellarybaisc.networkConfig.data

import java.util.Date

data class OrderHistoryResponse(
    var oid: Long,
    var phoneNumber: String,
    var orderValue:String,
    var totalPrice:Double,
    var orderDate: String,
    var address: String,
    var email: String,
    var status:String,
    var paymentMethod:String

)
