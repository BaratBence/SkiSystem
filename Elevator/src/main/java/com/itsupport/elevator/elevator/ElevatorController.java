package com.itsupport.elevator.elevator;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class ElevatorController {
    private Elevator elevator = null;

    @GetMapping("/status")
    ResponseEntity<Elevator> getElevator(){
        return this.elevator != null ? ResponseEntity.ok(this.elevator) : ResponseEntity.notFound().build();
    }

    @PostMapping("/register")
    ResponseEntity<Elevator> registerElevator(@RequestBody Elevator elevator){
        if(this.elevator != null){
            return ResponseEntity.badRequest().build();
        }else{
            this.elevator = new Elevator(elevator.getId(), elevator.getSeatsNum());
            return  ResponseEntity.created(URI.create("/get")).body(this.elevator);
        }
    }

    @PutMapping("/update")
    ResponseEntity<Elevator> updateElevator(@RequestBody Elevator elevator){
        if(this.elevator != null){
            this.elevator.updateElevator(elevator);
            return  ResponseEntity.ok(this.elevator);
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/turnOff")
    ResponseEntity<Elevator> turnOffElevator() {
        if(this.elevator != null){
            if(this.elevator.isOnline()){
                this.elevator.setOnline(false);
                return  ResponseEntity.ok().build();
            }
            else{
                return ResponseEntity.badRequest().build();
            }
        }else{
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/turnOn")
    ResponseEntity<Elevator> turnOnElevator() {
        if(this.elevator != null){
            if(this.elevator.isOnline()){
                return ResponseEntity.badRequest().build();
            }
            else{
                this.elevator.setOnline(true);
                return  ResponseEntity.ok().build();
            }
        }else{
            return ResponseEntity.badRequest().build();
        }
    }
}
