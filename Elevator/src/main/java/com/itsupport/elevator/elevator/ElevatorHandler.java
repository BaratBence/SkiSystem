package com.itsupport.elevator.elevator;

import com.itsupport.elevator.security.RestTemplateConfig;
import com.itsupport.elevator.services.BackendCommunicationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.Random;
import java.util.UUID;

public class ElevatorHandler {

    private static ElevatorHandler elevatorHandler = null;
    private static float MAX_ELEVATOR_UTILIZATION = 0.0f;
    public static String BACKEND_ADDRESS = "";
    public static UUID ID = null;
    private Elevator elevator = null;
    @Autowired
    private BackendCommunicationHandler backendCommunicationHandler;

    private ElevatorHandler() {
        backendCommunicationHandler = new BackendCommunicationHandler(new RestTemplate());
    }

    public static ElevatorHandler getInstance() {
        if(elevatorHandler == null){
            elevatorHandler = new ElevatorHandler();
        }
        return elevatorHandler;
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
