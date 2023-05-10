package com.melnykov.ElevatorSystem.interfaces;

import com.melnykov.ElevatorSystem.classes.ElevatorRequest;

import java.util.List;

public interface Elevator {
    void step();
    int getCurrentFloor();
    void addTarget(ElevatorRequest elevatorRequest);
    void insertTarget(ElevatorRequest elevatorRequest, int index);
    ElevatorRequest getCurrentTarget();
    List<ElevatorRequest> getTargets();
    void setCurrentFloor(int currentFloor);
    int[] getStatus();
    int getId();
    boolean isMoving();
}
