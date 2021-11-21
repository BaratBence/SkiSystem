package com.e.skiapp.network.requests

class LoginRequest {

    private var username: String? = null

    private var password: String? = null

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }

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