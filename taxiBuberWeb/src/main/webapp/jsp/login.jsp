<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : 'en'}" scope="session"/>
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="${pageContext.request.contextPath}/messages"/>
<html>
    <head>
        <title><fmt:message key="label.title"/></title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/flag-icon-css-master/css/flag-icon.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/fontawesome-free-5.0.4/web-fonts-with-css/css/fontawesome-all.css">
        <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/js/login_page.js"></script>
        <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    </head>
    <body>
        <nav class="navbar navbar-inverse" style="position: inherit; padding: 9px">
            <div class="container-fluid">
                <div class="navbar-header">
                    <c:url var="home" value="/index.jsp" scope="application">
                    </c:url>
                    <a class="navbar-brand" href="${home}"><span class="glyphicon glyphicon-home"></span> <fmt:message key="label.title"/></a>
                </div>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <a href="#" data-toggle="modal" data-target="#modal-signup"><span class="glyphicon glyphicon-user"></span> <fmt:message key="label.signup"/></a>
                    </li>
                    <li><a href="#" data-toggle="modal" data-target="#myModal"><span class="glyphicon glyphicon-log-in"></span> <fmt:message key="label.login"/></a></li>
                    <li class="lang">
                        <form action="../index.jsp" method="post">
                            <select id="language" name="language" onchange="submit()" class="selectpicker show-tick" data-width="fit">
                                <option data-content='<span class="flag-icon flag-icon-ru"></span> Ru' value="ru" ${language == 'ru' ? 'selected' : ''}>Ru</option>
                                <option data-content='<span class="flag-icon flag-icon-us"></span> En' value="en" ${language == 'en' ? 'selected' : ''}>En</option>
                            </select>
                        </form>
                    </li>
                </ul>
            </div>
        </nav>
        <div id="login-content" class="background-login"></div>
        <div class="footer">
            <div class="footer-content">&copy; 2017.EPAM Systems Taxi Buber</div>
        </div>
        <!-- Modal sign in-->
        <div class="modal fade" id="myModal" role="dialog" >
            <div class="modal-dialog">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close cancel" data-dismiss="modal" style="color:white;">&times;</button>
                        <h4 style="font-size: 35px;"><span class="glyphicon glyphicon-lock" style="font-size: 35px;"></span><fmt:message key="label.enter"/></h4>
                    </div>
                    <div class="modal-body" style="padding:40px 50px;">
                        <form role="form" id="jform" action="controller" method="post">
                            <input type="hidden" name="command"  value="login">
                            <div class="error" id="error-login">${errorLoginPassMessage}</div>
                            <div class="form-group">
                                <label for="username"><i class="fa fa-envelope" aria-hidden="true"></i> <fmt:message key="label.email"/>:</label>
                                <input type="email" name="email" class="form-control" id="username" placeholder="Enter email" required="required">
                                <div style="display: none" id="max-email" class="err"><fmt:message key="label.maxemail"/></div>
                                <div style="display: none" id="blank-email" class="err"><fmt:message key="label.blank"/></div>
                                <div style="display: none" id="invalid-email" class="err"><fmt:message key="label.invalidemail"/></div>
                            </div>
                            <div class="form-group">
                                <label for="psw"><span class="glyphicon glyphicon-eye-open"></span> <fmt:message key="label.password"/>:</label>
                                <input type="password" name="password" class="form-control" id="psw" placeholder="Enter password" required="required">
                                <div style="display: none" id="max-psw" class="err"><fmt:message key="label.maxpsw"/></div>
                                <div style="display: none" id="blank-psw" class="err"><fmt:message key="label.blank"/></div>
                                <div style="display: none" id="invalid-psw" class="err"><fmt:message key="label.invalidpsw"/></div>
                            </div>
                            <button type="submit" class="btn btn-success btn-block" id="submit-button" disabled><span class="glyphicon glyphicon-off"> </span><fmt:message key="label.login"/></button>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="submit" class="btn btn-danger btn-default pull-left cancel" data-dismiss="modal"><span class="glyphicon glyphicon-remove"></span><fmt:message key="button.cancel" /></button>
                        <p><fmt:message key="label.member"/> <a href="#" class="cancel" data-dismiss="modal" data-toggle="modal" data-target="#modal-signup"> <fmt:message key="label.signup"/></a></p>
                        <p><fmt:message key="label.forgot"/><a href="#" class="cancel" data-dismiss="modal" data-toggle="modal" data-target="#forgot-password"> <fmt:message key="label.password"/>?</a></p>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal singup-->
        <div class="modal fade" id="modal-signup" role="dialog">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header" style="height: 95px">
                        <button type="button" class="close cancel-signup" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title" style="font-size: 35px;"><span class="glyphicon glyphicon-user" style="font-size: 35px;"></span> <fmt:message key="label.signup"/></h4>
                    </div>
                    <div class="modal-body">
                        <div class="container" style="margin-bottom: -20px;">
                            <div class="row" style="margin-top: -95px;">
                                <input id="is-errors" type="hidden" value="${isErrors}">
                                <div class="col-sm-3 text-center">
                                    <div>
                                        <input type='file' id="imgInp" style="display: none" name="photo" form="saveUserForm"/>
                                        <a href="javascript:{}" id="img">
                                            <img id="blah" src="${pageContext.request.contextPath}/ajaxController?command=photo&amp;photo=${user.photoPath}" style="border: 1px solid lightgray;" width="280" height="280"  />
                                        </a>
                                    </div>
                                </div>
                                <div class="col-sm-9 text-center">
                                    <form class="form-horizontal" action="controller" method="post" enctype="multipart/form-data" id="saveUserForm">
                                        <input type="hidden" name="command" value="save_user">
                                        <div class="form-group required">
                                            <label class="control-label col-sm-4" for="name"><fmt:message key="label.name"/>:</label>
                                            <div class="col-sm-8">
                                                <input type="text" class="form-control" id="name" name="name" value="${user.name}" placeholder="Enter name"/>
                                                <div style="display: none" id="error-name" class="err"><fmt:message key="label.errorname"/></div>
                                                <div style="display: none" id="blank-name" class="err"><fmt:message key="label.blank"/></div>
                                                <div class="err2">${errors.name}</div>
                                                <div class="err2">${errors.nameBlank}</div>
                                            </div>
                                        </div>
                                        <div class="form-group required">
                                            <label class="control-label col-sm-4" for="surname"><fmt:message key="label.surname"/>:</label>
                                            <div class="col-sm-8">
                                                <input type="text" class="form-control" id="surname" name="surname" value="${user.surname}" placeholder="Enter surname"/>
                                                <div style="display: none" id="error-surname" class="err"><fmt:message key="label.errorname"/></div>
                                                <div style="display: none" id="blank-surname" class="err"><fmt:message key="label.blank"/></div>
                                                <div class="err2">${errors.surname}</div>
                                                <div class="err2">${errors.surnameBlank}</div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-4" for="patronymic"><fmt:message key="label.patronymic"/>:</label>
                                            <div class="col-sm-8">
                                                <input type="text" class="form-control" id="patronymic" name="patronymic" value="${user.patronymic}" placeholder="Enter patronymic"/>
                                                <div style="display: none" id="error-patronymic" class="err"><fmt:message key="label.errorname"/></div>
                                                <div class="err2">${errors.patronymic}</div>
                                            </div>
                                        </div>
                                        <div class="form-group required">
                                            <label class="control-label col-sm-4" for="email"><fmt:message key="label.email"/>:</label>
                                            <div class="col-sm-8">
                                                <input type="email" class="form-control" id="email" name="email" value="${user.email}" placeholder="Enter email">
                                                <div style="display: none" id="error-email" class="err"><fmt:message key="label.invalidemail"/></div>
                                                <div style="display: none" id="email-blank" class="err"><fmt:message key="label.blank"/></div>
                                                <div style="display: none" id="email-size" class="err"><fmt:message key="label.maxemail"/></div>
                                                <div class="err2">${errors.email}</div>
                                                <div class="err2">${errors.emailSize}</div>
                                                <div class="err2">${errors.emailBlank}</div>
                                                <div class="err2">${errors.notunique}</div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-4" for="phone"><fmt:message key="label.phone"/>:</label>
                                            <div class="col-sm-8">
                                                <input type="text" class="form-control" id="phone" name="phone" value="${user.phone}" placeholder="X(XXX)XXX-XX-XX">
                                                <div style="display: none" id="error-phone" class="err"><fmt:message key="label.invalidphone"/></div>
                                                <div class="err2">${errors.phone}</div>
                                            </div>
                                        </div>
                                        <div class="form-group required">
                                            <label class="control-label col-sm-4" for="role"><fmt:message key="label.role"/>:</label>
                                            <div class="col-sm-8">
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
                                                    <c:if test="${user.role == null}">
                                                        <option value="" selected="selected">not chosen</option>
                                                        <option value="DRIVER">DRIVER</option>
                                                        <option value="CLIENT">CLIENT</option>
                                                    </c:if>
                                                </select>
                                                <div class="err2">${errors.role}</div>
                                            </div>
                                        </div>
                                        <div class="form-group">
                                            <label class="control-label col-sm-4" for="birthday"><fmt:message key="label.birthday"/>:</label>
                                            <div class="col-sm-8">
                                                <input type="text" class="form-control" id="birthday" name="birthday" value="${birthday}" placeholder="yyyy-mm-dd"/>
                                                <div style="display: none" id="error-birthday" class="err"><fmt:message key="label.wrongdate"/></div>
                                                <div class="err2">${errors.date}</div>
                                            </div>
                                        </div>
                                        <div class="form-group required" id="div-pwd">
                                            <label class="control-label col-sm-4" for="password"><fmt:message key="label.password"/>:</label>
                                            <div class="col-sm-8">
                                                <input type="password" class="form-control" id="password" name="password" placeholder="Enter password">
                                                <div style="display: none" id="error-password" class="err"><fmt:message key="label.invalidpsw"/></div>
                                                <div style="display: none" id="blank-password" class="err"><fmt:message key="label.blank"/></div>
                                                <div class="err2">${errors.password}</div>
                                                <div class="err2">${errors.passwordBlank}</div>
                                            </div>
                                        </div>
                                        <div class="form-group required" id="div-pwd-repeat">
                                            <label class="control-label col-sm-4" for="repeat"><fmt:message key="label.passwordconfirm"/>:</label>
                                            <div class="col-sm-8">
                                                <input type="password" class="form-control" id="repeat" name="repeat" placeholder="Confirm password">
                                                <div style="display: none" id="repeat-password" class="err"><fmt:message key="label.notmatch"/></div>
                                                <div class="err2">${errors.passwordConfirm}</div>
                                            </div>
                                        </div>
                                        <div class="form-group" id="save">
                                            <div class="col-sm-offset-2 col-sm-4">
                                                <button type="submit" id="save-button" class="btn btn-primary" form="saveUserForm">
                                                    <i class="far fa-save"></i> <fmt:message key="button.save" />
                                                </button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default cancel-signup" data-dismiss="modal"><fmt:message key="button.cancel" /></button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Modal forgot password-->
        <div class="modal fade" id="forgot-password" role="dialog">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close can" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title" style="font-size: 30px"><i class="far fa-envelope"></i> <fmt:message key="label.send"/></h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal" action="controller" method="post" id="forgotPasswordForm">
                            <input type="hidden" name="command" value="forgot">
                            <div class="error" id="wrong-email">${wrongEmail}</div>
                            <div class="form-group">
                                <label class="control-label col-sm-2" for="email" style="margin-left: 12px;"><fmt:message key="label.email"/>:</label>
                                <div class="col-sm-9">
                                    <input type="email" class="form-control" name="emailForgot" id="email-forgot" placeholder="Enter email">
                                    <div style="display: none" id="error-forgot" class="err"><fmt:message key="label.invalidemail"/></div>
                                    <div style="display: none" id="forgot-blank" class="err"><fmt:message key="label.blank"/></div>
                                    <div style="display: none" id="forgot-size" class="err"><fmt:message key="label.maxemail"/></div>
                                </div>
                            </div>
                            <div class="form-group">
                                <div class="col-sm-offset-4 col-sm-6">
                                    <button type="submit" class="btn btn-success" id="send-button"><i class="fas fa-paper-plane"></i> <fmt:message key="label.send"/></button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default can" data-dismiss="modal"><fmt:message key="button.cancel"/></button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal message-->
        <div class="modal fade" id="modal-message2" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content modal-message">
                    <div class="modal-body modal-message-body">
                        <div id="message-update" style="display: none">${sentPassword}</div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>