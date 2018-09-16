<%@ page import="app.models.UserHero" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="bodyContent">
    <%
        UserHero userHero = (UserHero) request.getAttribute("userHero");
        out.println("<div>Рейтинг: " + userHero.getRating() +"</div>" +
                "<div>Урон: " + userHero.getDamageMultiplier() +"</div>" +
                "<div>Здоровье: " + userHero.getHealth() +"</div>");

    %>
</c:set>
<t:layout>
    <jsp:body>
        <div class="container">
            <div class="menu">
                <div class="characteristic">
                    <h2> Ваши характеристики: </h2>
                        ${bodyContent}
                </div>
                <button onclick="location.href= '/duelfield'">Начать дуэль</button>
            </div>
        </div>
    </jsp:body>
</t:layout>