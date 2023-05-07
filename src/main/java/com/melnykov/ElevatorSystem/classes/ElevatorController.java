package com.melnykov.ElevatorSystem.classes;

import com.melnykov.ElevatorSystem.exceptions.ToManyElevatorsException;
import com.melnykov.ElevatorSystem.interfaces.Elevator;
import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class ElevatorController implements ElevatorSystem {

    private final int elevatorsMaxNum = 16;
    private int elevatorsNum;
    private final List<Elevator> elevators;

    public ElevatorController(int elevatorsNum, int floorsNum) {
        if(elevatorsNum > elevatorsMaxNum) throw new ToManyElevatorsException();

        this.elevatorsNum = elevatorsNum;
        this.elevators = new ArrayList<>();
        for(int i = 0; i < elevatorsNum; i++) {
            this.elevators.add(new BasicElevator(i, 0));
        }
    }
    @Override
    public void pickup(int targetFloor, int direction) {
        Elevator closestElevator = elevators.stream()
                .min(Comparator.comparingInt(e -> Math.abs(e.getCurrentFloor() - targetFloor)))
                .orElseThrow();

        closestElevator.addTarget(targetFloor);
        closestElevator.setDirection(direction);
    }

    @Override
    public void update(int elevatorId, int currentFloor, int targetFloor) {
        this.elevators.get(elevatorId).setCurrentFloor(currentFloor);
        this.elevators.get(elevatorId).removeTarget(currentFloor);
        this.elevators.get(elevatorId).addTarget(targetFloor);
    }

    @Override
    public void step() {
        for(Elevator e : elevators) e.step();
    }

    @Override
    public int[][] status() {
        int[][] statuses = new int[this.elevatorsNum][3];
        for(int i = 0; i < statuses.length; i++) {
            statuses[i] = elevators.get(i).getStatus();
        }
        return statuses;
    }

    @Override
    public List<Elevator> getElevators() {
        return this.elevators;
    }
}
