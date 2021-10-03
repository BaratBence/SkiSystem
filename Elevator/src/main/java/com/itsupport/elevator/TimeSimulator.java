package com.itsupport.elevator;

import com.itsupport.elevator.elevator.ElevatorHandler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class TimeSimulator {
    //To simulate the different utilization of the Elevator
    @Scheduled(fixedDelay = 2000)
    private void simulateTime(){
        ElevatorHandler.getInstance().simulateUtilization();
    }
}
