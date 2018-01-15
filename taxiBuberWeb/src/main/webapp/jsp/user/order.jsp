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
                <div class="col-sm-4 text-center">
                    <form class="form-horizontal" id="order-form" action="controller" method="post" onsubmit="return false">
                        <input type="hidden" name="command" value="make_order">
                        <div class="form-group required">
                            <label class="control-label col-sm-3" for="start"><fmt:message key="label.source"/>:</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="start" name="start" required="required"/>
                            </div>
                            <div style="display: none" id="source-error" class="err"><fmt:message key="label.sourceerror"/></div>
                        </div>
                        <div class="form-group required">
                            <label class="control-label col-sm-3" for="end"><fmt:message key="label.destination"/>:</label>
                            <div class="col-sm-9">
                                <div class="input-group">
                                    <input type="text" id="end" name="end" class="form-control" aria-label="..." required="required">
                                    <div class="input-group-btn">
                                        <button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">Address <span class="caret"></span></button>
                                        <ul class="dropdown-menu dropdown-menu-right">
                                            <c:forEach var="address" items="${addresses}">
                                                <li><a href="#" class="address-link"><c:out value="${address.getFullAddress()}"/></a></li>
                                            </c:forEach>
                                        </ul>
                                    </div><!-- /btn-group -->
                                </div>
                            </div>
                            <div style="display: none" id="dest-error" class="err"><fmt:message key="label.desterror"/></div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="car"><fmt:message key="label.car"/>:</label>
                            <div class="col-sm-9">
                                <input type="text" class="form-control" id="car" placeholder="<fmt:message key="label.choosecar"/> " readonly/>
                                <input type="hidden" id="carId" name="carId">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3" for="body-type"><fmt:message key="label.capacity"/>:</label>
                            <div class="col-sm-9">
                                <select class="form-control selectpicker show-tick" id="body-type" name="capacity">
                                    <option value="" selected>Not chosen</option>
                                    <option value="CAR"><fmt:message key="label.sedan"/></option>
                                    <option value="MINIVAN"><fmt:message key="label.minivan"/></option>
                                    <option value="MINIBUS"><fmt:message key="label.minibus"/></option>
                                </select>
                            </div>
                        </div>
                    </form>
                    <br/>
                    <div id="dvDistance" style="display: none; margin-top: 45px">
                        <span class="label-span" style="margin-left: -45px"><fmt:message key="label.distance"/>:</span><span class="info-span" id="distance"></span>
                        <span class="label-span"><fmt:message key="label.duration"/>:</span><span id="duration" class="info-span"></span>
                        <span class="label-span" style="margin-left: -75px"><fmt:message key="label.cost"/>:</span><span id="cost" class="info-span"></span><span class="label-span"><fmt:message key="label.currency"/></span>
                    </div>
                    <div class="form-group" id="order">
                        <div class="col-sm-offset-3 col-sm-6" style="margin-top: 20px">
                            <input type="button" id="order-button" class="btn btn-danger" value="<fmt:message key="label.order"/>"/>
                        </div>
                    </div>
                </div>
                <div class="col-sm-8 text-center">
                    <div id="map" style="width:960px;height:620px;background:gray"></div>
                </div>
            </div>
        </div>
        <div class="footer">
            <div class="footer-content">&copy; 2017.EPAM Systems Taxi Buber</div>
        </div>
        <!-- Modal -->
        <div id="myModal" class="modal fade" role="dialog">
            <div class="modal-dialog modal-lg">
                <!-- Modal content-->
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 style="font-size: 35px;"><span class="glyphicon glyphicon-user" style="font-size: 35px;"></span><fmt:message key="label.driver"/></h4>
                        <button type="button" class="close" data-dismiss="modal">X</button>
                    </div>
                    <div class="modal-body">
                        <span id="driver"></span>
                        <div class="row">
                            <div class="col-sm-4 text-center">
                                <div>
                                    <img id="blah" src="" alt="your image" width="220" height="220"  />
                                    <div class="rating" style="width: 220px">
                                        <span id="rating-order"></span>
                                        <div class="productRate-order">
                                            <div class="productRate-div" id="str"></div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <div class="col-sm-8 text-center">
                                <div>
                                    <table class="table">
                                        <tbody id="tbody">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBWwujQHc9yN2BSlbT_L0-L7VLlQAYnUUg&libraries=places&callback=initMap"></script>
    </body>
</html>
