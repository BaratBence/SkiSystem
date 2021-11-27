package com.e.skiapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.recyclerview.widget.RecyclerView
import com.e.skiapp.R
import com.e.skiapp.databinding.ElevatorRowBinding
import com.e.skiapp.databinding.FragmentElevatorBinding
import com.e.skiapp.model.ElevatorApplication
import com.e.skiapp.model.UserData
import com.e.skiapp.network.RetrofitClient
import com.e.skiapp.network.services.ElevatorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class ElevatorAdapter(private var elevatorApplications: ArrayList<ElevatorApplication>, private val fragmentElevatorBinding: FragmentElevatorBinding):RecyclerView.Adapter<ElevatorAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ElevatorRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(elevatorApplication: ElevatorApplication) {
            binding.elevator = elevatorApplication
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ElevatorRowBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(elevatorApplications[position])
        viewHolder.binding.textViewUsage.text = elevatorApplications[position].getUtilization().toString()
        viewHolder.binding.isOnlineCheckBox.isChecked = elevatorApplications[position].getIsOnline()
        viewHolder.binding.isOnlineCheckBox.setOnClickListener {
            if (viewHolder.binding.isOnlineCheckBox.isChecked) turnOn(viewHolder.binding, position)
            else turnOff(viewHolder.binding, position)
        }

    }

    override fun getItemCount() = elevatorApplications.size

    fun getData(): ArrayList<ElevatorApplication> {
        return elevatorApplications
    }

    fun updateData(updatedData: ArrayList<ElevatorApplication>) {
        elevatorApplications = ArrayList()
        var noDanger= false
        for(elevator in updatedData) {
            elevator.setUtilization(Math.round(elevator.getUtilization()*100).toFloat())
            if(elevator.getUtilization()>=90.0 && !UserData.getDanger()) {
                var builder = NotificationCompat.Builder(fragmentElevatorBinding.root.context, "notification").setContentTitle("Elevator is at dangerous usage").setSmallIcon(R.drawable.ic_baseline_elevator_24)
                    .setContentText("One of the elevators is in dangerous usage state").setPriority(NotificationCompat.PRIORITY_DEFAULT)
                var notificationManager = NotificationManagerCompat.from(fragmentElevatorBinding.root.context)
                notificationManager.notify(1, builder.build());
                UserData.setDanger(true)
                noDanger = true
            }
            elevatorApplications.add(elevator)
        }
        if(!noDanger) UserData.setDanger(false)

    }

    fun turnOn(binding: ElevatorRowBinding, position: Int) {
        var retrofit = RetrofitClient.getInstance(binding.root.context)
        val call = retrofit!!.create(ElevatorService::class.java).turnOn(elevatorApplications[position].getID()!!, "Bearer " + UserData.getToken())
        call.enqueue(object : Callback<ElevatorApplication> {
            override fun onResponse(call: Call<ElevatorApplication>, message: Response<ElevatorApplication>) {
                if (message.code() == 200) {
                    elevatorApplications[position].setIsOnline(true)
                }
            }
            override fun onFailure(call: Call<ElevatorApplication>, t: Throwable) {
                Toast.makeText(binding.root.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun turnOff(binding: ElevatorRowBinding, position: Int) {
        var retrofit = RetrofitClient.getInstance(binding.root.context)
        val call = retrofit!!.create(ElevatorService::class.java).turnOff(elevatorApplications[position].getID()!!, "Bearer " + UserData.getToken())
        call.enqueue(object : Callback<ElevatorApplication> {
            override fun onResponse(call: Call<ElevatorApplication>, message: Response<ElevatorApplication>) {
                if (message.code() == 200) {
                    elevatorApplications[position].setIsOnline(false)
                }
            }
            override fun onFailure(call: Call<ElevatorApplication>, t: Throwable) {
                Toast.makeText(binding.root.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}