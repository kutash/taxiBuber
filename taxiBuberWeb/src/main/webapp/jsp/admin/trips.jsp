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
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/app.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/flag-icon-css-master/css/flag-icon.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}css/fontawesome-free-5.0.4/web-fonts-with-css/css/fontawesome-all.css">
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/trips.js"></script>
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
                <c:if test="${currentUser.role == 'ADMIN'}">
                    <li>
                        <c:url var="showUsers" value="controller">
                            <c:param name="command" value="show_users"/>
                        </c:url>
                        <a href="${showUsers}"><fmt:message key="label.users"/></a>
                    </li>
                    <li>
                        <c:url var="showTrips" value="controller">
                            <c:param name="command" value="trips"/>
                        </c:url>
                        <a href="${showTrips}"><fmt:message key="label.trips"/></a>
                    </li>
                </c:if>
                <c:if test="${currentUser.role == 'DRIVER' || currentUser.role == 'CLIENT'}">
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
                        <c:url var="showTrips" value="controller">
                            <c:param name="command" value="trips"/>
                        </c:url>
                        <a href="${showTrips}"><fmt:message key="label.trips"/></a>
                    </li>
                </c:if>
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
                                    <c:if test="${currentUser.role == 'ADMIN'}">
                                        <li>
                                            <a href="#">
                                                <span class="glyphicon glyphicon-user"></span>
                                                <span class="label-icon"><fmt:message key="label.searchbyname"/></span>
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${currentUser.role == 'DRIVER'}">
                                        <li>
                                            <a href="#">
                                                <span class="glyphicon glyphicon-user"></span>
                                                <span class="label-icon"><fmt:message key="label.searchbyclient"/></span>
                                            </a>
                                        </li>
                                    </c:if>
                                    <c:if test="${currentUser.role == 'CLIENT'}">
                                        <li>
                                            <a href="#">
                                                <span class="glyphicon glyphicon-user"></span>
                                                <span class="label-icon"><fmt:message key="label.searchbydriver"/></span>
                                            </a>
                                        </li>
                                    </c:if>
                                    <li>
                                        <a href="#">
                                            <span class="glyphicon glyphicon-home"></span>
                                            <span class="label-icon"><fmt:message key="label.searchbyaddress"/></span>
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
        <div id="current-user" style="display: none">${currentUser.role}</div>
        <div>
            <table class="table table-hover" id="trips-table">
                <thead>
                <tr>
                    <th><fmt:message key="label.price"/></th>
                    <th><fmt:message key="label.date"/></th>
                    <th><fmt:message key="label.distance"/></th>
                    <th><span><fmt:message key="label.source"/></span></th>
                    <th><span><fmt:message key="label.destination"/></span></th>
                    <th><fmt:message key="label.status"/></th>
                    <c:if test="${currentUser.role == 'ADMIN'}">
                        <th><fmt:message key="label.driver"/></th>
                        <th><fmt:message key="label.client"/></th>
                    </c:if>
                    <c:if test="${currentUser.role == 'DRIVER'}">
                        <th><fmt:message key="label.client"/></th>
                        <th><fmt:message key="label.makecomment"/></th>
                    </c:if>
                    <c:if test="${currentUser.role == 'CLIENT'}">
                        <th><fmt:message key="label.driver"/></th>
                        <th><fmt:message key="label.makecomment"/></th>
                    </c:if>
                </tr>
                </thead>
                <tbody id="content">
                <c:forEach var="trip" items="${trips}">
                    <tr>
                        <td><c:out value="${trip.price}"/></td>
                        <td class="date"><c:out value="${trip.date}"/></td>
                        <td><c:out value="${trip.distance}"/></td>
                        <td class="address">
                            <c:out value="${trip.departure.address}"/>
                        </td>
                        <td class="address2"><c:out value="${trip.destination.address}"/></td>
                        <td><c:out value="${trip.status}"/></td>
                        <c:if test="${currentUser.role == 'ADMIN'}">
                            <td class="name">
                                <c:url var="edit" value="controller">
                                    <c:param name="command" value="edit"/>
                                    <c:param name="userId" value="${trip.driverId}"/>
                                </c:url>
                                <a href="${edit}"><c:out value="${trip.driverName}"/></a>
                            </td>
                            <td class="name2">
                                <c:url var="edit" value="controller">
                                    <c:param name="command" value="edit"/>
                                    <c:param name="userId" value="${trip.clientId}"/>
                                </c:url>
                                <a href="${edit}"><c:out value="${trip.clientName}"/></a>
                            </td>
                        </c:if>
                        <c:if test="${currentUser.role == 'DRIVER'}">
                            <td class="client"><c:out value="${trip.clientName}"/></td>
                            <td>
                                <a class="comment-link" id="${trip.clientId}" href="javascript:{}">
                                    <i class="far fa-comment-alt"></i>
                                </a>
                            </td>
                        </c:if>
                        <c:if test="${currentUser.role == 'CLIENT'}">
                            <td class="driver"><c:out value="${trip.driverName}"/></td>
                            <td>
                                <a class="comment-link" id="${trip.driverId}" href="javascript:{}">
                                    <i class="far fa-comment-alt"></i>
                                </a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close cancel" data-dismiss="modal">&times;</button>
                    <h4 style="font-size: 30px" class="modal-title"><i class="far fa-comment-alt"></i> <fmt:message key="label.comment"/></h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal" action="controller" method="post" id="comment-form">
                        <input type="hidden" name="command" value="comment">
                        <input type="hidden" name="userId" id="user-id">
                        <input type="hidden" name="valuation" id="valuation">
                        <div class="error-comment" id="error-comment">${wrongComment}</div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="comment"><fmt:message key="label.comment"/>:</label>
                            <div class="col-sm-8">
                                <textarea class="form-control" id="comment" rows="5" name="comment" form="comment-form"></textarea>
                                <div style="display: none" id="max-comment" class="err"><fmt:message key="label.maxcomment"/></div>
                                <div style="display: none" id="blank-comment" class="err"><fmt:message key="label.blank"/></div>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" style="padding-top: 0"><fmt:message key="label.valuation"/>:</label>
                            <div class="col-sm-8">
                                <div class='rating-stars'>
                                    <ul id='stars'>
                                        <li class='star' title='Poor' data-value='1'>
                                            <i class='fa fa-star fa-fw'></i>
                                        </li>
                                        <li class='star' title='Fair' data-value='2'>
                                            <i class='fa fa-star fa-fw'></i>
                                        </li>
                                        <li class='star' title='Good' data-value='3'>
                                            <i class='fa fa-star fa-fw'></i>
                                        </li>
                                        <li class='star' title='Excellent' data-value='4'>
                                            <i class='fa fa-star fa-fw'></i>
                                        </li>
                                        <li class='star' title='WOW!!!' data-value='5'>
                                            <i class='fa fa-star fa-fw'></i>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <button type="submit" class="btn btn-success" id="submit-button" form="comment-form" disabled>Submit</button>
                            </div>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default cancel" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal message-->
    <div class="modal fade" id="modal-message" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content modal-message">
                <div class="modal-body modal-message-body">
                    <input type="hidden" value="${isCreated}" id="is-created">
                    <div id="message-deleted" style="margin: 12%"><fmt:message key="label.comment"/> <fmt:message key="message.created"/></div>
                </div>
            </div>
        </div>
    </div>
    <jsp:include page="/jsp/user/modal_order.jsp"/>
</body>
</html>
