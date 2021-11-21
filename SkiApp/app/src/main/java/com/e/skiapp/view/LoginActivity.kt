package com.e.skiapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.e.skiapp.R
import com.e.skiapp.databinding.ActivityLoginBinding
import com.e.skiapp.model.User
import com.e.skiapp.model.UserData
import com.e.skiapp.network.RetrofitClient
import com.e.skiapp.network.requests.LoginRequest
import com.e.skiapp.network.response.JwtResponse
import com.e.skiapp.network.services.UserService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private var retrofit: Retrofit ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login) as ActivityLoginBinding


        binding.loginbutton.setOnClickListener {
            var name = binding.editTextPersonName.text.toString()
            var pw = binding.editTextPassword.text.toString()
            if(!checkInput(pw,name)) return@setOnClickListener
           else login(name, pw)
        }

    }

    fun checkInput(pw: String, username:String): Boolean{
        if(pw == "" && username == "")
        {
            binding.editTextPersonName.error = "Username must not be empty"
            binding.editTextPassword.error = "Password must not be empty"
            return false
        }
        else if(pw == "")
        {
            binding.editTextPassword.error = "Password must not be empty"
            return false
        }
        else if(username == "")
        {
            binding.editTextPersonName.error = "Username must not be empty"
            return false
        }
        else return true
    }

    fun login(username: String, password: String) {
        retrofit = RetrofitClient.getInstance(binding.root.context)
        val call = retrofit!!.create(UserService::class.java).login(loginRequest = LoginRequest(username, password))
        call.enqueue(object : Callback<JwtResponse> {
            override fun onResponse(call: Call<JwtResponse>, message: Response<JwtResponse>) {
                if (message.code() == 200) {
                    UserData.initialize(message.body()!!.getAccessToken()!!, User(message.body()!!.getUsername()!!))
                    val intent = Intent(binding.root.context, MainMenuActivity::class.java)
                    binding.root.context.startActivity(intent)
                }
                if(message.code() == 401)  Toast.makeText(binding.root.context, "username or password is wrong", Toast.LENGTH_SHORT).show()
            }
            override fun onFailure(call: Call<JwtResponse>, t: Throwable) {
                Toast.makeText(binding.root.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
