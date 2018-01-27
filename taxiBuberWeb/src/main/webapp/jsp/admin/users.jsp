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
        <script type="text/javascript" src="${pageContext.request.contextPath}js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}js/paginate.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
    <jsp:include page="header.jsp"/>
    <c:url var="switchLanguage" value="controller" scope="page">
        <c:param name="command" value="show_users"/>
    </c:url>
    <form action="${switchLanguage}" method="post" id="l"></form>
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
                                <td class="users">
                                    <c:url var="edit" value="controller">
                                        <c:param name="command" value="edit"/>
                                        <c:param name="userId" value="${user.id}"/>
                                    </c:url>
                                    <a href="${edit}" class="${user.status == 'BANNED' ? 'banned' : ''}"><c:out value="${user.getFullName()}"/></a>
                                </td>
                                <td class="users"><c:out value="${user.rating}"/></td>
                                <td class="users"><c:out value="${user.role}"/></td>
                                <td class="users"><c:out value="${user.email}"/></td>
                                <td class="users"><c:out value="${user.birthday}"/></td>
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
                <div id="pager"><fmt:message key="label.page"/>: </div>
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
                        <div id="message-delete"><fmt:message key="label.deleteuser"/></div>
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
    <%--<script>
        paginate('content', 'pager', 10);
    </script>--%>
</html>
