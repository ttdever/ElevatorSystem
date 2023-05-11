# ElevatorSystem
Simple elevator controller with web-UI made with Spring and Thymeleaf

This app is able to controll up to 16 elevators. It is designed to find a shortest way to a requested floor.

# How to access a web interface?
Simply start an app and go to http://localhost:8080

# WebInterface explanation:
At the page you will see an Elevator Control Panel, where you will find next information:

1. Number of elevators
2. Simulation status (don't forget to turn it on if you want to test an application)
3. Table with information about elevators (ID, Current floor, Current target, Action button)
4. Field with floor selection, select a destination floor and a direction
5. Control panel creation field - here you can create a new control panel with different amount of elevators

You can click on "ⓘ" icon in an action row, to access more detailed information about selected elevator.

On Elevator information page you will find:

1. Simulation status (don't forget to turn it on if you want to test an application)
2. Update field, that will let you change elevator floor and target
3. Information about elevator (ID, Current floor, Current target and a list of targets)

# Algorithm explanation:
Algorithm is trying to find an elevator with a shortest path to requested floor, here is how it works:

1. First of all we create a map Elevators -> stepsToTarget
```java
Map<Elevator, Integer> elevatorFloorsToTargetMap = new HashMap<>();
```

2. Then we start to iterate over Elevators and get a request list of each:
```java
for (Elevator e : elevators) {
        List<ElevatorRequest> requestList = e.getTargets();
```

3. After that we iterate over request list and check if elevator will cross a requested floor:
```java
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
```

4. If it'll cross the requested floor and will be moving in the same direction, then we can stop at requested floor on our way.
5. If not -> we can move to next target and check if we will cross requested floor when moving to it, but way to target will be increased, because we need to process an earlier requests first:

```java
for(ElevatorRequest request : requestList) {
    if(isAbleToPickUpOnWay(e.getCurrentFloor(), request.getTargetFloor(), request.getDirection(), targetFloor, direction)) {
        crossTargetFloor = true;
        break;
    } else {
        indexToInsertTargetIn++;
        elevatorWayToTarget += Math.abs(request.getTargetFloor() - e.getCurrentFloor());
    }
}
```
6. Of course there may be a situation, when elevator will never cross a requested floor, then we need to add to elevator
way a distance between last target and requested floor
```java
if(!crossTargetFloor) {
    if(requestList.isEmpty()) elevatorWayToTarget += Math.abs(e.getCurrentFloor() - targetFloor);
    else elevatorWayToTarget += Math.abs(requestList.get(requestList.size() - 1).getTargetFloor() - targetFloor);
}
```

7. After calculations, we put our data into a Map and then search for a Elevator with minimum count of steps:
```java
Elevator bestElevator = elevatorFloorsToTargetMap.entrySet().stream().min(Map.Entry.comparingByValue()).get().getKey();
```

8. While iterating we were also checking a target index, at which we are iterating, in that way we know where to insert request in target list:
```java
bestElevator.insertTarget(new ElevatorRequest(targetFloor, direction), indexToInsertTargetIn);
```