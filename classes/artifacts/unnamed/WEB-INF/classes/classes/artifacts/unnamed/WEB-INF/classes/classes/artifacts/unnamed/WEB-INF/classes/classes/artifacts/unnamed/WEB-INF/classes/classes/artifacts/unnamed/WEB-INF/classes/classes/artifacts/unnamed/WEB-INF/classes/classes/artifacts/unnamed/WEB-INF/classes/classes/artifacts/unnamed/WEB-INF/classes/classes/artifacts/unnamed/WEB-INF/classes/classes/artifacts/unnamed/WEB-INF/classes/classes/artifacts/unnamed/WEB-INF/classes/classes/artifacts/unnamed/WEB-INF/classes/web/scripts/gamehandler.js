var wsUri = "ws://" + document.location.host + "/duelsession";
var websocket = new WebSocket(wsUri);
var gameID;
var sessionID;
// window.onunload = websocket.close();
websocket.onerror = function(evt) { onError(evt) };
websocket.onopen = function() {
    console.log("socket was opened")
}
websocket.onmessage = function(event) {messageHandler(event)}
document.getElementById("damage_btn").addEventListener("click", function() {
    websocket.send(JSON.stringify({status: "damage", gameSessionId: gameID}))
})
console.log("lal");
function runTimer(func) {
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

function initGame(gameId) {
    runTimer(removePopup)
    gameID = gameId
}
function messageHandler(event) {

    console.log(event.data);
    var data = JSON.parse(event.data);
    switch (data.status) {
        case "start":
            removePreloader();
            previewEnemy(data);
            initGame(data.gameId)
            break;
        case "connectionlost":
            alert("Противник отключился");
            document.location.href = "/menu";
            break;
        case "undefined":
            calculateHealthPoints(data)


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
function calculateHealthPoints(data) {
    data.forEach(function(elem) {
        if (elem.sessionId == sessionID)
            document.getElementsByClassName("myBar")[0].style.width = data.healthPercentage + '%';
        else
            document.getElementsByClassName("myBar")[1].style.width = data.healthPercentage + '%';
    })
}