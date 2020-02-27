<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    String message = pageContext.getException().getMessage();
    String exception = pageContext.getException().getClass().toString();
%>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/style/style.css"/>
</head>
<body>
<header>
    <ul id="menu">
        <li id="btn"><a href="index.html">GO TO THE MAIN PAGE</a></li>
    </ul>
</header>
<h2>Error occurred while processing the request</h2>
<!--<p>Type: <%=exception%></p>-->
<p><%=message%></p>
<footer>
    <a href="mailto:imekov.nikita@gmail.com">Imekov 'DraikoN' Nikita did it.</a>
</footer>
</body>
</html>
