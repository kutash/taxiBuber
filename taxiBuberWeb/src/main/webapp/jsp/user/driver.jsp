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
        <link rel="stylesheet" href="${pageContext.request.contextPath}css/fontawesome-free-5.0.4/web-fonts-with-css/css/fontawesome-all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
        <script src="../../js/jquery.js"></script>
        <script type="text/javascript" src="../../js/driver.js"></script>
        <script type="text/javascript" src="../../js/car.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    </head>
    <body>
        <jsp:include page="/jsp/user/driver_header.jsp"/>
        <c:url var="switchLanguage" value="controller" scope="page">
            <c:param name="command" value="main"/>
        </c:url>
        <form action="${switchLanguage}" method="post" id="l"></form>
        <div class="container">
            <div class="row">
                <div style="margin-left: 400px">
                    <form class="form-inline" id="complete-form" action="controller" method="post">
                        <input type="hidden" name="command" value="complete_trip">
                        <input type="hidden" id="tripId" name="tripId">
                        <input type="hidden" name="latitude" id="latitude">
                        <input type="hidden" name="longitude" id="longitude">
                        <div class="form-group">
                            <label class="control-label col-sm-3 label-driver" for="start"><fmt:message key="label.source"/>:</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control input-driver" id="start" readonly/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 label-driver" for="end"><fmt:message key="label.destination"/>:</label>
                            <div class="col-sm-9">
                                <input type="text" style="margin-left: -33px;" class="form-control input-driver" id="end" readonly/>
                            </div>
                        </div>
                    </form>
                </div>
                    <div id="map" style="width:1100px;height:600px;background:gray;margin-left: 284px;"></div>
                <%--</div>--%>
            </div>
            <div id="divDistance-driver" style="display: none">
                <span class="label-span"><fmt:message key="label.distance"/>:</span><span class="info-span" id="distance"></span> <span class="label-span"><fmt:message key="label.kilometers"/></span>
                <span class="label-span" style="margin-left: 30px"><fmt:message key="label.duration"/>:</span><span id="duration" class="info-span"></span><span class="label-span"></span><br>
                <div class="form-group">
                    <div class="col-sm-offset-3 col-sm-6" id="complete-div">
                        <input type="submit" form="complete-form" id="complete" class="btn btn-danger" value="<fmt:message key="label.complete"/>"/>
                    </div>
                </div>
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
                        <div id="order-message"><fmt:message key="message.neworder"/></div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6" style="margin-top: 20px">
                                <input type="button" id="begin" class="btn btn-danger" value="<fmt:message key="label.begin"/>"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="/jsp/user/modal_car.jsp"/>
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBWwujQHc9yN2BSlbT_L0-L7VLlQAYnUUg&libraries=places&callback=initMap"></script>
    </body>
</html>
