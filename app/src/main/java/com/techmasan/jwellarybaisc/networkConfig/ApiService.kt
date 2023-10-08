package com.techmasan.jwellarybaisc.networkConfig

import com.techmasan.jwellarybaisc.Util
import com.techmasan.jwellarybaisc.model.Grid1Home
import com.techmasan.jwellarybaisc.networkConfig.data.Config
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenRequest
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenResponse
import com.techmasan.jwellarybaisc.networkConfig.data.Helpline
import com.techmasan.jwellarybaisc.networkConfig.data.OrderHistoryResponse
import com.techmasan.jwellarybaisc.networkConfig.data.OrderRequest
import com.techmasan.jwellarybaisc.networkConfig.data.OrderResponse
import com.techmasan.jwellarybaisc.networkConfig.data.OtpRequest
import com.techmasan.jwellarybaisc.networkConfig.data.OtpResponse
import com.techmasan.jwellarybaisc.networkConfig.data.ProductRequestForOrder
import com.techmasan.jwellarybaisc.networkConfig.data.RetrivedProduct
import com.techmasan.jwellarybaisc.networkConfig.data.UpiConfig
import com.techmasan.jwellarybaisc.networkConfig.data.User
import com.techmasan.jwellarybaisc.networkConfig.data.UserUpdate
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @Headers("Content-type: application/json")
    @POST("/auth/generateToken/generateOtp")
    suspend fun requestOtp(@Body otpRequest: OtpRequest) : OtpResponse

    @Headers("Content-type: application/json")
    @POST("/auth/generateToken/generate")
    suspend fun requestToken(@Body generateTokenRequest: GenerateTokenRequest) : GenerateTokenResponse

    @Headers("Content-type: application/json")
    @POST("/auth/addNewUser")
    suspend fun addNewUser(@Body user: User) : OtpResponse

    @Headers("Content-type: application/json")
    @GET("/product/get-product")
    suspend fun loadProduct(@Query("pageNo") pageNo:Int) : List<RetrivedProduct>


    @Headers("Content-type: application/json")
    @GET("/product/get-product/focusable")
    suspend fun loadfocusedProduct() : List<RetrivedProduct>


    @Headers("Content-type: application/json")
    @POST("/api/order/place-order")
    suspend fun placeOrder(@Body orderRequest:OrderRequest, @Header("Authorization")token:String) : OrderResponse


    @Headers("Content-type: application/json")
    @GET("/api/order/order-history/findBy")
    suspend fun findOrderHistory(@Query("phn") phn:String, @Header("Authorization")token:String) : List<OrderHistoryResponse>



    @Headers("Content-type: application/json")
    @POST("/auth/generateToken/tokenExpireCheck")
    suspend fun chekTokenExpire(@Body token: String):Boolean


    @Headers("Content-type: application/json")
    @GET("/config/details")
    suspend fun getConfig() : Config


    @Headers("Content-type: application/json")
    @GET("/config/upi")
    suspend fun getUpi() : UpiConfig


    @Headers("Content-type: application/json")
    @GET("/config/helpline")
    suspend fun getHelpline() : Helpline


    @Headers("Content-type: application/json")
    @POST("/auth/user/update-user")
    suspend fun updateUser(@Body userUpdate: UserUpdate,@Header("Authorization")token:String) : User


}