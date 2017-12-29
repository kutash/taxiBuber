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
        <script type="text/javascript" src="../../js/paginate.js"></script>
        <script src="../../js/jquery.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
    <jsp:include page="header.jsp"/>
    <c:url var="switchLanguage" value="controller" scope="page">
        <c:param name="command" value="show_users"/>
    </c:url>
    <form action=${switchLanguage} method="post" id="l"></form>
        <div class="container">
            <div class="table-hover">
                <table class="table" id="users-table">
                    <thead>
                        <tr>
                            <th>#</th>
                            <th><fmt:message key="label.fullname"/></th>
                            <th><fmt:message key="label.rating"/></th>
                            <th><fmt:message key="label.role"/></th>
                            <th><fmt:message key="label.email"/></th>
                            <th><fmt:message key="label.birthday"/></th>
                        </tr>
                    </thead>
                    <tbody id="content">
                        <c:forEach var="user" items="${users}">
                            <tr>
                                <td><input type="checkbox" name="idUser" value="${user.id}"/></td>
                                <td>
                                    <c:url var="edit" value="controller">
                                        <c:param name="command" value="edit"/>
                                        <c:param name="userId" value="${user.id}"/>
                                    </c:url>
                                    <a href="${edit}" class="edit-href" ><c:out value="${user.getFullName()}"/></a>
                                </td>
                                <td><c:out value="${user.rating}"/></td>
                                <td><c:out value="${user.role}"/></td>
                                <td><c:out value="${user.email}"/></td>
                                <td><c:out value="${user.birthday}"/></td>
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
    </body>
    <script>
        paginate('content', 'pager', 10);
    </script>
</html>
