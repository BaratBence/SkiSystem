package com.itsupport.elevator.elevator;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class ElevatorAPIController {
    private final ElevatorHandler elevatorHandler;

    ElevatorAPIController() {
        this.elevatorHandler = ElevatorHandler.getInstance();
    }

    @GetMapping("/status")
    ResponseEntity<Elevator> getElevator(){
        return this.elevatorHandler.getElevatorStatus().map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/register")
    ResponseEntity<Elevator> registerElevator(){
        if (elevatorHandler.getElevatorStatus().isPresent()){
            return ResponseEntity.badRequest().build();
        }
        else {
            elevatorHandler.registerElevator();
            return ResponseEntity.ok().build();
        }
    }

    @PutMapping("/turnOff")
    ResponseEntity<Elevator> turnOffElevator() {
        if (elevatorHandler.getElevatorStatus().isPresent() && elevatorHandler.getElevatorStatus().get().isOnline()){
            elevatorHandler.turnOffElevator();
            return  ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/turnOn")
    ResponseEntity<Elevator> turnOnElevator() {
        if (elevatorHandler.getElevatorStatus().isPresent() && !elevatorHandler.getElevatorStatus().get().isOnline()){
            elevatorHandler.turnOnElevator();
            return  ResponseEntity.ok().build();
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
