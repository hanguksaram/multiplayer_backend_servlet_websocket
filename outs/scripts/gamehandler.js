var wsUri = "ws://" + document.location.host + "/duelsession";
var websocket = new WebSocket(wsUri);
var gameID;
var sessionID;
var enemyName;
// window.onunload = websocket.close();
websocket.onerror = function(evt) { onError(evt) };
websocket.onopen = function() {
    console.log("socket was opened")
}
websocket.onmessage = function(event) {messageHandler(event)}
document.getElementById("damage_btn").addEventListener("click",dealDamage)

console.log("lal");
function runTimer(func) {
    document.getElementById("timer").style.display = "flex";
    var timerDiv = document.getElementById("timer__timer");
    var time = 30;
    timerDiv.innerText = time;
    var intervalId = setInterval(function() {
        --time;
        timerDiv.innerText = time;
        if (time == 0){ clearInterval(intervalId);func()}
        },
    1000);

}

function initGame(data) {
    runTimer(removePopup)
    checkTurn(data.data[0].isTurn);
    gameID = data.data[0].gameId
    sessionID = data.data[0].sessionId
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
            console.log(data);
            handleDamage(data);
            break;
        case "end":
            handleGameEnd(data.data)
            break;


    }
}
function removePreloader() {
    var preloader = document.getElementsByClassName("container__preloader")[0];
    preloader.parentNode.removeChild(preloader);
}
function handleGameEnd(data) {
    for (var i = 0; i < data.length; i++) {
        if (data[i].sessionId != sessionID){
            continue
        }
        data[i].isTurn == 0 ? alert("вы победили"): alert("вы проиграли")
        websocket.close();
        document.location.href = "/menu";
        break;

    }
}
function previewEnemy(enemyObj) {
    console.log(enemyObj.data[0]);
    enemyName = enemyObj.data[0].enemyNickname;
    var enemyNickName = document.getElementsByClassName("enemyNickname");
    var enemyRating = document.getElementsByClassName("enemyRating");
    console.log(enemyNickName, enemyRating)

    for (var i = 0; i < enemyNickName.length; i++) {
        enemyNickName[i].innerText = "Имя: " + enemyName;
        enemyRating[i].innerText = "Рэйтинг: " + enemyObj.data[0].enemyRating;
    }

    document.getElementById("enemyHealth").innerText = "Здоровье: " +enemyObj.data[0].enemyHealth;
    document.getElementById("enemyDamage").innerText = "Урон: " +enemyObj.data[0].enemyDamage;
    document.getElementById("enemy_description").style.display = "flex";
}
function removePopup() {
    document.getElementsByClassName("container")[0].classList.add("container--closed");
}
function handleDamageMessage(data) {
    data.data.forEach(function(elem) {
        if (elem.sessionId == sessionID) {
            console.log(elem.healthPercentage);
            document.getElementsByClassName("myBar")[0].style.width = elem.healthPercentage + '%';

        }
        else
        {
            console.log(elem.healthPercentage);
            document.getElementsByClassName("myBar")[1].style.width = elem.healthPercentage + '%';
            checkTurn(elem.isTurn);
            logDuelMessages(elem.isTurn, elem);
        }
    })
}
function dealDamage() {
    websocket.send(JSON.stringify({status: "damage", gameSessionId: gameID}))
}
function handleDamage(data) {
    document.getElementById("damage_btn").disabled = false;

    handleDamageMessage(data)

}
function checkTurn(flag) {
    if (flag == 0)
    {
        document.getElementById("damage_btn").disabled = false;
        document.getElementById("notification").innerText = "Ваш ход";
    }
    else {
        document.getElementById("damage_btn").disabled = true;
        document.getElementById("notification").innerText = "Ходит " + enemyName;
    }


}
function logDuelMessages(flag, data) {
    function checkCrit(data) {
        return data.damageMultiplier == data.damageDealed ? " Кританул" : " Ударил";
    }
    var damageType = checkCrit(data);

    if (flag == 0) {

        document.getElementById("log").innerText = enemyName + damageType + " вас на "  + data.damageDealed;
    }
    else {

        document.getElementById("log").innerText = "Вы" + damageType + "и " + enemyName + "(a)" + " на " + data.damageDealed;
    }
}