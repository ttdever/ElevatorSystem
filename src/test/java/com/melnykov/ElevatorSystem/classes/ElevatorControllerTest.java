package com.melnykov.ElevatorSystem.classes;

import com.melnykov.ElevatorSystem.exceptions.NoElevatorWithSuchIdException;
import com.melnykov.ElevatorSystem.exceptions.NoElevatorsException;
import com.melnykov.ElevatorSystem.exceptions.ToManyElevatorsException;
import com.melnykov.ElevatorSystem.interfaces.Elevator;
import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorControllerTest {
    private ElevatorSystem elevatorController;

    @BeforeEach
    public void prepare() {
        this.elevatorController = new ElevatorController(3);
    }

    @Test
    public void constructor_ShouldCreateElevatorControllerWith3ElevatorsAndRightIds() {
        assertEquals(3, elevatorController.getElevators().size());
        List<Elevator> elevators = elevatorController.getElevators();
        for(int i = 0; i < 3; i++) {
            assertEquals(i, elevators.get(i).getId());
        }
    }

    @Test
    public void constructor_ShouldThrowToManyElevatorsException() {
        assertThrows(ToManyElevatorsException.class, () -> this.elevatorController = new ElevatorController(17));
    }

    @Test
    public void constructor_ShouldThrowNoElevatorsException() {
        assertThrows(NoElevatorsException.class, () -> this.elevatorController = new ElevatorController(0));
    }

    @Test
    public void update_ShouldCorrectlyUpdateElevator() {
        this.elevatorController.update(0, 3, 4);
        Elevator elevator0 = elevatorController.getElevators().get(0);

        assertEquals(0, elevator0.getId());
        assertEquals(3, elevator0.getCurrentFloor());
        assertEquals(4, elevator0.getCurrentTarget());
    }

    @Test
    public void update_ShouldThrowNoElevatorWithSuchIdException() {
        assertThrows(NoElevatorWithSuchIdException.class, () -> this.elevatorController.update(4, 1, 1));
    }

    @Test
    public void step_ShouldCorrectlyStepElevatorToRequestedFloor() {
        Elevator elevator0 = elevatorController.getElevators().get(0);
        elevatorController.pickup(3, 1);

        int counter = 0;
        while (elevator0.isMoving()) {
            assertEquals(counter, elevator0.getCurrentFloor());
            elevatorController.step();
            counter++;
        }
        assertEquals(3, elevator0.getCurrentFloor());
        assertEquals(0, elevator0.getDirection());
    }

    @Test
    public void pickup_ShouldSetTarget3andPositiveDirection() {

        //Ask for elevator to go to floor 3
        this.elevatorController.pickup(3, 1);
        Elevator elevator0 = this.elevatorController.getElevators().get(0);
        //Elevator 0 was selected:
        assertEquals(1, elevator0.getTargets().size());
        assertEquals(3, elevator0.getTargets().get(0).intValue());
        assertEquals(1, elevator0.getDirection());

        //Wait for elevator 0 to finish its move:
        int passedFloorCounter = 0;
        while (elevator0.isMoving()) {
            passedFloorCounter++;
            elevatorController.step();
        }
        //Check if elevator 0 has passed correct num of floor:
        assertEquals(3, passedFloorCounter);

        //Now elevator 0 is at the 3 floor, lets ask for pickup at 1 floor (it must call elevator 1 in this case):
        elevatorController.pickup(1, 1);
        Elevator elevator1 = elevatorController.getElevators().get(1);
        assertEquals(1, elevator1.getTargets().size());
        assertEquals(1, elevator1.getTargets().get(0).intValue());
        assertEquals(1, elevator1.getDirection());
    }

    @Test
    public void getElevators_ShouldReturnListOfElevatorsWithCorrectIds() {
        List<Elevator> elevators = elevatorController.getElevators();
        int numOfElevators = elevators.size();
        for(int i = 0; i < numOfElevators; i++) {
            assertEquals(i, elevators.get(i).getId());
        }
    }

    @Test
    public void status_ShouldReturnRightStatusesOfElevators() {
        elevatorController.update(0, 0, 0);
        elevatorController.update(1, 1, 1);
        elevatorController.update(2, 2, 2);

        assertTrue(Arrays.deepEquals(new int[][] {{0,0,0}, {1,1,1}, {2,2,2}}, elevatorController.status()));
    }
}