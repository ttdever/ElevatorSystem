package com.melnykov.ElevatorSystem.interfaces;

public interface ElevatorSystem {
    void pickup(int requestedFloor, int direction);
    void update(int elevatorId, int currentFloor, int requestedFloor);
    void step();
    int[][] status();

}
