package com.melnykov.ElevatorSystem.classes;

import com.melnykov.ElevatorSystem.exceptions.NoElevatorWithSuchIdException;
import com.melnykov.ElevatorSystem.exceptions.NoElevatorsException;
import com.melnykov.ElevatorSystem.exceptions.ToManyElevatorsException;
import com.melnykov.ElevatorSystem.exceptions.WrongDirectionException;
import com.melnykov.ElevatorSystem.interfaces.Elevator;
import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;


@Component
public class ElevatorController implements ElevatorSystem {
    private static ElevatorSystem instance = null;
    private final int elevatorsMaxNum = 16;
    private final int elevatorsNum;
    private final List<Elevator> elevators;

    private ElevatorController(@Value("3") int elevatorsNum) {
        if(elevatorsNum > elevatorsMaxNum) throw new ToManyElevatorsException();
        else if(elevatorsNum <= 0) throw new NoElevatorsException();

        this.elevatorsNum = elevatorsNum;
        this.elevators = new ArrayList<>();
        for(int i = 0; i < elevatorsNum; i++) {
            this.elevators.add(new BasicElevator(i, 0));
        }
    }

    public static ElevatorSystem getInstance(int elevatorsNum) {
        instance = new ElevatorController(elevatorsNum);
        return instance;
    }
    @Override
    public void pickup(int targetFloor, int direction) {
        if(direction != 1 && direction != -1) throw new WrongDirectionException();

        Map<Elevator, Integer> elevatorFloorsToTargetMap = new HashMap<>();
        int indexToInsertTargetIn = 0;

        for (Elevator e : elevators) {
            List<ElevatorRequest> requestList = e.getTargets();
            int elevatorWayToTarget = 0;

            boolean crossTargetFloor = false;

            for(ElevatorRequest request : requestList) {
                if(isAbleToPickUpOnWay(e.getCurrentFloor(), request.getTargetFloor(), request.getDirection(), targetFloor, direction)) {
                    crossTargetFloor = true;
                    break;
                } else {
                    indexToInsertTargetIn++;
                    elevatorWayToTarget += Math.abs(request.getTargetFloor() - e.getCurrentFloor());
                }
            }
            if(!crossTargetFloor) {
                if(requestList.isEmpty()) elevatorWayToTarget += Math.abs(e.getCurrentFloor() - targetFloor);
                else elevatorWayToTarget += Math.abs(requestList.get(requestList.size() - 1).getTargetFloor() - targetFloor);
            }
            elevatorFloorsToTargetMap.put(e, elevatorWayToTarget);
        }

        Elevator bestElevator = elevatorFloorsToTargetMap.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
        bestElevator.insertTarget(new ElevatorRequest(targetFloor, direction, false), indexToInsertTargetIn);
    }

    private boolean isAbleToPickUpOnWay(int currentFloor, int currentTargetFloor, int currentDirection, int requestedFloor, int requestedFloorDirection) {
        int directionDelta = currentDirection + requestedFloorDirection;
        switch (directionDelta) {
            case 0:
                return false;
            case 2:
                if (requestedFloor > currentFloor && requestedFloor < currentTargetFloor) return true;
            case -2:
                if (requestedFloor < currentFloor && requestedFloor > currentTargetFloor) return true;
        }
        return false;
    }

    @Override
    public void update(int elevatorId, int currentFloor, int targetFloor) {
        try {
            this.elevators.get(elevatorId).setCurrentFloor(currentFloor);
        } catch (IndexOutOfBoundsException e) {
            throw new NoElevatorWithSuchIdException(elevatorId);
        }

        this.elevators.get(elevatorId).addTarget(new ElevatorRequest(targetFloor, currentFloor, false));
    }

    @Override
    public void step() {
        for(Elevator e : elevators) e.step();
    }

    @Override
    public int[][] status() {
        int[][] statuses = new int[this.elevatorsNum][3];
        for(int i = 0; i < statuses.length; i++) {
            statuses[i] = elevators.get(i).getStatus();
        }
        return statuses;
    }

    @Override
    public List<Elevator> getElevators() {
        return this.elevators;
    }

    public int getElevatorsNum() {
        return elevatorsNum;
    }
}
