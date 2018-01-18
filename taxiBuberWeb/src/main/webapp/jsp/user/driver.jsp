<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ctg" uri="/WEB-INF/tld/custom.tld" %>
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
        <script type="text/javascript" src="../../js/order.js"></script>
        <script src="../../js/jquery.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <c:url var="switchLanguage" value="controller" scope="page">
            <c:param name="command" value="order"/>
        </c:url>
        <form action="${switchLanguage}" method="post" id="l"></form>
        <div class="container">
            <div class="row">
                <div style="margin-left: 400px">
                    <form class="form-inline" >
                        <div class="form-group">
                            <label class="control-label col-sm-3 label-driver" for="start"><fmt:message key="label.source"/>:</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control input-driver" id="start" name="start" value="<c:out value="${source}"/>"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 label-driver" for="end"><fmt:message key="label.destination"/>:</label>
                            <div class="col-sm-9">
                                <input type="text" style="margin-left: -33px;" class="form-control input-driver" id="end" name="start" value="<c:out value="${destination}"/>"/>
                            </div>
                        </div>
                    </form>
                </div>
                    <div id="map" style="width:1150px;height:650px;background:gray;margin-left: 284px;"></div>
                <%--</div>--%>
            </div>
        </div>
        <div class="footer">
            <div class="footer-content">&copy; 2017.EPAM Systems Taxi Buber</div>
        </div>
        <!-- Modal message-->
        <div class="modal fade" id="modal-message" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content modal-message">
                    <div class="modal-body modal-message-body">
                        <div id="order-message">${orderMessage}</div>
                    </div>
                </div>
            </div>
        </div>
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBWwujQHc9yN2BSlbT_L0-L7VLlQAYnUUg&libraries=places&callback=initMap"></script>
    </body>
</html>
