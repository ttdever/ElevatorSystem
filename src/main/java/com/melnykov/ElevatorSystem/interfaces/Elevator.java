package com.melnykov.ElevatorSystem.interfaces;

import java.util.List;

public interface Elevator {
    void step();
    int getCurrentFloor();
    void removeTarget(int target);
    void addTarget(int target);
    int getCurrentTarget();
    List<Integer> getTargets();
    void setCurrentFloor(int currentFloor);
    void setDirection(int direction);
    int getDirection();
    int[] getStatus();
    int getId();
    boolean isMoving();
}
