package com.itsupport.skibackend.frontendapi;

import com.itsupport.skibackend.elevatorapp.ElevatorApplication;
import com.itsupport.skibackend.elevatorapp.persistence.*;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.*;

@RestController
@RequestMapping("/api/elevators")
public class FrontendAPIController {

    private final ElevatorAppRepository elevatorAppRepository;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    FrontendAPIController(ElevatorAppRepository elevatorAppRepository){
        this.elevatorAppRepository = elevatorAppRepository;
    }

    @GetMapping()
    ResponseEntity<List<ElevatorApplication>> getAllElevatorApplication(){
        List<ElevatorApplication> foundElevatorApplications = elevatorAppRepository.findAll();
        if(!foundElevatorApplications.isEmpty()){
            return ResponseEntity.ok(foundElevatorApplications);
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/{id}")
    ResponseEntity<ElevatorApplication> getElevatorApplicationById(@PathVariable UUID id){
        Optional<ElevatorApplication> foundElevatorApplication = elevatorAppRepository.findById(id);
        return foundElevatorApplication.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/add")
    ResponseEntity<ElevatorApplication> addElevatorApplication(@RequestBody ElevatorApplication newElevatorApplication) {
        UriComponents url = UriComponentsBuilder.fromUriString(newElevatorApplication.getAddress().toUriString()).build();
        System.out.println(url);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> httpEntity = new HttpEntity<>(httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.postForEntity(url.toString(), String.class, String.class);
        System.out.println(response.getStatusCode());
        System.out.println("String   " + newElevatorApplication.getAddress().toString());
        System.out.println("Host:   " + newElevatorApplication.getAddress().getHost());
        System.out.println("URIString   " + newElevatorApplication.getAddress().toUriString());
        System.out.println("TO URI" + newElevatorApplication.getAddress().toUri());


        UUID newElevatorApplicationId = elevatorAppRepository.save(newElevatorApplication).getId();
        return ResponseEntity.created(URI.create("" + newElevatorApplicationId)).build();
    }

    @PutMapping("/{id}/update")
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
            //TODO:turn the app off
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
            //TODO:turn the app off
            return ResponseEntity.ok(elevatorAppRepository.save(foundElevatorApplication.get()));
        }
        else {
            return ResponseEntity.notFound().build();
        }
    }

}
