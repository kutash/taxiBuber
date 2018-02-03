<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.toString()}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="${pageContext.request.contextPath}/messages"/>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <c:if test="${currentUser.role == 'ADMIN'}">
            <ul class="nav navbar-nav">
                <li>
                    <c:url var="users" value="controller">
                        <c:param name="command" value="show_users"/>
                    </c:url>
                    <a href="${users}"><fmt:message key="label.users"/></a>
                </li>
                <li>
                    <c:url var="trips" value="controller">
                        <c:param name="command" value="trips"/>
                    </c:url>
                    <a href="${trips}"><fmt:message key="label.trips"/></a>
                </li>
                <li>
                    <input type="text" id="end" name="end" class="form-control" aria-label="..." style="margin-top: 10px;" placeholder="Search by">
                    <button type="button" id="search" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false" style=" margin-top: -34px;margin-left: 211px;height: 34px;width: 60px;">
                        <i class="fas fa-angle-down"></i></button>
                    <ul class="dropdown-menu dropdown-menu-right">
                        <li><a href="#" class="address-link">email</a></li>
                        <li><a href="#" class="address-link">name</a></li>
                    </ul>
                </li>
            </ul>
        </c:if>
        <c:if test="${currentUser.role == 'DRIVER' || currentUser.role == 'CLIENT'}">
            <ul class="nav navbar-nav">
                <li>
                    <c:url var="main" value="controller">
                        <c:param name="command" value="main"/>
                    </c:url>
                    <a href="${main}"><fmt:message key="label.order"/></a>
                </li>
                <li>
                    <c:url var="edit" value="controller">
                        <c:param name="command" value="edit"/>
                        <c:param name="userId" value="${currentUser.id}"/>
                    </c:url>
                    <a href="${edit}"><fmt:message key="label.profile"/></a>
                </li>
                <li>
                    <c:url var="trips" value="controller">
                        <c:param name="command" value="trips"/>
                    </c:url>
                    <a href="${trips}"><fmt:message key="label.trips"/></a>
                </li>
            </ul>
        </c:if>
        <ul class="nav navbar-nav navbar-right">
            <li>
                <span class="user-name">${currentUser.name}</span>
                <img src="${pageContext.request.contextPath}/ajaxController?command=photo&amp;photo=${currentUser.photoPath}&amp;userId=${currentUser.id}"  id="header-photo" width="30px" height="30px"/>
            </li>
            <li>
                <c:url var="logout" value="controller">
                    <c:param name="command" value="logout"/>
                </c:url>
                <a href="${logout}"><span class="glyphicon glyphicon-log-out"></span> <fmt:message key="label.logout"/></a>
            </li>
            <li class="lang">
                <select id="language" name="language" onchange="submit()" class="selectpicker show-tick" data-width="fit" form="l">
                    <option data-content='<span class="flag-icon flag-icon-ru"></span> Ru' value="ru" ${language == 'ru' ? 'selected' : ''}>Ru</option>
                    <option data-content='<span class="flag-icon flag-icon-us"></span> En' value="en" ${language == 'en' ? 'selected' : ''}>En</option>
                </select>
            </li>
        </ul>
    </div>
</nav>
</body>
</html>
