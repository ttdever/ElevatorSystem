package com.melnykov.ElevatorSystem.classes;

import com.melnykov.ElevatorSystem.exceptions.NoElevatorWithSuchIdException;
import com.melnykov.ElevatorSystem.exceptions.NoElevatorsException;
import com.melnykov.ElevatorSystem.interfaces.Elevator;
import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;

import java.util.Comparator;
import java.util.List;

public class ElevatorController implements ElevatorSystem {

    List<Elevator> elevators;

    @Override
    public void pickup(int requestedFloor, int direction) {
        elevators.stream()
                .min(Comparator.comparing(Elevator::getRequestedFloorQueueSize))
                .orElseThrow(NoElevatorsException::new).addRequestedFloor(requestedFloor);
    }

    @Override
    public void update(int elevatorId, int currentFloor, int requestedFloor) {
        elevators.stream()
                .filter(elevator -> elevator.getElevatorStatus()[0] == elevatorId)
                .findFirst().orElseThrow(() -> new NoElevatorWithSuchIdException(elevatorId))
                .update(currentFloor, requestedFloor);
    }

    @Override
    public void step() {
        for(Elevator e: elevators) e.step();
    }

    @Override
    public int[][] status() {
        int[][] result = new int[elevators.size()][3];
        for(int i = 0; i < elevators.size(); i++) {
            result[i] = elevators.get(i).getElevatorStatus();
        }
        return result;
    }
}
