package com.melnykov.ElevatorSystem.exceptions;

public class NoElevatorsException extends RuntimeException {
    public NoElevatorsException() {
        super("There are no elevators, please add some more");
    }
}
