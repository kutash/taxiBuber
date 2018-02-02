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
    <script src="${pageContext.request.contextPath}/js/jquery.js"></script>
    <script src="${pageContext.request.contextPath}/js/trips.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <jsp:include page="/jsp/user/header.jsp"/>
    <form action="${switchLanguage}" method="post" id="l"></form>
    <div class="container">
        <div>
            <table class="table table-hover" id="users-table">
                <thead>
                <tr>
                    <th><fmt:message key="label.price"/></th>
                    <th><fmt:message key="label.date"/></th>
                    <th><fmt:message key="label.distance"/></th>
                    <th><fmt:message key="label.source"/></th>
                    <th><fmt:message key="label.destination"/></th>
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
                        <td><c:out value="${trip.date}"/></td>
                        <td><c:out value="${trip.distance}"/></td>
                        <td><c:out value="${trip.departure.address}"/></td>
                        <td><c:out value="${trip.destination.address}"/></td>
                        <td><c:out value="${trip.status}"/></td>
                        <c:if test="${currentUser.role == 'ADMIN'}">
                            <td>
                                <c:url var="edit" value="controller">
                                    <c:param name="command" value="edit"/>
                                    <c:param name="userId" value="${trip.driverId}"/>
                                </c:url>
                                <a href="${edit}"><c:out value="${trip.driverName}"/></a>
                            </td>
                            <td>
                                <c:url var="edit" value="controller">
                                    <c:param name="command" value="edit"/>
                                    <c:param name="userId" value="${trip.clientId}"/>
                                </c:url>
                                <a href="${edit}"><c:out value="${trip.clientName}"/></a>
                            </td>
                        </c:if>
                        <c:if test="${currentUser.role == 'DRIVER'}">
                            <td><c:out value="${trip.clientName}"/></td>
                            <td>
                                <a class="comment-link" id="${trip.clientId}" href="javascript:{}">
                                    <i class="fa fa-commenting-o" aria-hidden="true"></i>
                                </a>
                            </td>
                        </c:if>
                        <c:if test="${currentUser.role == 'CLIENT'}">
                            <td><c:out value="${trip.driverName}"/></td>
                            <td>
                                <a class="comment-link" id="${trip.driverId}" href="javascript:{}">
                                    <i class="fa fa-commenting-o" aria-hidden="true"></i>
                                </a>
                            </td>
                        </c:if>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <div id="pager"><fmt:message key="label.page"/>: </div>
        </div>
    </div>
    <!-- Modal -->
    <div class="modal fade" id="myModal" role="dialog">
        <div class="modal-dialog">

            <!-- Modal content-->
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close cancel" data-dismiss="modal">&times;</button>
                    <h4 style="font-size: 30px" class="modal-title"><i class="fa fa-comments-o" aria-hidden="true"></i> <fmt:message key="label.comment"/></h4>
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
</body>
</html>
