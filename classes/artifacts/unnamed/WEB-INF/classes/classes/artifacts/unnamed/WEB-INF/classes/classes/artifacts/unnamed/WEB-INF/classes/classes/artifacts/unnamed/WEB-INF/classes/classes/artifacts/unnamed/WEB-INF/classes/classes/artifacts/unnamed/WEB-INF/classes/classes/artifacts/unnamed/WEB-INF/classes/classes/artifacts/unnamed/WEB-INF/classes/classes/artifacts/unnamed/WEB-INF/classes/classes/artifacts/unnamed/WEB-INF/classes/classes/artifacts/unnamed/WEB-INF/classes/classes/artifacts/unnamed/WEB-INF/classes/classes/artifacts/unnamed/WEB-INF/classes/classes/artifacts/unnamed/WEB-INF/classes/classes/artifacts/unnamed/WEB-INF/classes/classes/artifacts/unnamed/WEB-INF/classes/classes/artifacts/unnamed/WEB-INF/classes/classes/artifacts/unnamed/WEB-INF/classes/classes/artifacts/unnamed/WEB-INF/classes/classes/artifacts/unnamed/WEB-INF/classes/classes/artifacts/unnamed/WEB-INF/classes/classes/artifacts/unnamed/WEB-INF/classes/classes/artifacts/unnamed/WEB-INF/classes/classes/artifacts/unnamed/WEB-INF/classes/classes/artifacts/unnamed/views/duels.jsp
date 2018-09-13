<%@ page import="app.models.UserHero" %><%--
  Created by IntelliJ IDEA.
  User: javaboy
  Date: 9/11/18
  Time: 4:49 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../styles/normalize.css">
    <link rel="stylesheet" href="../styles/duels.css">
</head>
<body>

<div class="container">
    <div class="menu">
        <div class="characteristic">
            <h2> Ваши характеристики: </h2>
            <%
                UserHero userHero = (UserHero) request.getAttribute("userHero");
                out.println("<div>Rating: " + userHero.getRating() +"</div>" +
                        "<div>Damage: " + userHero.getDamageMultiplier() +"</div>");

            %>
        </div>
        <button onclick="location.href= '/duelfield'">Начать дуэль</button>
    </div>
</div>
</body>
</html>
