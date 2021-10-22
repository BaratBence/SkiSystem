package com.itsupport.skibackend.frontendapi;

import com.itsupport.elevator.elevator.Elevator;
import com.itsupport.skibackend.models.ElevatorApplication;
import com.itsupport.skibackend.models.persistence.*;

import com.itsupport.skibackend.communication.ElevatorCommunicationHandler;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/elevators")
public class ElevatorController {

    private final ElevatorAppRepository elevatorAppRepository;
    private final ElevatorCommunicationHandler elevatorConnectionHandler;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    ElevatorController(ElevatorAppRepository elevatorAppRepository, ElevatorCommunicationHandler elevatorConnectionHandler){
        this.elevatorAppRepository = elevatorAppRepository;
        this.elevatorConnectionHandler = elevatorConnectionHandler;
    }

    @GetMapping()
    ResponseEntity<List<ElevatorApplication>> getAllElevatorApplication(){
        List<ElevatorApplication> foundElevatorApplications = elevatorAppRepository.findAll();
        if(!foundElevatorApplications.isEmpty()){
            for(ElevatorApplication elevatorApplication : foundElevatorApplications){
                Elevator tmpElevator = elevatorConnectionHandler.getElevatorStatus(elevatorApplication.getAddress());
                elevatorApplication.setOnline(tmpElevator.isOnline());
                elevatorApplication.setUtilization(tmpElevator.getUtilization());
            }
            return ResponseEntity.ok(foundElevatorApplications);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<ElevatorApplication> getElevatorApplicationById(@PathVariable UUID id){
        Optional<ElevatorApplication> foundElevatorApplication = elevatorAppRepository.findById(id);
        if (foundElevatorApplication.isPresent()){
            Elevator tmpElevator = elevatorConnectionHandler.getElevatorStatus(foundElevatorApplication.get().getAddress());
            foundElevatorApplication.get().setOnline(tmpElevator.isOnline());
            foundElevatorApplication.get().setUtilization(tmpElevator.getUtilization());
            return ResponseEntity.ok(foundElevatorApplication.get());
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<ElevatorApplication> addElevatorApplication(@RequestBody @NotNull ElevatorApplication newElevatorApplication) {
        Elevator newElevator = elevatorConnectionHandler.registerNewElevator(newElevatorApplication.getAddress());
        newElevatorApplication.setOnline(newElevator.isOnline());
        newElevatorApplication.setUtilization(newElevator.getUtilization());
        UUID newElevatorApplicationId = elevatorAppRepository.save(newElevatorApplication).getId();
        return ResponseEntity.created(URI.create("" + newElevatorApplicationId)).build();
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<ElevatorApplication> updateElevatorApplication(@PathVariable UUID id, @RequestBody ElevatorApplication newElevatorApplication) {
        Optional<ElevatorApplication> foundElevatorApplication = elevatorAppRepository.findById(id);
        if(foundElevatorApplication.isPresent()){
            foundElevatorApplication.get().update(newElevatorApplication);
            return ResponseEntity.ok(elevatorAppRepository.save(foundElevatorApplication.get()));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/turnOff")
    ResponseEntity<ElevatorApplication> turnOffElevatorApplication(@PathVariable UUID id) {
        Optional<ElevatorApplication> foundElevatorApplication = elevatorAppRepository.findById(id);
        if(foundElevatorApplication.isPresent()){
            Elevator tmpElevator = elevatorConnectionHandler.turnOffElevator(foundElevatorApplication.get().getAddress());
            foundElevatorApplication.get().setOnline(tmpElevator.isOnline());
            foundElevatorApplication.get().setUtilization(tmpElevator.getUtilization());
            return ResponseEntity.ok(elevatorAppRepository.save(foundElevatorApplication.get()));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/turnOn")
    ResponseEntity<ElevatorApplication> turnOnElevatorApplication(@PathVariable UUID id) {
        Optional<ElevatorApplication> foundElevatorApplication = elevatorAppRepository.findById(id);
        if(foundElevatorApplication.isPresent()){
            Elevator tmpElevator = elevatorConnectionHandler.turnOnElevator(foundElevatorApplication.get().getAddress());
            foundElevatorApplication.get().setOnline(tmpElevator.isOnline());
            foundElevatorApplication.get().setUtilization(tmpElevator.getUtilization());
            return ResponseEntity.ok(elevatorAppRepository.save(foundElevatorApplication.get()));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
