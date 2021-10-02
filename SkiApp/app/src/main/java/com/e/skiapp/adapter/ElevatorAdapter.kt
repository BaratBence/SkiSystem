package com.e.skiapp.adapter

import android.service.autofill.UserData
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.e.skiapp.databinding.ElevatorRowBinding
import com.e.skiapp.databinding.FragmentElevatorBinding
import com.e.skiapp.model.Elevator

class ElevatorAdapter(private val elevators: ArrayList<Elevator>, private val fragmentElevatorBinding: FragmentElevatorBinding):RecyclerView.Adapter<ElevatorAdapter.ViewHolder>() {
    inner class ViewHolder(val binding: ElevatorRowBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(elevator: Elevator) {
            binding.elevator = elevator
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val binding = ElevatorRowBinding.inflate(LayoutInflater.from(viewGroup.context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(elevators[position])
        //TODO:ADD LOGIC
    }

    override fun getItemCount() = elevators.size

}