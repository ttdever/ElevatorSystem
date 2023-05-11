package com.melnykov.ElevatorSystem.classes;

public class ElevatorRequest {

    private final int targetFloor;
    private final int direction;

    public ElevatorRequest(int targetFloor, int direction) {
        this.targetFloor = targetFloor;
        this.direction = direction;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public int getDirection() {
        return direction;
    }
}
