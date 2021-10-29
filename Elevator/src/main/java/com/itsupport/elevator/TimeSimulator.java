package com.itsupport.elevator;

import com.itsupport.elevator.services.ElevatorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeSimulator {
    @Autowired
    private ElevatorHandler elevatorHandler;

    //To simulate the different utilization of the Elevator
    @Scheduled(fixedDelay = 10000)
    private void simulateTime(){
        this.elevatorHandler.simulateUtilization();
    }
}
