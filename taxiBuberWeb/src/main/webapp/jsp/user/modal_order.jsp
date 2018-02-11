<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="language" value="${not empty param.language ? param.language : not empty language ? language : pageContext.request.locale.toString()}" scope="session" />
<fmt:setLocale value="${language}" />
<fmt:setBundle basename="${pageContext.request.contextPath}/messages"/>
<html>
<head>
    <title>Title</title>
</head>
<body>
<!-- Modal waiting-->
    <div class="modal fade" id="modal-order" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content modal-message">
                <div class="modal-body modal-message-body">
                    <div class="message" id="message-success" style="display: none"><fmt:message key="message.ordersuccess"/></div>
                    <div id="message-driver" style="display: none">
                        <div id="order-message"><fmt:message key="message.neworder"/></div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6" style="margin-top: 20px">
                                <button id="begin" class="btn btn-danger">
                                    <fmt:message key="label.begin"/> <i class="fa fa-play-circle" aria-hidden="true"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                    <div id="message-wrong" style="display: none">${orderMessage}</div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
