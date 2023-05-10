package com.melnykov.ElevatorSystem.exceptions;

public class WrongDirectionException extends RuntimeException {
    public WrongDirectionException() {
        super("Wrong direction provided, direction can be either 1 or -1");
    }
}