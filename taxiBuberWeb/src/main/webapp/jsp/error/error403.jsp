<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.toString()}" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="${pageContext.request.contextPath}/messages"/>
<html>
<head>
    <title>Error</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/fontawesome-free-5.0.4/web-fonts-with-css/css/fontawesome-all.css">
</head>
<body>
<div>
    <h1 class="h1-error">403 <i class="fa fa-exclamation-circle fa-5" aria-hidden="true"></i></h1>
    <p class="p-error">
        <fmt:message key="label.403"/><br>
        <a href="${home}" title="Back to home" class="a-error"><fmt:message key="label.home"/></a>
    </p>
</div>
</body>
</html>
