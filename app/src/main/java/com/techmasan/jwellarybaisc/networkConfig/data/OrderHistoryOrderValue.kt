package com.techmasan.jwellarybaisc.networkConfig.data

data class OrderHistoryOrderValue(
    var pid:Long,
    var pname:String,
    var thumbnail:String,
    var basePrice:Double,
    var discount:Double,
    var sellPrice:Double,
    var requestQty:Int
)
