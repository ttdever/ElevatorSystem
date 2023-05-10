package com.melnykov.ElevatorSystem.classes;

import com.melnykov.ElevatorSystem.exceptions.NoElevatorWithSuchIdException;
import com.melnykov.ElevatorSystem.exceptions.NoElevatorsException;
import com.melnykov.ElevatorSystem.exceptions.ToManyElevatorsException;
import com.melnykov.ElevatorSystem.interfaces.Elevator;
import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorControllerTest {
    private ElevatorSystem elevatorController;

    @Test
    public void constructor_ShouldCreateElevatorControllerWith3ElevatorsAndRightIds() {
        elevatorController = ElevatorController.getInstance(3);
        assertEquals(3, elevatorController.getElevators().size());
        List<Elevator> elevators = elevatorController.getElevators();
        for(int i = 0; i < 3; i++) {
            assertEquals(i, elevators.get(i).getId());
        }
    }

    @Test
    public void constructor_ShouldThrowToManyElevatorsException() {
        assertThrows(ToManyElevatorsException.class, () -> this.elevatorController = ElevatorController.getInstance(17));
    }

    @Test
    public void constructor_ShouldThrowNoElevatorsException() {
        assertThrows(NoElevatorsException.class, () -> this.elevatorController = ElevatorController.getInstance(0));
    }

    @Test
    public void update_ShouldCorrectlyUpdateElevator() {
        elevatorController = ElevatorController.getInstance(1);
        this.elevatorController.update(0, 3, 4);
        Elevator elevator0 = elevatorController.getElevators().get(0);

        assertEquals(0, elevator0.getId());
        assertEquals(3, elevator0.getCurrentFloor());
        assertEquals(4, elevator0.getCurrentTarget().getTargetFloor());
    }

    @Test
    public void update_ShouldThrowNoElevatorWithSuchIdException() {
        elevatorController = ElevatorController.getInstance(1);
        assertThrows(NoElevatorWithSuchIdException.class, () -> this.elevatorController.update(4, 1, 1));
    }

    @Test
    public void step_ShouldCorrectlyStepElevatorToRequestedFloor() {
        elevatorController = ElevatorController.getInstance(1);
        Elevator elevator0 = elevatorController.getElevators().get(0);
        elevatorController.pickup(3, 1);

        int counter = 0;
        while (elevator0.isMoving()) {
            assertEquals(counter, elevator0.getCurrentFloor());
            elevatorController.step();
            counter++;
        }

        assertEquals(3, elevator0.getCurrentFloor());
    }

    @Test
    public void pickup_ShouldAssignTargetToElevator() {
        elevatorController = ElevatorController.getInstance(1);
        elevatorController.pickup(3, 1);

        Elevator elevator0 = elevatorController.getElevators().get(0);
        assertEquals(1, elevator0.getTargets().size());
        assertEquals(3, elevator0.getCurrentTarget().getTargetFloor());
    }

    @Test
    public void pickup_ShouldAssignTargetToNearestElevator() {
        elevatorController = ElevatorController.getInstance(2);
        elevatorController.update(0, 10, 10);
        elevatorController.update(1, 1, 1);
        elevatorController.step();
        elevatorController.pickup(2, 1);

        Elevator elevator1 = elevatorController.getElevators().get(1);

        assertEquals(1, elevator1.getTargets().size());
        assertEquals(2, elevator1.getCurrentTarget().getTargetFloor());
    }

    @Test
    public void getElevators_ShouldReturnListOfElevatorsWithCorrectIds() {
        elevatorController = ElevatorController.getInstance(16);
        List<Elevator> elevators = elevatorController.getElevators();
        int numOfElevators = elevators.size();
        for(int i = 0; i < numOfElevators; i++) {
            assertEquals(i, elevators.get(i).getId());
        }
    }

    @Test
    public void status_ShouldReturnRightStatusesOfElevators() {
        elevatorController = ElevatorController.getInstance(3);
        elevatorController.update(0, 0, 0);
        elevatorController.update(1, 1, 1);
        elevatorController.update(2, 2, 2);

        assertTrue(Arrays.deepEquals(new int[][] {{0,0,0}, {1,1,1}, {2,2,2}}, elevatorController.status()));
    }
}