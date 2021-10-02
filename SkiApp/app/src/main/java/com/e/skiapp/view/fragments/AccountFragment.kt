package com.e.skiapp.view.fragments

import android.os.Bundle
import android.service.autofill.UserData
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.e.skiapp.R
import com.e.skiapp.databinding.FragmentAccountBinding


class AccountFragment : Fragment() {

    private lateinit var binding: FragmentAccountBinding
    //private val retrofit: RetrofitClient = RetrofitClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_account,container,false)
        //binding.user = UserData.createUser()

        binding.saveButton.setOnClickListener{
            //if(checkInput())) return@setOnClickListener
            //retrofit.updateUser(binding.root.context,changeUserRequest,"Bearer " + UserData.getToken())
        }

        return binding.root
    }


    fun checkInput(username:String, password:String, passwordNew: String): Boolean {
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
        if(passwordNew == "")
        {
            binding.editTextPasswordAgainEdit.error = "New Password must not be empty"
            result = false
        }
        return result
    }

}