package com.techmasan.jwellarybaisc.networkConfig.data

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UserUpdate(
    @SerializedName("phn")
    @Expose
     val phn:String,
    @SerializedName("address")
    @Expose
     val address:String,
    @SerializedName("email")
    @Expose
     val email:String,
    @SerializedName("name")
    @Expose
     val name:String,
    @SerializedName("pin_code")
    @Expose
     val pin_code:String,
    @SerializedName("state")
    @Expose
     val state:String
)
