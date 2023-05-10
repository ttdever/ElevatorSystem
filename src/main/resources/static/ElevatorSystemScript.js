function pickUp() {
    var floorNumber = document.getElementById('floor').value;
    var direction = document.getElementById("direction").innerHTML == "up" ? "1" : "-1";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "POST", "/pick-up?requestedFloor=" + floorNumber + "&direction=" + direction, false );
    xmlHttp.send(null);
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
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "/step", false );
    xmlHttp.send(null);
    parseElevatorsStatuses(JSON.parse(xmlHttp.responseText));
}, 1000);

function createElevatorSystem(numOfElevators) {
    if(numOfElevators <= 0 || numOfElevators > 16) {
        alert("Allowed elevators count is from 1 to 16");
    }

    document.getElementById('simulationStatus').checked = false;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "POST", "/elevatorSystem?numOfElevators="+numOfElevators, false );
    xmlHttp.onreadystatechange =
        function() {
            if (this.readyState == 4 && this.status == 204) window.location.reload();
        };
    xmlHttp.send(null);
}

