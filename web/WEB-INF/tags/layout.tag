<%@ tag import="app.services.RenderPageLogger" %>
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
        long requestTime = (long)request.getAttribute("requestTime");
        long pageRenderTime = RenderPageLogger.calculatePageRenderTime(requestTime);
        out.println("Page: " +pageRenderTime + " ms");
    %>
</footer>
</body>
</html>