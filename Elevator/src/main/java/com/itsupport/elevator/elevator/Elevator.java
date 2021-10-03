package com.itsupport.elevator.elevator;

import org.jetbrains.annotations.NotNull;

public class Elevator {
    private String id;
    private int numSeats;
    private boolean isOnline = false;

    Elevator(String id, int numSeats) {
        this.id = id;
        this.numSeats = numSeats;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getNumSeats() {
        return numSeats;
    }

    public void setNumSeats(int numSeats) {
        this.numSeats = numSeats;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void updateElevator(@NotNull Elevator elevator) {
        this.id = elevator.id;
        this.numSeats = elevator.numSeats;
    }
}