package com.itsupport.elevator.services;

import com.itsupport.elevator.models.Elevator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class ElevatorHandler {

    public static float MAX_ELEVATOR_UTILIZATION = 0.0f;
    public static String BACKEND_ADDRESS = "";
    public static UUID ID = null;
    private Elevator elevator = null;
    @Autowired
    private BackendCommunicationHandler backendCommunicationHandler;

    public float getMAX_ELEVATOR_UTILIZATION() {
        return MAX_ELEVATOR_UTILIZATION;
    }

    public void setMAX_ELEVATOR_UTILIZATION(float MAX_ELEVATOR_UTILIZATION) {
        this.MAX_ELEVATOR_UTILIZATION = MAX_ELEVATOR_UTILIZATION;
    }

    public String getBACKEND_ADDRESS() {
        return BACKEND_ADDRESS;
    }

    public void setBACKEND_ADDRESS(String BACKEND_ADDRESS) {
        this.BACKEND_ADDRESS = BACKEND_ADDRESS;
    }

    public UUID getID() {
        return ID;
    }

    public void setID(UUID ID) {
        this.ID = ID;
    }

    public Elevator getElevator() {
        return elevator;
    }

    public void setElevator(Elevator elevator) {
        this.elevator = elevator;
    }

    ElevatorHandler(BackendCommunicationHandler backendCommunicationHandler) {
        this.backendCommunicationHandler = backendCommunicationHandler;
    }

    public Optional<Elevator> getElevatorStatus(){
        return Optional.ofNullable(elevator);
    }

    public void registerElevator(UUID id, String address, float maxUtil){
        this.elevator = new Elevator();
        ID = id;
        BACKEND_ADDRESS = address;
        MAX_ELEVATOR_UTILIZATION = maxUtil;
    }

    public void turnOffElevator(){
        if (this.elevator.isOnline()) {
            this.elevator.setOnline(false);
            this.elevator.setUtilization(0.0f);
        }
    }

    public void turnOnElevator(){
        this.elevator.setOnline(true);
    }

    public void simulateUtilization(){
        if(this.elevator != null && this.elevator.isOnline()) {
            float newUtilizationValue = new Random().nextFloat();
            this.elevator.setUtilization(newUtilizationValue);
            if(newUtilizationValue >= MAX_ELEVATOR_UTILIZATION){
                backendCommunicationHandler.notifyBackend(BACKEND_ADDRESS, this.elevator);
            }
        }
    }
}
