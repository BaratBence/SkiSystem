package com.itsupport.skibackend.models;

import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;;
import java.util.UUID;

@Entity
public class ElevatorApplication {

    @Id
    @GeneratedValue
    private UUID Id;
    private int seatsNum;
    private String name;
    private boolean isOnline;
    private float utilization;
    private String address;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public int getSeatsNum() {
        return seatsNum;
    }

    public void setSeatsNum(int seatsNum) {
        this.seatsNum = seatsNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public float getUtilization() {
        return utilization;
    }

    public void setUtilization(float utilization) {
        this.utilization = utilization;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void update(@NotNull ElevatorApplication elevatorApplication){
        this.seatsNum = elevatorApplication.getSeatsNum();
        this.name = elevatorApplication.getName();
    }
}