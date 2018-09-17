<%@ tag import="app.services.RenderPageLogger" %>
<%@ tag import="java.util.Optional" %>
<%@tag description="Template" pageEncoding="UTF-8"%>
<%@attribute name="footer" fragment="true" %>
<html>
<head>
    <title>test game</title>

    <link type="text/css" rel="stylesheet" href="../../styles/normalize.css">
    <link type="text/css" rel="stylesheet" href="../../styles/index.css">
</head>
<body>
<div id="body">
    <jsp:doBody/>
</div>
<footer id="pagefooter">
    <%
        Optional<String> sqlInfo = Optional.ofNullable((String)session.getAttribute("sqlLog"));
        long requestTime = (long)request.getAttribute("requestTime");
        long pageRenderTime = RenderPageLogger.calculatePageRenderTime(requestTime);
        out.println("Page: " +pageRenderTime + " ms " + sqlInfo.orElse(""));

    %>
</footer>
</body>
</html>