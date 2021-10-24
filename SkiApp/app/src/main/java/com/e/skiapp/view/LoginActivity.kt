package com.e.skiapp.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.e.skiapp.R
import com.e.skiapp.databinding.ActivityLoginBinding
import com.e.skiapp.network.RetrofitClient

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val retrofit: RetrofitClient = RetrofitClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login) as ActivityLoginBinding


        binding.loginbutton.setOnClickListener {
            retrofit.login(this,  binding.editTextPersonName.text.toString(), binding.editTextPersonName.text.toString())
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
}
