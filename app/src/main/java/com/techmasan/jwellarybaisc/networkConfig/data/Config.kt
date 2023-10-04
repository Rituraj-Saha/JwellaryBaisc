package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Config (
    @SerializedName("vcode")
    @Expose
    var VERSIONCODE:String,
    @SerializedName("vname")
    @Expose
    var VERSIONNAME:String,
    @SerializedName("appStatus")
    @Expose
    var ACTIVE:String
)