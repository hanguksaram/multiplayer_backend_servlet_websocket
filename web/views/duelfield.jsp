
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="bodyContent">
    <% String nickname = (String) request.getAttribute("name");
        out.println("<span class=\"nickname\">Имя: "+ nickname+"</span>");%>

    <div class="myProgress">
        <div class="myBar"></div>

    </div>
    <% int rating = (int) request.getAttribute("rating");
        out.println("<span class=\"rating\">Рэйтинг: "+ + rating +"</span>");%>
</c:set>
<t:layout>
    <jsp:body>
        <div class="body_game">
        <div class="container">
            <div class="container__preloader">
                <h1>Поиск противника</h1>
                <div class="preloader">
                </div>
            </div>

            <div id="timer">
                <h1>До начала дуэли: </h1>
                <div id="timer__timer"></div>
            </div>
            <div id="enemy_description">
                <h2>Ваш противник: </h2>
                <div class="enemyNickname"></div>
                <div class="enemyRating"></div>
                <div id="enemyHealth"></div>
                <div id="enemyDamage"></div>
                <div><img width="120px" height="120px" src="https://pre00.deviantart.net/7e15/th/pre/i/2010/274/e/e/stick_man_by_minimoko94-d2zvfn8.png"></div>
            </div>
        </div>
        <div class="game_container">
        <div id="notification"
        ></div>
        <div class="top_bar">
            <div class="health_points">
                    ${bodyContent}
            </div>
            <div class="health_points">
                <span class="enemyRating">rating</span>
                <div class="myProgress">
                    <div class="myBar"></div>
                </div>
                <span class="enemyNickname">nickname</span>
            </div>
            </div>
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
            <button id="damage_btn" class="attack_btn">Атаковать</button>
            <div class="bottom_bar"><p id="log"></p></div>
        </div>
        </div>
    </jsp:body>
</t:layout>
<jsp:include page="../jsLoader.jsp"></jsp:include>