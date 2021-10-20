package com.itsupport.elevator.elevator;

import java.util.Optional;
import java.util.Random;

public class ElevatorHandler {

    private static ElevatorHandler elevatorHandler = null;
    private Elevator elevator = null;

    private ElevatorHandler() {}

    public static ElevatorHandler getInstance() {
        if(elevatorHandler == null){
            elevatorHandler = new ElevatorHandler();
        }
        return elevatorHandler;
    }

    public Optional<Elevator> getElevatorStatus(){
        return Optional.ofNullable(elevator);
    }

    public void registerElevator(){
        this.elevator = new Elevator();
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
            this.elevator.setUtilization(new Random().nextFloat());
        }
    }
}
