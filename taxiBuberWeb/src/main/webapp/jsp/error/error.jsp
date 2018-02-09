<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/fontawesome-free-5.0.4/web-fonts-with-css/css/fontawesome-all.css">
</head>
<body>

<br/>

<h1 class="h1-error">Error <i class="fa fa-exclamation-circle fa-5" aria-hidden="true"></i></h1>
<p class="p-error">
    ${wrongAction}<br>
    ${nullPage}<br>
    <a href="/index.jsp" title="Back to home" class="a-error">BACK TO HOME</a>
</p>
</body>
</html>
