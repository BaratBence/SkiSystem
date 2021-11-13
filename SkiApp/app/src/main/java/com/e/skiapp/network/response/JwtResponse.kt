package com.e.skiapp.network.response

import java.util.*

class JwtResponse {

    private var token: String? = null

    private var id: UUID? = null

    private var username: String? = null

    fun JwtResponse(id: UUID, username: String, token: String) {
        this.id = id
        this.token = token
        this.username
    }

    fun getToken(): String? {
        return token
    }

    fun setToken(accessToken: String) {
        token = accessToken
    }

    fun getId(): UUID? {
        return id
    }

    fun setId(id: UUID) {
        this.id = id
    }

    fun getUsername(): String? {
        return username
    }

    fun setUsername(username: String) {
        this.username = username
    }


}