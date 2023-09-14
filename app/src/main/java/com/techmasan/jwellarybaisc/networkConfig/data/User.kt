package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("name")
    @Expose
    val name:String,

    @SerializedName("email")
    @Expose
    val email:String,

    @SerializedName("phoneNumber")
    @Expose
    val phoneNumber:String,

    @SerializedName("addressLine")
    @Expose
    val addressLine:String,

    @SerializedName("pinCode")
    @Expose
    val pinCode:String,

    @SerializedName("state")
    @Expose
    val state:String
)
