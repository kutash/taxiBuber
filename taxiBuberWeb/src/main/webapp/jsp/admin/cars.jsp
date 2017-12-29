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
            <c:param name="command" value="show_cars"/>
        </c:url>
        <form action="${switchLanguage}" method="post" id="l"></form>
        <div class="container">
            <div class="cars-content" id="googleMap"></div>
        </div>
        <div class="footer">
            <p class="footer-content">&copy; 2017.EPAM Systems Taxi Buber</p>
        </div>
        <script>
            function myMap() {
                var mapProp= {
                    center:new google.maps.LatLng(51.508742,-0.120850),
                    zoom:5
                };
                var map=new google.maps.Map(document.getElementById("googleMap"),mapProp);
            }
        </script>

        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBWwujQHc9yN2BSlbT_L0-L7VLlQAYnUUg&callback=myMap" type="text/javascript"></script>
    </body>
</html>
