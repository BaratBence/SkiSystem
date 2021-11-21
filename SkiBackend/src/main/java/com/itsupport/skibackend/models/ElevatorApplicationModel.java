package com.itsupport.skibackend.models;

import com.itsupport.elevator.models.Elevator;
import org.hibernate.annotations.Type;
import org.jetbrains.annotations.NotNull;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;;
import java.util.UUID;

@Entity
public class ElevatorApplicationModel {

    @Id
    @GeneratedValue
    @Type(type="uuid-char")
    private UUID Id;
    private int seatsNum;
    private String name;
    private boolean isOnline;
    private float utilization;
    private String address;

    private float startX;
    private float startY;
    private float endX;
    private float endY;

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

    public void update(@NotNull ElevatorApplicationModel elevatorApplicationModel){
        this.seatsNum = elevatorApplicationModel.getSeatsNum();
        this.name = elevatorApplicationModel.getName();
    }

    public ElevatorApplicationModel refreshElevatorStats(@NotNull Elevator elevator){
        this.utilization = elevator.getUtilization();
        this.isOnline = elevator.isOnline();
        return this;
    }

    public void setStartX(float startX) {
        this.startX = startX;
    }

    public float getStartX() {
        return startX;
    }

    public void setStartY(float startY) {
        this.startY = startY;
    }

    public float getStartY() {
        return startY;
    }

    public void setEndX(float endX) {
        this.endX = endX;
    }

    public float getEndX() {
        return endX;
    }

    public void setEndY(float endY) {
        this.endY = endY;
    }

    public float getEndY() {
        return endY;
    }
}