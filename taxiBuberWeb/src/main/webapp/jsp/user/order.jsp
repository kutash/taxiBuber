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
        <script type="text/javascript" src="../../js/order.js"></script>
        <script src="../../js/jquery.js"></script>
        <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
    </head>
    <body>
        <jsp:include page="header.jsp"/>
        <c:url var="switchLanguage" value="controller" scope="page">
            <c:param name="command" value="order"/>
        </c:url>
        <form action="${switchLanguage}" method="post" id="l"></form>

        <div class="container">
            <div class="row">
                <div class="col-sm-4 text-center">
                    <form class="form-horizontal">
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="start"><fmt:message key="label.source"/>:</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="start"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="end"><fmt:message key="label.destination"/>:</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="end"/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="car"><fmt:message key="label.car"/>:</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="car" placeholder="<fmt:message key="label.choosecar"/> "/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="body-type"><fmt:message key="label.capacity"/>:</label>
                            <div class="col-sm-7">
                                <select class="form-control" id="body-type">
                                    <option value="" selected>Not chosen</option>
                                    <option value="CAR"><fmt:message key="label.sedan"/></option>
                                    <option value="MINIVAN"><fmt:message key="label.minivan"/></option>
                                    <option value="MINIBUS"><fmt:message key="label.minibus"/></option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="price"><fmt:message key="label.price"/>:</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="price" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="distance"><fmt:message key="label.distance"/>:</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="distance" disabled/>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="duration"><fmt:message key="label.duration"/>:</label>
                            <div class="col-sm-7">
                                <input type="text" class="form-control" id="duration" disabled/>
                            </div>
                        </div>
                    </form>
                    <div class="form-group" id="submit">
                        <div class="col-sm-offset-3 col-sm-6">
                            <button type="submit" class="btn btn-info"><fmt:message key="label.calculate"/></button>
                        </div>
                    </div>
                    <div class="form-group" id="order" style="margin-top: 20px">
                        <div class="col-sm-offset-3 col-sm-6">
                            <button type="submit" class="btn btn-danger"><fmt:message key="label.order"/></button>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8 text-center">
                    <div id="map" style="width:960px;height:620px;background:gray"></div>
                    <div id="dvDistance" style="display: none">
                        <fmt:message key="label.distance"/>:<span id="distance1"></span>
                        <fmt:message key="label.duration"/>:<span id="duration1"></span>
                        <fmt:message key="label.cost"/>:<span id="cost"></span> <fmt:message key="label.currency"/>
                    </div>
                </div>
            </div>
        </div>

        <div class="footer">
            <div class="footer-content">&copy; 2017.EPAM Systems Taxi Buber</div>
        </div>

        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBWwujQHc9yN2BSlbT_L0-L7VLlQAYnUUg&libraries=places&callback=initMap"></script>
    </body>
</html>
