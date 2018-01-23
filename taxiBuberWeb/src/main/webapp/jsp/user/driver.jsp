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
        <nav class="navbar navbar-inverse navbar-fixed-top">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="${home}"><span class="glyphicon glyphicon-home"></span> <fmt:message key="label.title"/></a>
                </div>
                <ul class="nav navbar-nav">
                    <li class="active"><a href="controller?command=main"><fmt:message key="label.order"/></a></li>
                    <li>
                        <c:url var="edit" value="controller">
                            <c:param name="command" value="edit"/>
                            <c:param name="userId" value="${currentUser.id}"/>
                        </c:url>
                        <a href="${edit}"><fmt:message key="label.profile"/></a>
                    </li>
                    <li><a href="#"><fmt:message key="label.trips"/></a></li>
                    <li><a href="#" data-toggle="modal" data-target="#modal-car"><fmt:message key="label.car"/></a></li>
                </ul>
                <ul class="nav navbar-nav navbar-right">
                    <li>
                        <span class="user-name">${currentUser.name}</span>
                        <img src="${pageContext.request.contextPath}/ajaxController?command=photo&amp;photo=${currentUser.photoPath}&amp;userId=${currentUser.id}"  id="header-photo" width="30px" height="30px"/>
                    </li>
                    <li>
                        <c:url var="logout" value="controller">
                            <c:param name="command" value="logout"/>
                        </c:url>
                        <a href="${logout}"><span class="glyphicon glyphicon-log-out"></span> <fmt:message key="label.logout"/></a>
                    </li>
                    <li class="lang">
                        <select id="language" name="language" onchange="submit()" class="selectpicker show-tick" data-width="100px" form="l">
                            <option value="en_US" ${language == 'en_US' ? 'selected' : ''}>English</option>
                            <option value="ru_RU" ${language == 'ru_RU' ? 'selected' : ''}>Русский</option>
                        </select>
                    </li>
                </ul>
            </div>
        </nav>
        <c:url var="switchLanguage" value="controller" scope="page">
            <c:param name="command" value="order"/>
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
        <!-- Modal message-->
        <div class="modal fade" id="modal-message2" role="dialog">
            <div class="modal-dialog modal-sm">
                <div class="modal-content modal-message">
                    <div class="modal-body modal-message-body">
                        <div class="form-group">
                            <div class="col-sm-offset-3 col-sm-6" style="margin-top: 20px">
                                <div id="message-update" style="display: none">${updateMessage}</div>
                                <div id="message-create" style="display: none">${createMessage}</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- Modal car-->
        <div class="modal fade" id="modal-car" role="dialog">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close cancel-car" data-dismiss="modal">&times;</button>
                        <h4 class="modal-title" style="font-size: 35px;"><i class="fa fa-car" aria-hidden="true"></i><fmt:message key="label.car"/></h4>
                    </div>
                    <div class="modal-body">
                            <div class="row">
                                <input id="is-car" type="hidden" value="${isCar}">
                                <div class="col-sm-3 text-center">
                                    <div>
                                        <input type="hidden" id="car-photo" value="${car.photoPath}">
                                        <input type="hidden" id="user-id" value="${currentUser.id}">
                                        <input type='file' id="imgInp" style="display: none" name="photo" form="saveCarForm"/>
                                        <a href="javascript:{}" id="img">
                                            <img id="blah" src="${pageContext.request.contextPath}/ajaxController?command=photo&amp;photo=${car.photoPath}&amp;userId=${currentUser.id}" alt="your image" width="270" height="270"  />
                                        </a>
                                    </div>
                                </div>
                                <div class="col-sm-9 text-center">
                                    <form class="form-horizontal" action="controller" method="post" enctype="multipart/form-data" id="saveCarForm">
                                        <input type="hidden" name="command" value="save_car">
                                        <input type="hidden" value="${car.id}" name="carId">
                                        <div class="form-group required">
                                            <label class="control-label col-sm-5" for="number"><fmt:message key="label.number"/>:</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" id="number" name="number" value="${car.registrationNumber}" placeholder="0000XX-0"/>
                                                <div style="display: none" id="error-number" class="err"><fmt:message key="label.errornumber"/></div>
                                                <div style="display: none" id="blank-number" class="err"><fmt:message key="label.blank"/></div>
                                                <div class="err">${errors.number}</div>
                                                <div class="err">${errors.numberBlank}</div>
                                                <div class="err">${errors.notuniquenumber}</div>
                                            </div>
                                        </div>
                                        <div class="form-group required">
                                            <label class="control-label col-sm-5" for="model"><fmt:message key="label.model"/>:</label>
                                            <div class="col-sm-7">
                                                <input type="text" class="form-control" id="model" name="model" value="${car.model}" placeholder="<fmt:message key="label.entermodel"/>"/>
                                                <div style="display: none" id="error-model" class="err"><fmt:message key="label.errormodel"/></div>
                                                <div style="display: none" id="blank-model" class="err"><fmt:message key="label.blank"/></div>
                                                <div class="err">${errors.model}</div>
                                                <div class="err">${errors.modelBlank}</div>
                                            </div>
                                        </div>
                                        <div class="form-group required">
                                            <label class="control-label col-sm-5" for="brand"><fmt:message key="label.brand"/>:</label>
                                            <div class="col-sm-7">
                                                <select class="form-control" id="brand" name="brand">
                                                    <option value="" ${car.brand == null ? 'selected="selected"' : ''}>not chosen</option>
                                                    <c:forEach var="brand" items="${brands}">
                                                        <option value="${brand.id} ${brand.name}" ${brand.id == car.brand.id ? 'selected="selected"' : ''}>${brand.name}</option>
                                                    </c:forEach>
                                                </select>
                                                <div class="err">${errors.brand}</div>
                                            </div>
                                        </div>
                                        <div class="form-group required">
                                            <label class="control-label col-sm-5" for="capacity"><fmt:message key="label.capacity"/>:</label>
                                            <div class="col-sm-7">
                                                <select class="form-control selectpicker show-tick" id="capacity" name="capacity">
                                                    <c:if test="${car.capacity == 'CAR'}">
                                                        <option value="">not chosen</option>
                                                        <option selected="selected" value="CAR"><fmt:message key="label.sedan"/></option>
                                                        <option value="MINIVAN"><fmt:message key="label.minivan"/></option>
                                                        <option value="MINIBUS"><fmt:message key="label.minibus"/></option>
                                                    </c:if>
                                                    <c:if test="${car.capacity == 'MINIVAN'}">
                                                        <option value="">not chosen</option>
                                                        <option value="CAR"><fmt:message key="label.sedan"/></option>
                                                        <option selected="selected" value="MINIVAN"><fmt:message key="label.minivan"/></option>
                                                        <option value="MINIBUS"><fmt:message key="label.minibus"/></option>
                                                    </c:if>
                                                    <c:if test="${car.capacity == 'MINIBUS'}">
                                                        <option value="">not chosen</option>
                                                        <option value="CAR"><fmt:message key="label.sedan"/></option>
                                                        <option value="MINIVAN"><fmt:message key="label.minivan"/></option>
                                                        <option selected="selected" value="MINIBUS"><fmt:message key="label.minibus"/></option>
                                                    </c:if>
                                                    <c:if test="${car.capacity == null}">
                                                        <option selected="selected" value="">not chosen</option>
                                                        <option value="CAR"><fmt:message key="label.sedan"/></option>
                                                        <option value="MINIVAN"><fmt:message key="label.minivan"/></option>
                                                        <option value="MINIBUS"><fmt:message key="label.minibus"/></option>
                                                    </c:if>
                                                </select>
                                                <div class="err">${errors.capacity}</div>
                                            </div>
                                        </div>
                                        <div class="form-group" id="save">
                                            <div class="col-sm-offset-2 col-sm-6">
                                                <button type="submit" id="save-button" class="btn btn-primary" form="saveCarForm"><i class="fa fa-floppy-o" aria-hidden="true"></i>Save</button>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-default cancel-car" data-dismiss="modal">Cancel</button>
                    </div>
                </div>
            </div>
        <script async defer src="https://maps.googleapis.com/maps/api/js?key=AIzaSyBWwujQHc9yN2BSlbT_L0-L7VLlQAYnUUg&libraries=places&callback=initMap"></script>
    </body>
</html>
