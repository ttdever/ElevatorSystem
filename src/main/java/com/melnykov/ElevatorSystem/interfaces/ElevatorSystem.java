package com.melnykov.ElevatorSystem.interfaces;

import java.util.List;

public interface ElevatorSystem {
    void pickup(int targetFloor, int direction);
    void update(int elevatorId, int currentFloor, int requestedFloor);
    void step();
    int[][] status();
    List<Elevator> getElevators();
    void changeElevatorsNumber(int elevatorsNum);

}
