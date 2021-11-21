package com.e.skiapp.network.response

import java.util.*

class JwtResponse {

    private var accessToken: String? = null

    private var id: UUID? = null

    private var username: String? = null

    constructor(id: UUID, username: String, token: String) {
        this.id = id
        this.accessToken = token
        this.username
    }

    fun getAccessToken(): String? {
        return accessToken
    }

    fun setAccessToken(accessToken: String) {
        this.accessToken = accessToken
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