package com.melnykov.ElevatorSystem.classes;

public class ElevatorRequest {

    private int targetFloor;
    private int direction;

    private boolean isMovingToMostFrequentFloor;

    public ElevatorRequest(int targetFloor, int direction, boolean isMovingToMostFrequentFloor) {
        this.targetFloor = targetFloor;
        this.direction = direction;
        this.isMovingToMostFrequentFloor = isMovingToMostFrequentFloor;
    }

    public int getTargetFloor() {
        return targetFloor;
    }

    public int getDirection() {
        return direction;
    }

    public boolean isMovingToMostFrequentFloor() {
        return isMovingToMostFrequentFloor;
    }
}
