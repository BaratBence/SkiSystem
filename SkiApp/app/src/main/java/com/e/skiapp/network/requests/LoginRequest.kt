package com.e.skiapp.network.requests

class LoginRequest constructor (username: String, password: String) {

    private var username: String? = null

    private var password: String? = null


    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun getPassword(): String? {
        return password
    }

    fun setPassword(password: String) {
        this.password = password
    }
}