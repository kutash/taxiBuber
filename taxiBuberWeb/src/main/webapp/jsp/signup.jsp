<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.toString()}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="${pageContext.request.contextPath}/messages"/>
<html>
<head>
    <title><fmt:message key="label.title"/></title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <link rel="stylesheet" href="../css/app.css">
    <script src="../js/jquery.js"></script>
    <script src="../js/user.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="${home}"><span class="glyphicon glyphicon-home"></span> <fmt:message key="label.title"/></a>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li class="lang">
                    <form action="signup.jsp" method="post">
                        <select id="language" name="language" onchange="submit()" class="selectpicker show-tick" data-width="100px">
                            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>English</option>
                            <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Русский</option>
                        </select>
                    </form>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <div class="row">
            <div class="before-form"></div>
            <div class="col-sm-4 text-center">
                <div>
                    <input type='file' id="imgInp" style="display: none"/>
                    <a href="javascript:{}" id="img">
                        <img id="blah" src="${pageContext.request.contextPath}/controller?command=photo&amp;photo=${user.photoPath}" alt="your image" width="300" height="300"  />
                    </a>
                </div>
            </div>
            <div class="col-sm-7 text-center">
                <form class="form-horizontal" action="controller" method="post" enctype="multipart/form-data" id="saveUserForm">
                    <input type="hidden" name="command" value="save_user">
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="name"><fmt:message key="label.name"/>:</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="name" value="${user.name}" placeholder="Enter name"/>
                        </div>
                        <div style="display: none" id="error-name" class="err"><fmt:message key="label.errorname"/></div>
                        <div class="err">${errorName}</div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="surname"><fmt:message key="label.surname"/>:</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="surname" name="surname" value="${user.surname}" placeholder="Enter surname"/>
                        </div>
                        <div style="display: none" id="error-surname" class="err"><fmt:message key="label.errorname"/></div>
                        <div class="err">${errorSurname}</div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="patronymic"><fmt:message key="label.patronymic"/>:</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="patronymic" name="patronymic" value="${user.patronymic}" placeholder="Enter patronymic"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="birthday"><fmt:message key="label.birthday"/>:</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="birthday" name="birthday" value="${user.birthday}" placeholder="yyyy-mm-dd"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="phone"><fmt:message key="label.phone"/>:</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="phone" name="phone" value="${user.phone}" placeholder="X(XXX)XXX-XX-XX">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="email"><fmt:message key="label.email"/>:</label>
                        <div class="col-sm-6">
                            <input type="email" class="form-control" id="email" name="email" value="${user.email}" placeholder="Enter email">
                        </div>
                    </div>
                    <div class="form-group" id="div-pwd">
                        <label class="control-label col-sm-3" for="pwd"><fmt:message key="label.password"/>:</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="pwd" name="password" placeholder="Enter password">
                        </div>
                    </div>
                    <div class="form-group" id="div-pwd-repeat">
                        <label class="control-label col-sm-3" for="repeat">Confirm Password:</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="repeat" name="repeat" placeholder="Confirm password">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-sm-3" for="role"><fmt:message key="label.role"/>:</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="role" name="role">
                                <c:if test="${user.role == 'DRIVER'}">
                                    <option value="">not chosen</option>
                                    <option selected="selected" value="DRIVER">DRIVER</option>
                                    <option value="CLIENT">CLIENT</option>
                                </c:if>
                                <c:if test="${user.role == 'CLIENT'}">
                                    <option value="">not chosen</option>
                                    <option value="DRIVER">DRIVER</option>
                                    <option selected="selected" value="CLIENT">CLIENT</option>
                                </c:if>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" id="submit-button">
                        <div class="col-sm-offset-2 col-sm-6">
                            <button type="submit" class="btn btn-default">Submit</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="footer">
        <div class="footer-content">&copy; 2017.EPAM Systems Taxi Buber</div>
    </div>
    </body>
</html>