package com.training.training

import android.net.wifi.hotspot2.pps.Credential
import retrofit2.Call
import retrofit2.http.*

interface BuyerService {
    @GET("master/buyers")
    fun get(@Header("Authorization") credentials: String): Call<BuyerResponse>

    @GET("master/buyers/{id}")
    fun getById(@Path("id") id: String, @Header("Authorization") credentials: String): Call<BuyerDetailResponse>
}