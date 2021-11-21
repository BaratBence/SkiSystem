package com.e.skiapp.view.fragments

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.NotificationManager.IMPORTANCE_DEFAULT
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.e.skiapp.R
import com.e.skiapp.adapter.ElevatorAdapter
import com.e.skiapp.databinding.FragmentElevatorBinding
import com.e.skiapp.model.ElevatorApplication

import com.e.skiapp.network.RetrofitClient

import com.e.skiapp.network.services.ElevatorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*


class ElevatorFragment : Fragment() {

    private lateinit var binding: FragmentElevatorBinding
    private var retrofit: Retrofit?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_elevator, container, false)
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel("notification", "notification",IMPORTANCE_DEFAULT)
            val notificationManager: NotificationManager = getSystemService(binding.root.context, NotificationManager::class.java) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }

        val adapter = ElevatorAdapter(ArrayList<ElevatorApplication>(), binding)
        getAll(adapter)
        binding.rvelevators.adapter = adapter
        binding.rvelevators.layoutManager = LinearLayoutManager(binding.root.context)

        val mainHandler = Handler(Looper.getMainLooper())

        mainHandler.post(object : Runnable {
            override fun run() {
                getAll(adapter)
                activity?.runOnUiThread(java.lang.Runnable {
                    adapter.notifyDataSetChanged()
                })
                mainHandler.postDelayed(this, 10000)
            }
        })

        binding.swipeToRefreshLayout.setOnRefreshListener {
            getAll(adapter)
            binding.swipeToRefreshLayout.isRefreshing = false
        }

        return binding.root
    }

    fun getAll(adapter: ElevatorAdapter) {
        retrofit = RetrofitClient.getInstance(binding.root.context)
        val call = retrofit!!.create(ElevatorService::class.java).getAll()
        call.enqueue(object : Callback<List<ElevatorApplication>> {
            override fun onResponse(call: Call<List<ElevatorApplication>>, message: Response<List<ElevatorApplication>>) {
                if (message.code() == 200) {
                    adapter.updateData(message.body()!! as ArrayList<ElevatorApplication>)
                    adapter.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<List<ElevatorApplication>>, t: Throwable) {
                Toast.makeText(binding.root.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }


}