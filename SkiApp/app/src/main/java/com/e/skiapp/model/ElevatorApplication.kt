package com.e.skiapp.model


class ElevatorApplication {

    private var name: String ?=null

    private var online = false

    private var id: String ?= null

    private var utilization: Float = 0.0F

    private var startX = 0f
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f

    constructor(id: String, name: String, isOnline: Boolean, utilization: Float) {
        this.id = id
        this.name = name
        this.online = isOnline
        this.utilization  =utilization
    }

    constructor(id: String, name: String, isOnline: Boolean , utilization: Float, startX: Float, startY: Float, endX: Float, endY: Float) {
        this.name = name
        this.online = isOnline
        this.id = id
        this.utilization = utilization
        this.startX = startX
        this.startY = startY
        this.endX = endX
        this.endY = endY
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
        this.online = isOnline
    }

    fun getIsOnline(): Boolean {
        return online
    }

    fun getUtilization(): Float {
        return utilization
    }

    fun setStartX(startX: Float) {
        this.startX = startX
    }

    fun getStartX(): Float {
        return startX.toFloat()
    }

    fun setStartY(startY: Float) {
        this.startY = startY
    }

    fun getStartY(): Float {
        return startY.toFloat()
    }

    fun setEndX(endX: Float) {
        this.endX = endX
    }

    fun getEndX(): Float {
        return endX.toFloat()
    }

    fun setEndY(endY: Float) {
        this.endY = endY
    }

    fun getEndY(): Float {
        return endY.toFloat()
    }

    //TODO: REMOVE
    fun setUtilization(utilization: Float) {
        this.utilization = utilization
    }

}