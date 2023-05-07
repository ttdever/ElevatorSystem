package com.melnykov.ElevatorSystem.classes;

import com.melnykov.ElevatorSystem.interfaces.Elevator;
import com.melnykov.ElevatorSystem.interfaces.ElevatorSystem;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ElevatorControllerTest {
    private ElevatorSystem elevatorController;

    @BeforeEach
    public void prepare() {
        this.elevatorController = new ElevatorController(3, 10);
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
        //Check if elevator 0 has passed correct num of floor (assuming that moving from floor 3 to 3 isn't move):
        assertEquals(3, passedFloorCounter - 1);

        //Now elevator 0 is at the 3 floor, lets ask for pickup at 1 floor (it must call elevator 1 in this case):
        elevatorController.pickup(1, 1);
        Elevator elevator1 = elevatorController.getElevators().get(1);
        assertEquals(1, elevator1.getTargets().size());
        assertEquals(1, elevator1.getTargets().get(0).intValue());
        assertEquals(1, elevator1.getDirection());
    }
}