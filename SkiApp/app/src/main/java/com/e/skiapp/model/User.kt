package com.e.skiapp.model

class User {

    private var username:String ?= null

    fun getUsername(): String {
        return username!!
    }

    fun setUsername(username: String) {
        this.username = username
    }
}