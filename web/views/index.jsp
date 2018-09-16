<%@ page import="app.dtos.UserDtoResponse" %><%--
  Created by IntelliJ IDEA.
  User: javaboy
  Date: 9/10/18
  Time: 1:34 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="bodyContent">
  <% UserDtoResponse user = (UserDtoResponse)session.getAttribute("user");
    if (user == null){
      out.println("<form class='login_form' method='post'>" +
              "<input type=\"text\" name=\"login\" placeholder=\"Name\" maxlength=\"45\"/>" +
              " <input type=\"password\" name=\"password\" placeholder=\"Password\"/>");

      String error = (String)request.getAttribute("error");
      if (error != null && error.length() != 0) {
        out.println("<div class='error_message'>" + error + "</div>");
      }
      out.println("<input type=\"submit\" value=\"Sign in or register\"/>");
    }
    else {
      out.println("<h1>Добро пожаловать, " + user.getName() + "</h1>");
    }
  %>
</c:set>

<t:layout>


  <jsp:body>
  <div class="container">
    <div>
        ${bodyContent}
      </form>
    </div>
  </div>
  </jsp:body>
</t:layout>

