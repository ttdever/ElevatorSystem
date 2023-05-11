function pickUp() {
    var floorNumber = document.getElementById('floor').value;
    var direction = document.getElementById("direction").innerHTML == "up" ? "1" : "-1";

    var pickupRequest = new XMLHttpRequest();
    pickupRequest.open( "POST", "/pick-up?requestedFloor=" + floorNumber + "&direction=" + direction, false );
    pickupRequest.send(null);
}

function parseElevatorsStatuses(data) {
    for(var i = 0; i < data.length; i++) {
        var floor = document.getElementById('statusFloor'+i);
        var target = document.getElementById('statusTarget'+i)

        floor.innerHTML = data[i][1];
        target.innerHTML = data[i][2];
    }
}

setInterval(function(){
    if(!document.getElementById('simulationStatus').checked) return;

    var stepRequest = new XMLHttpRequest();
    stepRequest.open( "POST" , "/step", false);
    stepRequest.send(null);

    var elevatorSystemInfoRequest = new XMLHttpRequest();
    elevatorSystemInfoRequest.open( "GET", "/elevatorSystemInfo", false );
    elevatorSystemInfoRequest.send(null);
    parseElevatorsStatuses(JSON.parse(elevatorSystemInfoRequest.responseText));
}, 1000);

function createElevatorSystem(numOfElevators) {
    if(numOfElevators <= 0 || numOfElevators > 16) {
        alert("Allowed elevators count is from 1 to 16");
    }

    document.getElementById('simulationStatus').checked = false;
    var creationRequest = new XMLHttpRequest();
    creationRequest.open( "POST", "/elevatorSystem?numOfElevators="+numOfElevators, false );
    creationRequest.onreadystatechange =
        function() {
            if (this.readyState == 4 && this.status == 204) window.location.reload();
        };
    creationRequest.send(null);
}

