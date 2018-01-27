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
        <link rel="stylesheet" href="../../css/app.css">
        <script src=".${pageContext.request.contextPath}/js/jquery.js"></script>
        <script src="${pageContext.request.contextPath}/js/user.js"></script>
        <script src="${pageContext.request.contextPath}/js/car.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
    <c:if test="${currentUser.role == 'CLIENT'}">
        <jsp:include page="/jsp/user/header.jsp"/>
    </c:if>
    <c:if test="${currentUser.role == 'ADMIN'}">
        <jsp:include page="/jsp/admin/header.jsp"/>
    </c:if>
    <c:if test="${currentUser.role == 'DRIVER'}">
        <jsp:include page="/jsp/user/driver_header.jsp"/>
    </c:if>
    <c:url var="switchLanguage" value="controller" scope="page">
        <c:param name="command" value="edit"/>
        <c:param name="userId" value="${user.id}"/>
    </c:url>
    <form action="${switchLanguage}" method="post" id="l"></form>
        <div class="container">
            <div class="row">
                <div class="before-form">${currentUser.role}</div>
                <div class="col-sm-4 text-center" style="margin-left: 91px">
                    <div>
                        <input type='file' id="imgInp" style="display: none" name="photo" form="saveUserForm" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}/>
                        <a href="javascript:{}" id="img">
                            <img id="blah" src="${pageContext.request.contextPath}/ajaxController?command=photo&amp;photo=${user.photoPath}&amp;userId=${user.id}" alt="your image" width="300" height="300"  />
                        </a>
                        <div class="rating">
                            <h1 id="rating">${user.rating}</h1>
                            <div class="productRate">
                                <div class="productRate-div" id="str"></div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-7 text-center">
                    <form class="form-horizontal" action="controller" method="post" enctype="multipart/form-data" id="saveUserForm">
                        <input type="hidden" name="command" value="update_user">
                        <input type="hidden" name="userId" value="${user.id}" id="user-id">
                        <input type="hidden" id="user-photo" value="${user.photoPath}">
                        <div class="form-group required">
                            <label class="control-label col-sm-3" for="name"><fmt:message key="label.name"/>:</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="name" name="name" value="${user.name}" placeholder="Enter name" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}/>
                                <div style="display: none" id="error-name" class="err"><fmt:message key="label.errorname"/></div>
                                <div style="display: none" id="blank-name" class="err"><fmt:message key="label.blank"/></div>
                                <div class="err2">${errors.name}</div>
                                <div class="err2">${errors.nameBlank}</div>
                            </div>
                        </div>
                        <div class="form-group required">
                            <label class="control-label col-sm-3" for="surname"><fmt:message key="label.surname"/>:</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="surname" name="surname" value="${user.surname}" placeholder="Enter surname" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}/>
                                <div style="display: none" id="error-surname" class="err"><fmt:message key="label.errorname"/></div>
                                <div style="display: none" id="blank-surname" class="err"><fmt:message key="label.blank"/></div>
                                <div class="err2">${errors.surname}</div>
                                <div class="err2">${errors.surnameBlank}</div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="patronymic"><fmt:message key="label.patronymic"/>:</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="patronymic" name="patronymic" value="${user.patronymic}" placeholder="Enter patronymic" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}/>
                                <div style="display: none" id="error-patronymic" class="err"><fmt:message key="label.errorname"/></div>
                                <div class="err2">${errors.patronymic}</div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="birthday"><fmt:message key="label.birthday"/>:</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="birthday" name="birthday" value="${not empty birthday ? birthday : user.birthday}" placeholder="yyyy-mm-dd" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}/>
                                <div style="display: none" id="error-birthday" class="err"><fmt:message key="label.wrongdate"/></div>
                                <div class="err2">${errors.date}</div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="phone"><fmt:message key="label.phone"/>:</label>
                            <div class="col-sm-6">
                                <input type="text" class="form-control" id="phone" name="phone" value="${user.phone}" placeholder="X(XXX)XXX-XX-XX" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}>
                                <div style="display: none" id="error-phone" class="err"><fmt:message key="label.invalidphone"/></div>
                                <div class="err2">${errors.phone}</div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="email"><fmt:message key="label.email"/>:</label>
                            <div class="col-sm-6">
                                <input type="email" class="form-control" id="email" name="email" value="${user.email}" placeholder="Enter email" disabled>
                            </div>
                        </div>
                        <c:if test="${currentUser.role == 'DRIVER' || currentUser.role == 'CLIENT'}">
                            <div class="form-group" id="password-group">
                                <label class="control-label col-sm-3" for="email"><fmt:message key="label.password"/>:</label>
                                <div class="col-sm-6" style="margin-top: 7px">
                                    <a href="#" data-toggle="modal" data-target="#myModal" id="change-psw" style="font-size: 15px"><fmt:message key="label.changepassword"/></a>
                                </div>
                            </div>
                            <div class="form-group" style="margin-top: 55px;">
                                <div class="col-sm-offset-2 col-sm-6">
                                    <button type="submit" id="save-button" class="btn btn-success btn-md" form="saveUserForm"><fmt:message key="button.save"/></button>
                                    <button class="btn btn-danger btn-md" id="cancel-button"><fmt:message key="button.cancel"/></button>
                                </div>
                            </div>
                        </c:if>
                        <c:if test="${currentUser.role == 'ADMIN'}">
                            <div class="form-group">
                                <label class="control-label col-sm-3" for="role" id="label-role"><fmt:message key="label.role"/>:</label>
                                <div class="col-sm-6">
                                    <select class="form-control" id="role" name="role" disabled>
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
                                    <div class="err2">${errors.role}</div>
                                </div>
                            </div>
                            <c:if test="${user.role == 'DRIVER'}">
                                <div class="form-group">
                                    <label class="control-label col-sm-3" for="email"><fmt:message key="label.car"/>:</label>
                                    <div class="col-sm-6" style="margin-top: 5px">
                                        <a href="#" data-toggle="modal" data-target="#modal-car" id="car" style="font-size: 16px;margin-right: 72%;">${car.brand.name } ${car.model}</a>
                                    </div>
                                </div>
                            </c:if>
                        </c:if>
                    </form>
                </div>
            </div>
            <c:if test="${currentUser.role == 'CLIENT'}">
                <div class="row">
                    <div class="panel-group" id="addresses">
                        <div class="panel panel-default">
                            <div class="panel-heading">
                                <h4 class="panel-title">
                                    <a data-toggle="collapse" href="#collapse1"><fmt:message key="label.myaddresses"/></a>
                                </h4>
                            </div>
                            <div id="collapse1" class="panel-collapse collapse">
                                <ul class="list-group">
                                <c:forEach var="address" items="${addresses}">
                                    <li class="list-group-item" id="${address.id}">
                                        ${address.address}
                                            <input type="hidden" id="${address.id}" value="${address.id}">
                                        <a style="float: right;font-size: 25px" href="javascript:{}" onclick="deleteA(${address.id})"><i class="fa fa-trash" aria-hidden="true"></i></a>
                                    </li>
                                </c:forEach>
                                </ul>
                            </div>
                        </div>
                    </div>
                </div>
            </c:if>
            <c:if test="${comments != null}">
            <div class="row">
                <div class="comment-div">
                    <h1 style="text-align: center"><i class="fa fa-comments-o" aria-hidden="true"></i> <fmt:message key="label.comments"/></h1>
                    <table class="table">
                        <tbody>
                        <c:forEach var="comment" items="${comments}">
                            <tr>
                                <td class="col-md-3" style="padding-left: 50px">
                                    <img src="${pageContext.request.contextPath}/ajaxController?command=photo&amp;photo=${comment.reviewerPhoto}&amp;userId=${comment.reviewerId}" width="70" height="70"  class="comment-photo"/>
                                    <c:out value="${comment.reviewerName}"/><br>
                                        <c:out value="${comment.date}"/>
                                </td>
                                <td class="col-md-6 comment-text"><c:out value="${comment.text}"/><br>
                                    <h4><fmt:message key="label.valuation"/>:</h4>
                                    <div class="productRate" id="mark-div">
                                        <div class="productRate-div" id="mark-str" style="width:<ctg:rating mark="${comment.mark}"/>%"></div>
                                    </div>
                                </td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>
            </c:if>
        </div>

        <div class="footer">
            <div class="footer-content">&copy; 2017.EPAM Systems Taxi Buber</div>
        </div>
        <!-- Modal password-->
        <div class="modal fade" id="myModal" role="dialog">
            <div class="modal-dialog">

                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close cancel" data-dismiss="modal">&times;</button>
                        <h4 style="font-size: 30px;" class="modal-title"><i class="fa fa-key" aria-hidden="true"></i><fmt:message key="label.changepassword"/></h4>
                    </div>
                    <div class="modal-body">
                        <form action="controller" id="changePasswordForm" method="post" class="form-horizontal">
                            <input type="hidden" name="command" value="change_password">
                            <input type="hidden" id="is-password" value="${isPassword}">
                            <div class="form-group required">
                                <label class="control-label col-sm-4" for="old-password"><fmt:message key="label.oldpassword"/>:</label>
                                <div class="col-sm-8">
                                    <input type="password" class="form-control" id="old-password" name="oldPassword" placeholder="Enter password">
                                    <div style="display: none" id="error-old" class="err"><fmt:message key="label.invalidpsw"/></div>
                                    <div style="display: none" id="blank-old" class="err"><fmt:message key="label.blank"/></div>
                                    <div class="err2">${errors.wrongPassword}</div>
                                </div>
                            </div>
                            <div class="form-group required" id="div-pwd">
                                <label class="control-label col-sm-4" for="password"><fmt:message key="label.newpassword"/>:</label>
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
                                <div class="col-sm-offset-5 col-sm-6">
                                    <button type="submit" id="change-password" class="btn btn-success" form="changePasswordForm" disabled><fmt:message key="label.change"/></button>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-danger cancel" data-dismiss="modal"><fmt:message key="button.cancel"/></button>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal message-->
        <div class="modal fade" id="modal-message" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content modal-message">
                    <div class="modal-body modal-message-body">
                        <div id="update-password">${updatePassword}</div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="/jsp/user/modal_car.jsp"/>
    </body>
</html>
