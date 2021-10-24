package com.itsupport.skibackend.services;


import com.itsupport.elevator.elevator.Elevator;
import com.itsupport.skibackend.models.ElevatorApplicationModel;
import com.itsupport.skibackend.models.persistence.ElevatorAppRepository;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ElevatorCallbackHandler {

    private final ElevatorAppRepository elevatorAppRepository;
    private final ClientCommunicationHandler clientCommunicationHandler;
    private final ElevatorCommunicationHandler elevatorCommunicationHandler;

    public ElevatorCallbackHandler(ElevatorAppRepository elevatorAppRepository,
                                   ClientCommunicationHandler clientCommunicationHandler,
                                   ElevatorCommunicationHandler elevatorCommunicationHandler) {
        this.elevatorAppRepository = elevatorAppRepository;
        this.clientCommunicationHandler = clientCommunicationHandler;
        this.elevatorCommunicationHandler = elevatorCommunicationHandler;
    }

    public ResponseEntity<?> handleExceptionalEvent(UUID faultyElevatorAppID, @NotNull Elevator faultyElevator) {
        if(faultyElevator.getUtilization() > 0.9 && Boolean.getBoolean("${com.itsupport.UserInterventionAtExceptionalEvent}")){
            clientCommunicationHandler.pushMessage(faultyElevatorAppID, faultyElevator);
            return ResponseEntity.ok().build();
        }
        else if (faultyElevator.getUtilization() > 0.9 && !Boolean.getBoolean("${com.itsupport.UserInterventionAtExceptionalEvent}")){
            ElevatorApplicationModel faultyElevatorApplicationModel = elevatorAppRepository.getById(faultyElevatorAppID);
            Elevator updatedElevator = elevatorCommunicationHandler.turnOffElevator(faultyElevatorApplicationModel.getAddress());
            elevatorAppRepository.save(faultyElevatorApplicationModel.refreshElevatorStats(updatedElevator));
        }
        return ResponseEntity.ok().build();
    }
}
