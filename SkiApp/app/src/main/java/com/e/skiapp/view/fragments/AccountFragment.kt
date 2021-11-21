package com.e.skiapp.view.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.e.skiapp.R
import com.e.skiapp.databinding.FragmentAccountBinding
import com.e.skiapp.model.User
import com.e.skiapp.model.UserData
import com.e.skiapp.network.RetrofitClient
import com.e.skiapp.network.requests.LoginRequest
import com.e.skiapp.network.response.JwtResponse
import com.e.skiapp.network.services.UserService
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    private var retrofit: Retrofit ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account,container,false)
        binding.user = UserData.createUser()

        binding.saveButton.setOnClickListener{
            val userName = binding.editTextUserNameEdit.text.toString()
            val password= binding.editTextPasswordEdit.text.toString()
            if(!checkInput(userName,password)) return@setOnClickListener
            update(userName, password)
        }
        return binding.root
    }


    fun checkInput(username:String, password:String): Boolean {
        var result = true
        if(username == "") {
            binding.editTextUserNameEdit.error = "Username must be not be empty"
            result = false
        }
        if(password == "")
        {
            binding.editTextPasswordEdit.error = "Password must not be empty"
            result = false
        }
        return result
    }

    fun update(userName: String, password: String) {
        retrofit = RetrofitClient.getInstance(binding.root.context)
        val call = retrofit!!.create(UserService::class.java).update(user = User(userName, password), "Bearer " + UserData.getToken() )
       // System.out.println(call.body.getUser().)
        call.enqueue(object : Callback<JwtResponse> {
            override fun onResponse(call: Call<JwtResponse>, message: Response<JwtResponse>) {
                if (message.code() == 200) {
                    if(message.body()!!.getAccessToken()!! == "") UserData.initialize(UserData.getToken()!!, User(message.body()!!.getUsername()!!))
                    else UserData.initialize(message.body()!!.getAccessToken()!!, User(message.body()!!.getUsername()!!))
                    Toast.makeText(binding.root.context, "Successful update", Toast.LENGTH_SHORT).show()
                }
            }
            override fun onFailure(call: Call<JwtResponse>, t: Throwable) {
                Toast.makeText(binding.root.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}