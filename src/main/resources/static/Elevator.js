
function parseElevatorStatus(status) {
    var currentFloor = document.getElementById("currentFloor" + status[0]);
    var currentTarget = document.getElementById("currentTarget" + status[0]);

    currentFloor.innerHTML = "Current Floor: " + status[1];
    if(currentTarget != null) currentTarget.innerHTML = "Current Target: " + status[2];
}

function parseElevatorTargets(targets) {
    var table = document.getElementById("targetsTable");

    table.querySelector('tbody').innerHTML = '';
     targets.forEach(target => {
        const { targetFloor, direction } = target;
        const row = document.createElement('tr');

        const floorCell = document.createElement('td');
        floorCell.innerHTML = `<h2>${targetFloor}</h2>Floor`;
        row.appendChild(floorCell);

        const directionCell = document.createElement('td');
        directionCell.innerHTML = `<h2>${direction == 1 ? '↑' : '↓'}</h2>Direction`;
        row.appendChild(directionCell);

        table.querySelector('tbody').appendChild(row);
      });

      const numRows = targets.length + 1;
      table.style.height = `${numRows * 50}px`;
}

setInterval(function(){
    if(!document.getElementById('simulationStatus').checked) return;

    var elevatorId = document.getElementById("elevatorId").innerHTML;

    var stepRequest = new XMLHttpRequest();
    stepRequest.open( "POST" , "/step", false);
    stepRequest.send(null);

    var elevatorStatusRequest = new XMLHttpRequest();
    elevatorStatusRequest.open( "GET", "/elevator/" + elevatorId + "/status", false );
    elevatorStatusRequest.send(null);
    parseElevatorStatus(JSON.parse(elevatorStatusRequest.responseText));

    var elevatorTargetsRequest = new XMLHttpRequest();
    elevatorTargetsRequest.open("GET", "/elevator/" + elevatorId + "/targets", false );
    elevatorTargetsRequest.send(null);
    parseElevatorTargets(JSON.parse(elevatorTargetsRequest.responseText));
}, 1000);

function update(elevatorId, currentFloor, targetFloor) {
    var elevatorUpdateRequest = new XMLHttpRequest();
    var url = "/elevator/" + elevatorId + "/update" + "?currentFloor=" + currentFloor + "&targetFloor=" + targetFloor;

    elevatorUpdateRequest.open("PUT", url, true);
    elevatorUpdateRequest.setRequestHeader("Content-Type", "application/json");
    elevatorUpdateRequest.onreadystatechange = function () {
        if (elevatorUpdateRequest.readyState === 4 && elevatorUpdateRequest.status === 204) {
            alert("Done!");
            return;
        }
     };
    elevatorUpdateRequest.send();
}

