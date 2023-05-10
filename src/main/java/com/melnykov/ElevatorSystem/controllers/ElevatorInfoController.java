package com.melnykov.ElevatorSystem.controllers;

import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public class ElevatorInfoController {
    @Autowired
    private ElevatorSystem elevatorSystem;

    @GetMapping("/elevatorStep/{id}")
    public ResponseEntity<int[]> elevatorStep(@PathVariable("id") int id) {
        elevatorSystem.step();
        int[] status = elevatorSystem.getElevators().get(id).getStatus();
        return ResponseEntity.ok(status);
    }

}
