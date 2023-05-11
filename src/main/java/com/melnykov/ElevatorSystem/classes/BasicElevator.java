package com.melnykov.ElevatorSystem.classes;

import com.melnykov.ElevatorSystem.interfaces.Elevator;

import java.util.ArrayList;
import java.util.List;


public class BasicElevator implements Elevator {

    private final int id;
    private int currentFloor;
    private final List<ElevatorRequest> targets;

    public BasicElevator(int id, int startFloor) {
        this.id = id;
        this.currentFloor = startFloor;
        this.targets = new ArrayList<>();
    }


    @Override
    public void step() {
        if(targets.isEmpty()) return;

        if(this.currentFloor == this.getCurrentTarget().getTargetFloor()) {
            this.targets.remove(this.getCurrentTarget());
            return;
        }

        this.currentFloor += this.getCurrentTarget().getTargetFloor() > this.currentFloor ? 1 : -1;
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public void addTarget(ElevatorRequest elevatorRequest) {
        targets.add(elevatorRequest);
    }

    @Override
    public void insertTarget(ElevatorRequest elevatorRequest, int index) {
        targets.add(index, elevatorRequest);
    }

    @Override
    public ElevatorRequest getCurrentTarget() {
        return this.targets.isEmpty() ? null : this.targets.get(0);
    }

    @Override
    public List<ElevatorRequest> getTargets() {
        return this.targets;
    }

    @Override
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    @Override
    public int[] getStatus() {
        return new int[]{this.id, this.currentFloor, this.targets.isEmpty() ? this.currentFloor : this.targets.get(0).getTargetFloor()};
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public boolean isMoving() {
        return !this.targets.isEmpty();
    }
}
