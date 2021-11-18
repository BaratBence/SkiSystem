package com.itsupport.skibackend.backendapi;

import com.itsupport.elevator.models.Elevator;
import com.itsupport.skibackend.services.ElevatorCallbackHandler;
import com.itsupport.skibackend.models.ElevatorApplicationModel;
import com.itsupport.skibackend.models.persistence.ElevatorAppRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/callback")
public class ElevatorCallbackController {

    private final ElevatorAppRepository elevatorAppRepository;
    private final ElevatorCallbackHandler elevatorCallbackHandler;

    public ElevatorCallbackController(ElevatorAppRepository elevatorAppRepository,
                                      ElevatorCallbackHandler elevatorCallbackHandler) {
        this.elevatorAppRepository = elevatorAppRepository;
        this.elevatorCallbackHandler = elevatorCallbackHandler;
    }

    @PutMapping("/{faultyElevatorAppID}/exceptional")
    ResponseEntity<?> exceptionalElevatorEvent(@PathVariable UUID faultyElevatorAppID,
                                               @RequestBody Elevator faultyElevator){
        Optional<ElevatorApplicationModel> faultyElevatorApplication = elevatorAppRepository.findById(faultyElevatorAppID);
        if (faultyElevatorApplication.isPresent()){
            System.out.println("CallBack received!!");
            return elevatorCallbackHandler.handleExceptionalEvent(faultyElevatorAppID, faultyElevator);
        } else
        {
            return ResponseEntity.notFound().build();
        }
    }
}
