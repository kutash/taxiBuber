<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
        <link rel="stylesheet" href="${pageContext.request.contextPath}css/app.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}css/fontawesome-free-5.0.4/web-fonts-with-css/css/fontawesome-all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}/css/flag-icon-css-master/css/flag-icon.css">
        <script type="text/javascript" src="${pageContext.request.contextPath}js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}js/users.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    </head>
    <body>
    <nav class="navbar navbar-inverse navbar-fixed-top" style="padding-bottom: 2px">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="${home}"><span class="glyphicon glyphicon-home"></span> <fmt:message key="label.title"/></a>
            </div>
            <ul class="nav navbar-nav">
                <li>
                    <c:url var="showUsers" value="controller">
                        <c:param name="command" value="show_users"/>
                    </c:url>
                    <a href="${showUsers}"><fmt:message key="label.users"/></a>
                </li>
                <li>
                    <c:url var="trips" value="controller">
                        <c:param name="command" value="trips"/>
                    </c:url>
                    <a href="${trips}"><fmt:message key="label.trips"/></a>
                </li>
                <li>
                    <form class="navbar-form navbar-search" role="search">
                        <input type="hidden" id="parameter">
                        <div class="input-group">
                            <div class="input-group-btn">
                                <button type="button" class="btn btn-search btn-default dropdown-toggle" data-toggle="dropdown">
                                    <span class="glyphicon glyphicon-search"></span>
                                    <span class="label-icon"><fmt:message key="label.search"/></span>
                                    <span class="caret"></span>
                                </button>
                                <ul class="dropdown-menu pull-left" role="menu">
                                    <li>
                                        <a href="#">
                                            <span class="glyphicon glyphicon-user"></span>
                                            <span class="label-icon"><fmt:message key="label.searchbyname"/></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <i class="fas fa-at"></i>
                                            <span class="label-icon"><fmt:message key="label.searchbyemail"/></span>
                                        </a>
                                    </li>
                                    <li>
                                        <a href="#">
                                            <span class="glyphicon glyphicon-calendar"></span>
                                            <span class="label-icon"><fmt:message key="label.searchbydate"/></span>
                                        </a>
                                    </li>
                                </ul>
                            </div>
                            <input type="text" class="form-control" id="search-input" style="width: 250px;height: 33px;">
                        </div>
                    </form>
                </li>
            </ul>
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
                    <form method="post">
                        <select id="language" name="language" onchange="submit()" class="selectpicker show-tick" data-width="fit">
                            <option data-content='<span class="flag-icon flag-icon-ru"></span> Ru' value="ru" ${language == 'ru' ? 'selected' : ''}>Ru</option>
                            <option data-content='<span class="flag-icon flag-icon-us"></span> En' value="en" ${language == 'en' ? 'selected' : ''}>En</option>
                        </select>
                    </form>
                </li>
            </ul>
        </div>
    </nav>
        <div class="container">
            <div>
                <table class="table table-hover" id="users-table">
                    <thead>
                        <tr>
                            <th></th>
                            <th><fmt:message key="label.fullname"/></th>
                            <th><fmt:message key="label.rating"/></th>
                            <th><fmt:message key="label.role"/></th>
                            <th><fmt:message key="label.email"/></th>
                            <th><fmt:message key="label.birthday"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="content">
                        <c:forEach var="user" items="${users}">
                            <tr class="${user.status == 'BANNED' ? 'banned' : ''}" id="tr${user.id}">
                                <td class="users">
                                    <img id="blah" src="${pageContext.request.contextPath}/ajaxController?command=photo&amp;photo=${user.photoPath}&amp;userId=${user.id}" width="55" height="55"  />
                                </td>
                                <td class="name users">
                                    <c:url var="edit" value="controller">
                                        <c:param name="command" value="edit"/>
                                        <c:param name="userId" value="${user.id}"/>
                                    </c:url>
                                    <a href="${edit}" class=" ${user.status == 'BANNED' ? 'banned' : ''}"><c:out value="${user.getFullName()}"/></a>
                                </td>
                                <td class="users"><c:out value="${user.rating}"/></td>
                                <td class="users"><c:out value="${user.role}"/></td>
                                <td class="email users"><c:out value="${user.email}"/></td>
                                <td class="date users"><c:out value="${user.birthday}"/></td>
                                <td class="users">
                                    <a href="javascript:{}" class="ban-link" id="${user.id}">
                                        <i class="fa fa-ban ban-icon ${user.status == 'BANNED' ? 'banned' : ''}" style="line-height: 3.428571;" aria-hidden="true"></i>
                                    </a>
                                </td>
                                <td class="users">
                                    <a  href="javascript:{}" class="callConfirm" id="${user.id}">
                                        <i class="fa fa-trash delete-icon ${user.status == 'BANNED' ? 'banned' : ''}" style="line-height: 3.428571;" aria-hidden="true"></i>
                                    </a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="footer">
            <div class="footer-content">&copy; 2017.EPAM Systems Taxi Buber</div>
        </div>
        <!-- Modal message danger-->
        <div class="modal fade" id="modal-message2" role="dialog" data-backdrop="static">
            <div class="modal-dialog modal-sm">
                <div class="modal-content modal-danger">
                    <div class="modal-body modal-message-body">
                        <div id="message-delete" style="margin-top: -20px"><fmt:message key="label.deleteuser"/></div>
                        <div class="col-sm-offset-3 col-sm-6" style="margin-top: 20px;" id="delete-div">
                            <input type="button" id="yes" class="yes btn btn-default" style="float: left; color: red" value="<fmt:message key="label.yes"/>"/>
                            <input type="button" id="no" class="no btn btn-default" style="float: right" value="<fmt:message key="label.no"/>"/>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal message-->
        <div class="modal fade" id="modal-message" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content modal-message">
                    <div class="modal-body modal-message-body">
                        <div id="message-deleted" style="margin: 12%"><fmt:message key="label.user"/><fmt:message key="message.deleted"/></div>
                    </div>
                </div>
            </div>
        </div>
    </body>
</html>
