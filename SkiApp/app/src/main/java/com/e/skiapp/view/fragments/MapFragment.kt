package com.e.skiapp.view.fragments

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.e.skiapp.R
import com.e.skiapp.databinding.FragmentMapBinding
import com.e.skiapp.model.ElevatorApplication
import com.e.skiapp.network.RetrofitClient
import com.e.skiapp.network.services.ElevatorService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.*

class MapFragment : Fragment() {
    private lateinit var binding: FragmentMapBinding
    private var retrofit: Retrofit?=null
    private lateinit var canvas:Canvas
    private var elevatorList = ArrayList<ElevatorApplication>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_map, container, false)
        val bitmap: Bitmap = Bitmap.createBitmap(1080, 1920, Bitmap.Config.ARGB_8888)
        canvas = Canvas(bitmap)

        val mainHandler = Handler(Looper.getMainLooper())
        mainHandler.post(object : Runnable {
            override fun run() {
                getAll()
                binding.imageV.background = BitmapDrawable(getResources(), bitmap)
                activity?.runOnUiThread(java.lang.Runnable {

                })
                mainHandler.postDelayed(this, 10000)
            }
        })

        binding.imageV.background = BitmapDrawable(getResources(), bitmap)
        return binding.root
    }

    private fun drawElevator(startX: Float, startY: Float, endX: Float, endY: Float, utilization: Float, name: String) {
        val paint = Paint()
        paint.color = colorCode(utilization)
        paint.strokeWidth = 7F
        canvas.drawLine(startX, startY, endX, endY, paint)
        canvas.drawCircle(startX, startY, 20f, paint)
        canvas.drawCircle(endX, endY, 20f, paint)
        paint.textSize = 40f
        canvas.drawText(name, (startX + endX) / 2, (endY + startY) / 2, paint)
    }

    private fun colorCode(utilization: Float): Int {
        if(utilization==0F) return Color.WHITE
        else if(utilization>0 && utilization<25.0) return Color.GREEN
        else if(utilization>=25.0 && utilization<50.0) return Color.YELLOW
        else if(utilization>=50.0 && utilization<75.0) return Color.MAGENTA
        else return Color.RED
    }

    private fun getAll() {
        retrofit = RetrofitClient.getInstance(binding.root.context)
        val call = retrofit!!.create(ElevatorService::class.java).getAll()
        call.enqueue(object : Callback<List<ElevatorApplication>> {
            override fun onResponse(call: Call<List<ElevatorApplication>>, message: Response<List<ElevatorApplication>>) {
                if (message.code() == 200) {
                    parseData(message.body()!! as ArrayList<ElevatorApplication>)
                }
            }
            override fun onFailure(call: Call<List<ElevatorApplication>>, t: Throwable) {
                Toast.makeText(binding.root.context, t.message, Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun parseData(elevators: ArrayList<ElevatorApplication>) {
        elevatorList = ArrayList<ElevatorApplication>()
        for(elevator in elevators) {
            drawElevator(elevator.getStartX(), elevator.getStartY(), elevator.getEndX(), elevator.getEndY(),elevator.getUtilization()*100, elevator.getName()!!)
            elevatorList.add(elevator)
        }
    }

}