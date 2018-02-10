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
        <link rel="stylesheet" href="${pageContext.request.contextPath}css/app.css">
        <link rel="stylesheet" href="${pageContext.request.contextPath}css/fontawesome-free-5.0.4/web-fonts-with-css/css/fontawesome-all.css">
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/css/bootstrap-select.min.css">
        <script src="${pageContext.request.contextPath}js/jquery.js"></script>
        <script type="text/javascript" src="${pageContext.request.contextPath}js/driver.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.12.2/js/bootstrap-select.min.js"></script>
    </head>
    <body>
    <jsp:include page="/jsp/user/header.jsp"/>
        <%--<c:url var="switchLanguage" value="controller" scope="page">
            <c:param name="command" value="main"/>
        </c:url>--%>
        <form action="${switchLanguage}" method="post" id="l"></form>
        <div class="container">
            <div class="row">
                <div class="col-sm-4 text-center">
                    <span id="messageGoesHere"></span>
                    <div class="form-horizontal" id="complete-form">
                        <div class="checkbox">
                            <label id="start-work" style="display: ${car.available == false && trip == null ? 'block' : 'none'}" class="label-span"><fmt:message key="label.start"/></label>
                                <label id="stop-work" style="display: ${car.available == true && trip == null ? 'block' : 'none'}" class="label-span"><fmt:message key="label.stop"/></label>
                            <label class="switch">
                                <input type="checkbox" id="work" ${car.available == true || not empty trip ? 'checked' : ''} ${not empty trip ? 'disabled' : ''}>
                                <span class="slider round"></span>
                            </label>
                        </div>
                        <div class="alert alert-danger" style="display: none" id="no-car">
                            <fmt:message key="label.nullcar"/>
                            <c:url var="edit" value="controller">
                                <c:param name="command" value="edit"/>
                                <c:param name="userId" value="${currentUser.id}"/>
                                <c:param name="isCarParam" value="true"/>
                            </c:url>
                            <a href="${edit}"><fmt:message key="label.addcar"/></a>
                        </div>
                        <div class="alert alert-danger" style="display: none" id="trips">
                            <fmt:message key="message.trips"/>
                        </div>
                        <input type="hidden" id="tripId" name="tripId" value="${trip.id}">
                        <input type="hidden" name="carId" value="${car.id}" id="car-id">
                        <div class="form-group">
                            <label class="control-label col-sm-3 label-driver" for="start"><fmt:message key="label.source"/>:</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="start" value="${trip.departure.address}" readonly/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3 label-driver" for="end"><fmt:message key="label.destination"/>:</label>
                            <div class="col-sm-8">
                                <input type="text" class="form-control" id="end" value="${trip.destination.address}" readonly/>
                            </div>
                        </div>
                        <div id="divDistance-driver" style="display: ${empty trip ? 'none' : 'block'}">
                            <span class="label-span"><fmt:message key="label.distance"/>:</span><span class="info-span" id="distance"></span> <span class="label-span"><fmt:message key="label.kilometers"/></span><br>
                            <span class="label-span" style="margin-left: 30px"><fmt:message key="label.duration"/>:</span><span id="duration" class="info-span"></span><span class="label-span"></span><br>
                            <div class="form-group">
                                <div class="col-sm-offset-3 col-sm-6" id="complete-div">
                                    <button id="complete" class="btn btn-danger" style="font-size: 25px;">
                                        <i class="fa fa-stop-circle" aria-hidden="true"> <fmt:message key="label.complete"/></i>
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8 text-center">
                    <div id="map" style="width:1070px;height:700px;background:gray"></div>
                </div>
            </div>
        </div>
        <div class="footer">
            <div class="footer-content">&copy; 2017.EPAM Systems Taxi Buber</div>
        </div>
        <!-- Modal continue-->
        <div class="modal fade" id="modal-continue" role="dialog" data-backdrop="static">
            <div class="modal-dialog modal-sm">
                <div class="modal-content modal-danger">
                    <div class="modal-body modal-message-body">
                        <div><fmt:message key="message.continue"/></div>
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6" style="margin-top: 20px;" id="delete-div">
                                <input type="button" id="yes" class="yes btn btn-default" style="float: left; color: red" value="<fmt:message key="label.yes"/>"/>
                                <input type="button" id="no" class="no btn btn-default" style="float: right" value="<fmt:message key="label.no"/>"/>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <jsp:include page="/jsp/user/modal_order.jsp"/>
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBWwujQHc9yN2BSlbT_L0-L7VLlQAYnUUg&libraries=places&callback=initMap"></script>
    </body>
</html>
