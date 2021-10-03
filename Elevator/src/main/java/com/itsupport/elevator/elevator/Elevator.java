package com.itsupport.elevator.elevator;

import org.jetbrains.annotations.NotNull;

public class Elevator {
    private final String id;
    private int seatsNum;
    private boolean isOnline = false;

    Elevator(String id, int seatsNum) {
        this.id = id;
        this.seatsNum = seatsNum;
    }

    public String getId() {
        return id;
    }

    public int getSeatsNum() {
        return seatsNum;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public void updateElevator(@NotNull Elevator elevator) {
        this.seatsNum = elevator.seatsNum;
    }
}