package com.melnykov.ElevatorSystem.interfaces;

import java.util.List;

public interface Elevator {
    void step();
    void update(int currentFloor, int requestedFloor);
    void addRequestedFloor(int newFloor);
    int getCurrentFloor();
    List<Integer> getRequestedFloorQueue();
    int getRequestedFloorQueueSize();
    int[] getElevatorStatus();
    boolean getIsMoving();
}
