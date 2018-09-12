<%--
  Created by IntelliJ IDEA.
  User: javaboy
  Date: 9/11/18
  Time: 9:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../styles/normalize.css">
    <link rel="stylesheet" href="../styles/duelfield.css">
</head>
<body>
<div class="container">
    <div class="container__preloader">
        <h1>Поиск противника</h1>
        <div class="preloader">
        </div>
    </div>

    <div id="timer">

    </div>
    <div id="enemy_description">
        <h2>Ващ противник: </h2>
        <div class="enemyNickname">Nickname</div>
        <div class="enemyRating">Rating</div>
        <div><img width="120px" height="120px" src="https://pre00.deviantart.net/7e15/th/pre/i/2010/274/e/e/stick_man_by_minimoko94-d2zvfn8.png"></div>
    </div>
</div>
<div class="game_container">
    <div class="notification"

>Ход пети</div>
    <header>
        <div class="health_points">
            <% String nickname = (String) request.getAttribute("name");
            out.println("<span class=\"nickname\">Имя: "+ nickname+"</span>");%>

            <div class="myProgress">
                <div class="myBar"></div>

            </div>
            <% int rating = (int) request.getAttribute("rating");
                out.println("<span class=\"rating\">Рэйтинг: "+ + rating +"</span>");%>


        </div>
        <div class="health_points">
            <span class="enemyRating">rating</span>
            <div class="myProgress">
                <div class="myBar"></div>
            </div>
            <span class="enemyNickname">nickname</span>
        </div>
    </header>
    <main class="gamefield">
        <div class="gamefield__player">
            <div>
                <img src="https://orig00.deviantart.net/b4da/f/2012/176/b/2/just_a_simple_stick_man_by_roblox3dflash-d54szei.png"/>
            </div>
        </div>
        <div class="gamefield_enemy">
            <div>
                <img src="https://orig00.deviantart.net/b4da/f/2012/176/b/2/just_a_simple_stick_man_by_roblox3dflash-d54szei.png"/>
            </div>
        </div>
    </main>
    <button disabled class="attack_btn">Атаковать</button>
    <footer><p>Java ударила c# на 10</p></footer>
</div>
    <jsp:include page="../jsLoader.jsp"></jsp:include>
</body>
</html>
