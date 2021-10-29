package com.itsupport.elevator.controllers;


import com.itsupport.elevator.models.Elevator;
import com.itsupport.elevator.services.ElevatorHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ElevatorAPIController {
    @Autowired
    private ElevatorHandler elevatorHandler;

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
