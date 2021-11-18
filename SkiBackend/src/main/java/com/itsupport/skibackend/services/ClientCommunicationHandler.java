package com.itsupport.skibackend.services;

import com.itsupport.elevator.models.Elevator;
import org.hibernate.cfg.NotYetImplementedException;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ClientCommunicationHandler {
    public void pushMessage(UUID faultyElevatorAppID, Elevator faultyElevator) {
        throw new NotYetImplementedException();
    }
}
