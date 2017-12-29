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
    <script src="../../js/jquery.js"></script>
    <script type="text/javascript" src="../../js/welcome.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>
<body>
    <jsp:include page="header.jsp"/>
    <form action="controller?command=show_cars" method="post" id="l"></form>

    <div class="order-map" id="map" style="width:800px;height:500px;background:gray"></div>
    <div>
        <input type="text" id="start">
        <input type="text" id="end">
        <input type="submit" id="submit" value="show">
    </div>
    <div id="right-panel"></div>
    <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBWwujQHc9yN2BSlbT_L0-L7VLlQAYnUUg&callback&libraries=places&callback=initMap"></script>
</body>
</html>
