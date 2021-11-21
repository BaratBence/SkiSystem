package com.e.skiapp.network.services

import com.e.skiapp.model.User
import com.e.skiapp.network.requests.LoginRequest
import com.e.skiapp.network.response.JwtResponse
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @POST("api/users/login")
    fun login(@Body loginRequest: LoginRequest): Call<JwtResponse>

    @Headers("Content-Type: application/json")
    @PUT("api/users/update")
    fun update(@Body user: User, @Header("Authorization") authHeader: String): Call<JwtResponse>
}