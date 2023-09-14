package com.techmasan.jwellarybaisc.networkConfig

import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenRequest
import com.techmasan.jwellarybaisc.networkConfig.data.GenerateTokenResponse
import com.techmasan.jwellarybaisc.networkConfig.data.OtpRequest
import com.techmasan.jwellarybaisc.networkConfig.data.OtpResponse
import com.techmasan.jwellarybaisc.networkConfig.data.User
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

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

}