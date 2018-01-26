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
        <link rel="stylesheet" href="../../css/app.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}css/fontawesome-free-5.0.4/web-fonts-with-css/css/fontawesome-all.css">
        <script src="../../js/jquery.js"></script>
        <script type="text/javascript" src="../../js/paginate.js"></script>
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
                            <th><fmt:message key="label.status"/></th>
                            <th></th>
                            <th></th>
                        </tr>
                    </thead>
                    <tbody id="content">
                        <c:forEach var="user" items="${users}">
                            <tr class="${user.status == 'BANNED' ? 'banned' : ''}">
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
                                <td class="users"><c:out value="${user.status}"/></td>
                                <td class="users">
                                    <c:url var="ban" value="controller">
                                        <c:param name="command" value="ban"/>
                                        <c:param name="userId" value="${user.id}"/>
                                    </c:url>
                                    <a href="${ban}" class="${user.status == 'BANNED' ? 'banned' : ''}">
                                        <i class="fa fa-ban" style="line-height: 3.428571;" aria-hidden="true"></i>
                                    </a>
                                </td>
                                <td class="users">
                                    <c:url var="delete" value="controller">
                                        <c:param name="command" value="delete"/>
                                        <c:param name="userId" value="${user.id}"/>
                                    </c:url>
                                    <%--<form id="delete" method="post">
                                        <input type="hidden" name="command" value="delete">
                                        <input type="hidden" name="userId" value="${user.id}">
                                    </form>--%>
                                    <a href="${delete}" id="delete-link" class="${user.status == 'BANNED' ? 'banned' : ''}">
                                        <i class="fa fa-trash" style="line-height: 3.428571;" aria-hidden="true"></i>
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
        <!-- Modal message-->
        <div class="modal fade" id="modal-message2" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content modal-message">
                    <div class="modal-body modal-message-body">
                        <div id="message-delete" style="display: none"><fmt:message key="label.deleteuser"/></div>
                        <div class="col-sm-offset-3 col-sm-6" style="margin-top: 20px; display: none" id="delete-div">
                            <input type="button" id="yes" class="btn btn-danger" value="<fmt:message key="label.yes"/>"/>
                            <input type="button" id="no" class="btn btn-danger" value="<fmt:message key="label.no"/>"/>
                        </div>
                        <div id="message-deleted">${deletedMessage}</div>
                    </div>
                </div>
            </div>
        </div>
    </body>
    <%--<script>
        paginate('content', 'pager', 10);
    </script>--%>
</html>
