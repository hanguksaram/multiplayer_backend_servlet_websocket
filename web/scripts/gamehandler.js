var wsUri = "ws://" + document.location.host + "/duelsession";
var websocket = new WebSocket(wsUri);

// window.onunload = websocket.close();
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
        if (time == 0){ clearInterval(intervalId);removePopup()}
        },
    1000);

}
function messageHandler(event) {

    console.log(event.data);
    var data = JSON.parse(event.data);
    switch (data.status) {
        case "start":
            removePreloader();
            previewEnemy(data);
            runTimer();
            break;
        case "connectionlost":
            alert("Противник отключился");
            document.location.href = document.location.host + "/menu";

    }
}
function removePreloader() {
    var preloader = document.getElementsByClassName("container__preloader")[0];
    preloader.parentNode.removeChild(preloader);
}
function previewEnemy(enemyObj) {
    var enemyNickName = document.getElementsByClassName("enemyNickname");
    var enemyRating = document.getElementsByClassName("enemyRating");
    for (var i = 0; i < enemyNickName.length; i++) {
        enemyNickName[i].innerText = "Имя: " +enemyObj.enemyNickname;
        enemyRating[i].innerText = "Рэйтинг: " + enemyObj.enemyRating;
    }
    document.getElementById("enemy_description").style.display = "flex";
}
function removePopup() {
    document.getElementsByClassName("container")[0].classList.add("container--closed");
}