package com.e.skiapp.model

class User {

    private var username:String ?= null

    private var password: String? =null

    fun getUsername(): String {
        return username!!
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun setPassword(password: String) {
        this.password = password
    }

    constructor(username: String) {
        this.username = username
    }

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}