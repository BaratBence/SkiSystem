package com.itsupport.skibackend.frontendapi;

import com.itsupport.elevator.models.Elevator;
import com.itsupport.skibackend.models.ElevatorApplicationModel;
import com.itsupport.skibackend.models.persistence.*;

import com.itsupport.skibackend.services.ElevatorCommunicationHandler;
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
    ResponseEntity<List<ElevatorApplicationModel>> getAllElevatorApplication(){
        List<ElevatorApplicationModel> foundElevatorApplicationModels = elevatorAppRepository.findAll();
        if(!foundElevatorApplicationModels.isEmpty()){
            for(ElevatorApplicationModel elevatorApplicationModel : foundElevatorApplicationModels){
                Elevator tmpElevator = elevatorConnectionHandler.getElevatorStatus(elevatorApplicationModel.getAddress());
                elevatorApplicationModel.setOnline(tmpElevator.isOnline());
                elevatorApplicationModel.setUtilization(tmpElevator.getUtilization());
            }
            return ResponseEntity.ok(foundElevatorApplicationModels);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<ElevatorApplicationModel> getElevatorApplicationById(@PathVariable UUID id){
        Optional<ElevatorApplicationModel> foundElevatorApplication = elevatorAppRepository.findById(id);
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
    ResponseEntity<ElevatorApplicationModel> addElevatorApplication(@RequestBody @NotNull ElevatorApplicationModel newElevatorApplicationModel) {
        ElevatorApplicationModel newElevatorApplication = elevatorAppRepository.save(newElevatorApplicationModel);
        newElevatorApplication.setOnline(false);
        newElevatorApplication.setUtilization(0.0f);
        Elevator newElevator = elevatorConnectionHandler.registerNewElevator(newElevatorApplication);
        return ResponseEntity.created(URI.create("")).build();
    }

    @PutMapping("/{id}/update")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    ResponseEntity<ElevatorApplicationModel> updateElevatorApplication(@PathVariable UUID id, @RequestBody ElevatorApplicationModel newElevatorApplicationModel) {
        Optional<ElevatorApplicationModel> foundElevatorApplication = elevatorAppRepository.findById(id);
        if(foundElevatorApplication.isPresent()){
            foundElevatorApplication.get().update(newElevatorApplicationModel);
            return ResponseEntity.ok(elevatorAppRepository.save(foundElevatorApplication.get()));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/turnOff")
    ResponseEntity<ElevatorApplicationModel> turnOffElevatorApplication(@PathVariable UUID id) {
        Optional<ElevatorApplicationModel> foundElevatorApplication = elevatorAppRepository.findById(id);
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
    ResponseEntity<ElevatorApplicationModel> turnOnElevatorApplication(@PathVariable UUID id) {
        Optional<ElevatorApplicationModel> foundElevatorApplication = elevatorAppRepository.findById(id);
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
