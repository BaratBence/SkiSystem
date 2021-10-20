package com.itsupport.elevator.elevator;

public class Elevator {
    private boolean isOnline = false;
    private float utilization = 0.0f;

    Elevator(){}

    Elevator(boolean isOnline, float utilization){
        this.isOnline = isOnline;
        this.utilization = utilization;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean value) {
        this.isOnline = value;
    }

    public float getUtilization() {
        return utilization;
    }

    public void setUtilization(float utilization) {
        this.utilization = utilization;
    }
}