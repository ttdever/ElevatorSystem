package com.melnykov.ElevatorSystem.exceptions;

public class ToManyElevatorsException extends RuntimeException {
    public ToManyElevatorsException() {
        super("There are to many elevators");
    }
}
