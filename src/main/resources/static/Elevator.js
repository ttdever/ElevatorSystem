
function parseElevatorData(data) {

}

setInterval(function(){
    if(!document.getElementById('simulationStatus').checked) return;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", "/elevatorStep", false );
    xmlHttp.send(null);
    parseElevatorData(JSON.parse(xmlHttp.responseText));
}, 1000);

