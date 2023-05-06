package com.melnykov.ElevatorSystem.classes;

import com.melnykov.ElevatorSystem.interfaces.Elevator;

import java.util.List;

public class BasicElevator implements Elevator {

    int id;
    int currentFloor;
    int targetFloor;
    int direction;
    List<Integer> requestedFloorQueue;

    public BasicElevator() {
        direction = 0;
        currentFloor = 0;
        requestedFloorQueue = List.of();
    }

    @Override
    public void step() {
        if(currentFloor == targetFloor && requestedFloorQueue.size() == 0) direction = 0;
        else if(currentFloor == targetFloor) {
            targetFloor = requestedFloorQueue.get(0);
            requestedFloorQueue.remove(0);
            direction = currentFloor > targetFloor ? 1 : -1;
        }
        else currentFloor += direction;
    }

    @Override
    public void update(int currentFloor, int requestedFloor) {
        this.currentFloor = currentFloor;
        this.requestedFloorQueue.add(requestedFloor);
    }

    @Override
    public void addRequestedFloor(int newFloor) {
        requestedFloorQueue.add(newFloor);
    }

    @Override
    public int getCurrentFloor() {
        return currentFloor;
    }

    @Override
    public List<Integer> getRequestedFloorQueue() {
        return requestedFloorQueue;
    }

    @Override
    public int getRequestedFloorQueueSize() {
        return requestedFloorQueue.size();
    }

    @Override
    public int[] getElevatorStatus() {
        return new int[] {id, currentFloor, targetFloor};
    }

    @Override
    public boolean getIsMoving() {
        return currentFloor != targetFloor;
    }
}
