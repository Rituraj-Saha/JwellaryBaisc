package com.techmasan.jwellarybaisc.networkConfig.data

data class ProductRequestForOrder(
    var pid:Long,
    var pname:String,
    var thumbnail:String,
    var basePrice:Double,
    var discount:Int,
    var sellPrice:Double,
    var requestQty:Int
)
