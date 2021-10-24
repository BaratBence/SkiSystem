package com.e.skiapp.network

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

class RetrofitClient {

    //TODO: SHOULD BE CHANGED
    private val BASE_URL = "https://192.168.1.123:8443/"

    fun login(loginActivity: LoginActivity, username: String, password: String) {
        val retrofit = Retrofit.Builder().baseUrl(BASE_URL).addConverterFactory(GsonConverterFactory.create()).build()
        val call = retrofit.create(UserService::class.java).login(loginRequest = LoginRequest(username, password))
        call.enqueue(object : Callback<JwtResponse> {
            override fun onResponse(call: Call<JwtResponse>, message: Response<JwtResponse>) {
                if (message.code() == 200) {
                    //TODO: add response
                    val intent = Intent(loginActivity, MainMenuActivity::class.java)
                    loginActivity.startActivity(intent)
                }
                if(message.code() == 401)  Toast.makeText(loginActivity.applicationContext, "username or password is wrong", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<JwtResponse>, t: Throwable) {

            }
        })
    }
}