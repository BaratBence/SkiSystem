package com.e.skiapp.network.services

import com.e.skiapp.network.requests.LoginRequest
import com.e.skiapp.network.response.JwtResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("api/users/login")
    fun login(@Body loginRequest: LoginRequest): Call<JwtResponse>
}