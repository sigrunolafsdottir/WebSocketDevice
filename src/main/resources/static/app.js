
var ws;
ws = new WebSocket('ws://localhost:8080/device');

ws.onmessage = function(event) {
    var deviceStatus = document.getElementById("deviceStatus");
    var message = event.data;
    deviceStatus.innerHTML = message;
};


function toggle() {
    var status = document.getElementById("deviceStatus").innerHTML.trim();
    var newStatus;

    if (status === 'ON'){
        newStatus = "OFF";
    }
    else{
        newStatus = 'ON';
    }

    var json = JSON.stringify({
        "device":newStatus
    });

    ws.send(json);
}