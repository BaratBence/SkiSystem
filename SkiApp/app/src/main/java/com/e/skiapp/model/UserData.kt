package com.e.skiapp.model

import java.util.*

class UserData {
    companion object {
        private var token: String? = null

        private var user: User? = null

        private var danger = false

        fun getToken(): String? {
            return token
        }

        fun initialize(token: String, user: User) {
            this.token = token
            this.user = user
        }

        fun createUser(): User? {
            return user
        }

        fun setDanger(danger: Boolean) {
            this.danger = danger
        }

        fun getDanger(): Boolean {
            return danger
        }

    }
}