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
document.getElementById("damage_btn").addEventListener("click",dealDamage)

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

function initGame(data) {
    runTimer(removePopup)
    gameID = data.gameId
    sessionID = data.sessionId
}
function messageHandler(event) {

    console.log(event.data);
    var data = JSON.parse(event.data);
    switch (data.status) {
        case "start":
            removePreloader();
            previewEnemy(data);
            initGame(data)
            break;
        case "connectionlost":
            alert("Противник отключился");
            document.location.href = "/menu";
            break;
        case "damage":
            handleDamage(data)


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
    data.data.forEach(function(elem) {
        if (elem.sessionId == sessionID) {
            console.log(elem.healthPercentage);
            document.getElementsByClassName("myBar")[0].style.width = elem.healthPercentage.toString() + '%';
        }
        else
        {
            console.log(elem.healthPercentage);
            document.getElementsByClassName("myBar")[1].style.width = elem.healthPercentage.toString() + '%';
        }
    })
}
function dealDamage() {
    websocket.send(JSON.stringify({status: "damage", gameSessionId: gameID})
    document.getElementById("damage_btn").disabled = true;
    document.getElementById("notification").innerText = "Ходит " + document.getElementsByClassName("enemyNickname")[0].innerText;
}
function handleDamage(data) {
    document.getElementById("damage_btn").disabled = false;
    document.getElementById("notification").innerText = "Ващ ход";
    calculateHealthPoints(data)

}