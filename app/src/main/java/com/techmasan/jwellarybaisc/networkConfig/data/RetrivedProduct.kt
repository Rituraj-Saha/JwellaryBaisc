package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RetrivedProduct (
    @SerializedName("pid")
    @Expose
    var pid:Long ,
    @SerializedName("pname")
    @Expose
var pname:String ,
    @SerializedName("description")
    @Expose
var description:String,
    @SerializedName("thumbnail")
    @Expose
var thumbnail:String,
    @SerializedName("imageList")
    @Expose
var imageList:String,
    @SerializedName("basePrice")
    @Expose
var basePrice:Double,
    @SerializedName("discount")
    @Expose
var discount:Double ,
    @SerializedName("costPrice")
    @Expose
var costPrice:Double ,
    @SerializedName("sellPrice")
    @Expose
var sellPrice:Double
    )