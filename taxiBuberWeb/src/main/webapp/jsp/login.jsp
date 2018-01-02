<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.toString()}" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="${pageContext.request.contextPath}/messages"/>
<html>
<head>
    <title><fmt:message key="label.title"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="../css/app.css">
    <script src="../js/jquery.js"></script>
    <script src="../js/login_page.js"></script>
    <script src="../js/bootstrap.min.js"></script>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
    <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
</head>
<body>
<nav class="navbar navbar-inverse" style="position: inherit">
    <div class="container-fluid">
        <div class="navbar-header">
            <c:url var="home" value="/index.jsp" scope="application">
            </c:url>
            <a class="navbar-brand" href="${home}"><span class="glyphicon glyphicon-home"></span> <fmt:message key="label.title"/></a>
        </div>
        <ul class="nav navbar-nav navbar-right">
            <li><a href="../jsp/signup.jsp"><span class="glyphicon glyphicon-user"></span> <fmt:message key="label.signup"/></a></li>
            <li><a href="#" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-log-in"></span> <fmt:message key="label.login"/></a></li>
            <li class="lang">
                <form action="../index.jsp" method="post">
                    <select id="language" name="language" onchange="submit()" class="selectpicker show-tick" data-width="100px">
                        <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>English</option>перделать по умолчанию
                        <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Русский</option>
                    </select>
                </form>
            </li>
        </ul>
        <!-- Modal -->
        <div class="modal fade" id="myModal" role="dialog" >
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close cancel" data-dismiss="modal" style="color:white;">X</button>
                        <h4 style="font-size: 35px;"><span class="glyphicon glyphicon-lock" style="font-size: 35px;"></span><fmt:message key="label.enter"/></h4>
                    </div>
                    <div class="modal-body" style="padding:40px 50px;">
                        <form role="form" id="jform" action="controller" method="post">
                            <input type="hidden" name="command"  value="login">
                            <div class="error" id="error-login">${errorLoginPassMessage}</div>
                            <div class="form-group">
                                <label for="username"><span class="glyphicon glyphicon-user"></span><fmt:message key="label.email"/>:</label>
                                <input type="email" name="email" class="form-control" id="username" placeholder="Enter email">
                                <div style="display: none" id="max-email" class="err"><fmt:message key="label.maxemail"/></div>
                                <div style="display: none" id="blank-email" class="err"><fmt:message key="label.blank"/></div>
                                <div style="display: none" id="invalid-email" class="err"><fmt:message key="label.invalidemail"/></div>
                            </div>
                            <div class="form-group">
                                <label for="psw"><span class="glyphicon glyphicon-eye-open"></span><fmt:message key="label.password"/>:</label>
                                <input type="password" name="password" class="form-control" id="psw" placeholder="Enter password">
                                <div style="display: none" id="max-psw" class="err"><fmt:message key="label.maxpsw"/></div>
                                <div style="display: none" id="blank-psw" class="err"><fmt:message key="label.blank"/></div>
                                <div style="display: none" id="invalid-psw" class="err"><fmt:message key="label.invalidpsw"/></div>
                            </div>
                            <button type="submit" class="btn btn-success btn-block" id="submit-button" disabled><span class="glyphicon glyphicon-off"></span><fmt:message key="label.login"/></button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger btn-default pull-left cancel" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span><fmt:message key="button.cancel" /></button>
                        <p><fmt:message key="label.member"/> <a href="#"><fmt:message key="label.signup"/></a></p>
                        <p><fmt:message key="label.forgot"/><a href="#"><fmt:message key="label.password"/></a></p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</nav>
<div id="login-content" class="background-login"></div>
<div class="footer">
    <div class="footer-content">&copy; 2017.EPAM Systems Taxi Buber</div>
</div>
</body>
</html>