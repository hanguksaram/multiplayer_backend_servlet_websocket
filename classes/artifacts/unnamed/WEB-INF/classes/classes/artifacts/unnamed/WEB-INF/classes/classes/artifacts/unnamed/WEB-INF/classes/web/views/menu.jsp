<%--
  Created by IntelliJ IDEA.
  User: javaboy
  Date: 9/10/18
  Time: 4:07 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../styles/normalize.css">
    <link rel="stylesheet" href="../styles/menu.css">
</head>
<body>
<%
    String user = null;
    if(session.getAttribute("user") == null){
        response.sendRedirect("/");
    }
%>
    <div class="container">
        <div class="menu">
            <button onclick="location.href= '/duels'">Дуэли</button>
            <button onclick="location.href= '/' + '?logout=true'">Выход</button>
        </div>
    </div>
</body>
</html>
