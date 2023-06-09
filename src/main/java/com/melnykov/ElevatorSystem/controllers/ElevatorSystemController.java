package com.melnykov.ElevatorSystem.controllers;

import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ElevatorSystemController {
    @Autowired
    private ElevatorSystem elevatorSystem;

    @Autowired
    private WebController webController;

    @PostMapping("/pick-up")
    public void pickUp(@RequestParam int requestedFloor, @RequestParam int direction) {
        elevatorSystem.pickup(requestedFloor, direction);
    }

    @PostMapping("/step")
    public ResponseEntity step() {
        elevatorSystem.step();
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/elevatorSystemInfo")
    public ResponseEntity<int[][]> getElevatorControllerInfo() {
        return ResponseEntity.ok(elevatorSystem.status());
    }

    @PostMapping("/elevatorSystem")
    public ResponseEntity changeNumberOfElevators(@RequestParam int numOfElevators) {
        try {
            this.elevatorSystem.changeElevatorsNumber(numOfElevators);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(e.getLocalizedMessage());
        }
    }
}
