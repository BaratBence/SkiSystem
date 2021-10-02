package com.e.skiapp.view.fragments

import android.content.Intent
import android.os.Bundle
import android.service.autofill.UserData
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.e.skiapp.R
import com.e.skiapp.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false)
        //binding.user = UserData.createUser()


        binding.manageButton.setOnClickListener {
            Navigation.findNavController(binding.root).navigate(R.id.elevatorFragment);
        }
        return binding.root
    }

}