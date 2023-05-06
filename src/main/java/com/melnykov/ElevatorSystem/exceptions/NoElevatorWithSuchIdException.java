package com.melnykov.ElevatorSystem.exceptions;

public class NoElevatorWithSuchIdException extends RuntimeException {
    public NoElevatorWithSuchIdException(int requestedId) {
        super("There is no elevator with id " + requestedId);
    }
}
