package com.melnykov.ElevatorSystem.classes;

import com.melnykov.ElevatorSystem.exceptions.NoElevatorWithSuchIdException;
import com.melnykov.ElevatorSystem.exceptions.NoElevatorsException;
import com.melnykov.ElevatorSystem.exceptions.ToManyElevatorsException;
import com.melnykov.ElevatorSystem.interfaces.Elevator;
import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


@Component
public class ElevatorController implements ElevatorSystem {
    private static ElevatorSystem instance = null;
    private final int elevatorsMaxNum = 16;
    private int elevatorsNum;
    private final List<Elevator> elevators;

    private ElevatorController(@Value("3") int elevatorsNum) {
        if(elevatorsNum > elevatorsMaxNum) throw new ToManyElevatorsException();
        else if(elevatorsNum <= 0) throw new NoElevatorsException();

        this.elevatorsNum = elevatorsNum;
        this.elevators = new ArrayList<>();
        for(int i = 0; i < elevatorsNum; i++) {
            this.elevators.add(new BasicElevator(i, 0));
        }
    }

    public static ElevatorSystem getInstance(int elevatorsNum) {
        instance = new ElevatorController(elevatorsNum);
        return instance;
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
        try {
            this.elevators.get(elevatorId).setCurrentFloor(currentFloor);
        } catch (IndexOutOfBoundsException e) {
            throw new NoElevatorWithSuchIdException(elevatorId);
        }

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

    public int getElevatorsNum() {
        return elevatorsNum;
    }
}
