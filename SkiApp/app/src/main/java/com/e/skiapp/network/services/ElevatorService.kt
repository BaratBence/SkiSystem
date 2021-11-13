package com.e.skiapp.network.services

import com.e.skiapp.model.ElevatorApplication
import retrofit2.Call
import retrofit2.http.*

interface ElevatorService {
    @GET("api/elevators")
    fun getAll(@Header("Authorization") authHeader: String): Call<List<ElevatorApplication>>

    @PUT("api/elevators/{id}/turnOff")
    fun turnOff(@Path("id") id:String,@Header("Authorization") authHeader: String): Call<ElevatorApplication>

    @PUT("api/elevators/{id}/turnOn")
    fun turnOn(@Path("id") id:String,@Header("Authorization") authHeader: String): Call<ElevatorApplication>

}