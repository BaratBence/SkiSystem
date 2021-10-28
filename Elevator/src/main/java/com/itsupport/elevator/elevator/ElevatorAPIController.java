package com.itsupport.elevator.elevator;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

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
    ResponseEntity<Elevator> registerElevator(@RequestBody Map<String, String>payload){
        System.out.println(payload.get("ID"));
        System.out.println(payload.get("IP"));
        System.out.println(payload.get("MaxUtil"));
        if (elevatorHandler.getElevatorStatus().isPresent()){
            return ResponseEntity.badRequest().build();
        }
        else {
            elevatorHandler.registerElevator(
                    UUID.fromString(payload.get("ID")),
                    payload.get("Address"),
                    Float.parseFloat(payload.get("MaxUtil")));
            return ResponseEntity.ok(elevatorHandler.getElevatorStatus().get());
        }
    }

    @PutMapping("/turnOff")
    ResponseEntity<Elevator> turnOffElevator() {
        if (elevatorHandler.getElevatorStatus().isPresent() && elevatorHandler.getElevatorStatus().get().isOnline()){
            elevatorHandler.turnOffElevator();
            return  ResponseEntity.ok(elevatorHandler.getElevatorStatus().get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/turnOn")
    ResponseEntity<Elevator> turnOnElevator() {
        if (elevatorHandler.getElevatorStatus().isPresent() && !elevatorHandler.getElevatorStatus().get().isOnline()){
            elevatorHandler.turnOnElevator();
            return  ResponseEntity.ok(elevatorHandler.getElevatorStatus().get());
        }
        else {
            return ResponseEntity.badRequest().build();
        }
    }
}
