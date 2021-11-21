package com.e.skiapp.model

class User {

    private lateinit var username:String

    private lateinit var password: String

    fun getUsername(): String {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }

    fun setPassword(password: String) {
        this.password = password
    }

    fun getPassword(): String {
        return password
    }

    constructor(username: String) {
        this.username = username
    }

    constructor(username: String, password: String) {
        this.username = username
        this.password = password
    }
}