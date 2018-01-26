<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.toString()}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="${pageContext.request.contextPath}/messages"/>
<html>
<head>
    <title><fmt:message key="label.title"/></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/flag-icon-css-master/css/flag-icon.css">
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <div class="navbar-header">
            <a class="navbar-brand" href="${home}"><span class="glyphicon glyphicon-home"></span> <fmt:message key="label.title"/></a>
        </div>
        <ul class="nav navbar-nav">
            <li class="active"><a href="controller?command=show_users"><fmt:message key="label.users"/></a></li>
            <li><a href="#"><fmt:message key="label.trips"/></a></li>
        </ul>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <span class="user-name">${currentUser.name}</span>
                <input type="hidden" name="userId" value="${currentUser.id}">
                <img src="${pageContext.request.contextPath}/ajaxController?command=photo&amp;photo=${currentUser.photoPath}&amp;userId=${currentUser.id}"  id="header-photo" width="30px" height="30px"/>
            </li>
            <li>
                <c:url var="logout" value="controller">
                    <c:param name="command" value="logout"/>
                </c:url>
                <a href="${logout}"><span class="glyphicon glyphicon-log-out"></span> <fmt:message key="label.logout"/></a>
            </li>
            <li class="lang">
                <select id="language" name="language" onchange="submit()" class="selectpicker show-tick" data-width="95px">
                    <option data-content='<span class="flag-icon flag-icon-us"></span> En' value="en" ${language == 'en' ? 'selected' : ''}>En</option>
                    <option data-content='<span class="flag-icon flag-icon-ru"></span> Ru' value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Ru</option>
                </select>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>
