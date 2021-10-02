package com.e.skiapp.model

class Elevator {

    private var name: String ?=null

    fun getName(): String {
        return name!!
    }

    fun setName(name: String) {
        this.name = name
    }
}