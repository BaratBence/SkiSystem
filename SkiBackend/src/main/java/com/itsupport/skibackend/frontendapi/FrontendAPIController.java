package com.itsupport.skibackend.frontendapi;

import com.itsupport.skibackend.elevatorapp.ElevatorApplication;
import com.itsupport.skibackend.elevatorapp.persistence.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class FrontendAPIController {

    private final ElevatorAppRepository elevatorAppRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    FrontendAPIController(ElevatorAppRepository elevatorAppRepository){
        this.elevatorAppRepository = elevatorAppRepository;
    }

    @GetMapping("/elevators")
    List<ElevatorApplication> getAllElevatorApplication(){
        return elevatorAppRepository.findAll();
    }

    @GetMapping("/elevators/{id}")
    ResponseEntity<ElevatorApplication> getElevatorApplicationById(@PathVariable UUID id){
        Optional<ElevatorApplication> foundElevatorApplication = elevatorAppRepository.findById(id);
        return foundElevatorApplication.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

}
