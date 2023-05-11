package com.melnykov.ElevatorSystem.controllers;

import com.melnykov.ElevatorSystem.classes.ElevatorRequest;
import com.melnykov.ElevatorSystem.exceptions.NoElevatorWithSuchIdException;
import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ElevatorInfoController {
    @Autowired
    private ElevatorSystem elevatorSystem;

    @GetMapping(value = "/elevator/{id}/status")
    public ResponseEntity getElevatorStatus(@PathVariable("id") int id) {
        try {
            int[] status = elevatorSystem.getElevators().get(id).getStatus();
            return ResponseEntity.ok(status);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.badRequest().body(new NoElevatorWithSuchIdException(id));
        }
    }

    @GetMapping(value = "/elevator/{id}/targets")
    public ResponseEntity getElevatorTargets(@PathVariable("id") int id) {
        try {
            List<ElevatorRequest> targets = elevatorSystem.getElevators().get(id).getTargets();
            return ResponseEntity.ok(targets);
        } catch (IndexOutOfBoundsException e) {
            return ResponseEntity.badRequest().body(new NoElevatorWithSuchIdException(id));
        }
    }

    @PutMapping(value = "/elevator/{id}/update")
    public ResponseEntity updateElevator(@PathVariable("id") int id, @RequestParam int currentFloor, @RequestParam int targetFloor) {
        try {
            elevatorSystem.update(id, currentFloor, targetFloor);
            return ResponseEntity.noContent().build();
        } catch (NoElevatorWithSuchIdException e) {
            return ResponseEntity.badRequest().body(e);
        }
    }

}
