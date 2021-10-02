package com.e.skiapp.view.fragments

import android.os.Bundle
import android.service.autofill.UserData
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.skiapp.R
import com.e.skiapp.adapter.ElevatorAdapter
import com.e.skiapp.databinding.FragmentElevatorBinding


class ElevatorFragment : Fragment() {

    private lateinit var binding: FragmentElevatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_elevator, container, false)
        val adapter = ElevatorAdapter(ArrayList(), binding)
        binding.rvelevators.adapter = adapter
        binding.rvelevators.layoutManager = LinearLayoutManager(binding.root.context)
        return binding.root
    }
}