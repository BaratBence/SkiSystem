package com.e.skiapp.network

import android.content.Context
import android.content.Intent
import android.widget.Toast

import com.e.skiapp.network.requests.LoginRequest
import com.e.skiapp.network.response.JwtResponse
import com.e.skiapp.network.services.UserService
import com.e.skiapp.view.LoginActivity
import com.e.skiapp.view.MainMenuActivity

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.*

import java.util.*


class RetrofitClient {

    companion object {
        //TODO: SHOULD BE CHANGED
        private val BASE_URL = "https://192.168.1.123:8443/"

        private var retrofit: Retrofit? = null

        fun getInstance(context: Context): Retrofit? {
            if (retrofit == null) {
                retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
            }
            return retrofit
        }
    }
}