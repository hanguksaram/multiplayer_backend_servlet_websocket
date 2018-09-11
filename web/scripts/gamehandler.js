var wsUri = "ws://" + document.location.host + "/duelsession";
var websocket = new WebSocket(wsUri);

websocket.onerror = function(evt) { onError(evt) };
websocket.onopen = function() {
    console.log("socket was opened")
}
websocket.onmessage = function(event) {messageHandler(event)}
console.log("lal");
function runTimer() {
    var timerDiv = document.getElementById("timer");
    var time = 30;
    timerDiv.innerText = time;
    var intervalId = setInterval(function() {
        --time;
        timerDiv.innerText = time;
        if (time == 0) clearInterval(intervalId);},
    1000);

}
function messageHandler(event) {
    console.log(event.data);
    var data = json.parse(event.data);
    switch (data.status) {
        case "startGame":
            runTimer();

    }
}