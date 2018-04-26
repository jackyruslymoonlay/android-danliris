package com.training.training

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthenticationService {
    @POST("authenticate")
    fun login(@Body user: User): Call<Token>
}