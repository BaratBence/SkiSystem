package com.e.skiapp.model

import java.util.*

class ElevatorApplication {

    private var name: String ?=null

    private var isOnline = false

    private var id: String ?= null

    private var utilization: Float = 0.0F

    constructor(id: String, name: String, isOnline: Boolean, utilization: Float) {
        this.id = id
        this.name = name
        this.isOnline = isOnline
        this.utilization  =utilization
    }

    fun getName(): String? {
        return name
    }

    fun setName(name: String) {
        this.name = name
    }

    fun getID(): String? {
        return id
    }

    fun setIsOnline(isOnline: Boolean) {
        this.isOnline = isOnline
    }

    fun getIsOnline(): Boolean {
        return isOnline
    }

    fun getUtilization(): Float {
        return utilization
    }

    //TODO: REMOVE
    fun setUtilization(utilization: Float) {
        this.utilization = utilization
    }

}