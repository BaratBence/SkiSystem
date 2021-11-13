package com.e.skiapp.model

import java.util.*

class UserData {
    companion object {
        private var token: String? = null

        private var user: User? = null

        private var elevatorApplications: ArrayList<ElevatorApplication> = ArrayList()

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

        fun getElevatorApplications(): ArrayList<ElevatorApplication> {
            return elevatorApplications
        }

        fun updateElevators(elevators: ArrayList<ElevatorApplication>) {
            this.elevatorApplications = elevators
        }

    }
}