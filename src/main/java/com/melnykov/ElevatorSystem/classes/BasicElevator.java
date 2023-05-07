package com.melnykov.ElevatorSystem.classes;

import com.melnykov.ElevatorSystem.interfaces.Elevator;

import java.util.ArrayList;
import java.util.List;


public class BasicElevator implements Elevator {

    private int id;
    private int currentFloor;
    private int direction;
    private List<Integer> targets;

    public BasicElevator(int id, int startFloor) {
        this.id = id;
        this.currentFloor = startFloor;
        this.direction = 0;
        this.targets = new ArrayList<>();
    }


    @Override
    public void step() {
        if (this.targets.isEmpty()) this.direction = 0;
        else {
            if (this.currentFloor == targets.get(0)) this.targets.remove(0);
            else this.currentFloor += direction;
        }
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public void addTarget(int target) {
        if (!this.targets.contains(target)) this.targets.add(target);
    }

    @Override
    public void removeTarget(int target) {
        this.targets.remove(Integer.valueOf(target));
    }

    @Override
    public int getCurrentTarget() {
        return this.targets.get(0);
    }

    @Override
    public List<Integer> getTargets() {
        return this.targets;
    }

    @Override
    public void setCurrentFloor(int currentFloor) {
        this.currentFloor = currentFloor;
    }

    @Override
    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public int getDirection() {
        return this.direction;
    }

    @Override
    public int[] getStatus() {
        return new int[]{this.id, this.currentFloor, this.targets.isEmpty() ? this.currentFloor : this.targets.get(0)};
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
