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
    <!-- Modal car-->
    <div class="modal fade" id="modal-car" role="dialog">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close cancel-car" data-dismiss="modal">&times;</button>
                    <h4 class="modal-title" style="font-size: 35px;"><i class="fa fa-car" aria-hidden="true"></i> <fmt:message key="label.car"/></h4>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <input id="is-car" type="hidden" value="${isCar}">
                        <div class="col-sm-3 text-center">
                            <div>
                                <input type="hidden" id="car-photo" value="${car.photoPath}">
                                <input type='file' id="file-car" style="display: none" name="photo" form="saveCarForm" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}/>
                                <a href="javascript:{}" id="img">
                                    <img id="car-img" src="${pageContext.request.contextPath}/ajaxController?command=photo&amp;photo=${car.photoPath}&amp;userId=${car.userId}" alt="your image" width="270" height="270"  />
                                </a>
                            </div>
                        </div>
                        <div class="col-sm-9 text-center">
                            <form class="form-horizontal" action="controller" method="post" enctype="multipart/form-data" id="saveCarForm">
                                <input type="hidden" name="command" value="save_car">
                                <input type="hidden" value="${car.id}" name="carId">
                                <input type="hidden" value="${not empty car ? car.userId : user.id}" name="userId" id="user-id">
                                <div class="form-group required">
                                    <label class="control-label col-sm-5" for="number"><fmt:message key="label.number"/>:</label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control" id="number" name="number" value="${car.registrationNumber}" placeholder="0000XX-0" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}/>
                                        <div style="display: none" id="error-number" class="err"><fmt:message key="label.errornumber"/></div>
                                        <div style="display: none" id="blank-number" class="err"><fmt:message key="label.blank"/></div>
                                        <div class="err2">${errors.number}</div>
                                        <div class="err2">${errors.numberBlank}</div>
                                        <div class="err2">${errors.notuniquenumber}</div>
                                    </div>
                                </div>
                                <div class="form-group required">
                                    <label class="control-label col-sm-5" for="model"><fmt:message key="label.model"/>:</label>
                                    <div class="col-sm-7">
                                        <input type="text" class="form-control" id="model" name="model" value="${car.model}" placeholder="<fmt:message key="label.entermodel"/>" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}/>
                                        <div style="display: none" id="error-model" class="err"><fmt:message key="label.errormodel"/></div>
                                        <div style="display: none" id="blank-model" class="err"><fmt:message key="label.blank"/></div>
                                        <div class="err2">${errors.model}</div>
                                        <div class="err2">${errors.modelBlank}</div>
                                    </div>
                                </div>
                                <div class="form-group required">
                                    <label class="control-label col-sm-5" for="brand"><fmt:message key="label.brand"/>:</label>
                                    <div class="col-sm-7">
                                        <select class="form-control" id="brand" name="brand" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}>
                                            <option value="" ${car.brand == null ? 'selected="selected"' : ''}>not chosen</option>
                                            <c:forEach var="brand" items="${brands}">
                                                <option value="${brand.id} ${brand.name}" ${brand.id == car.brand.id ? 'selected="selected"' : ''}>${brand.name}</option>
                                            </c:forEach>
                                        </select>
                                        <div class="err2">${errors.brand}</div>
                                    </div>
                                </div>
                                <div class="form-group required">
                                    <label class="control-label col-sm-5" for="capacity"><fmt:message key="label.capacity"/>:</label>
                                    <div class="col-sm-7">
                                        <select class="form-control" id="capacity" name="capacity" ${currentUser.role == 'ADMIN' ? 'disabled' : ''}>
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
                                        <div class="err2">${errors.capacity}</div>
                                    </div>
                                </div>
                                <c:if test="${currentUser.role == 'DRIVER'}">
                                    <div class="form-group" id="save">
                                        <div class="col-sm-offset-2 col-sm-6">
                                            <button type="submit" id="save-car" class="btn btn-success" form="saveCarForm"><i class="fa fa-floppy-o" aria-hidden="true"></i> <fmt:message key="button.save"/></button>
                                        </div>
                                    </div>
                                </c:if>
                            </form>
                            <form id="deleteCarForm" action="controller" method="post">
                                <input type="hidden" name="command" value="delete_car">
                                <input type="hidden" value="${car.id}" name="carId">
                                <input type="hidden" value="${car.userId}" name="userId">
                            </form>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default cancel-car" data-dismiss="modal"><fmt:message key="button.cancel"/></button>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal message-->
    <div class="modal fade" id="modal-message2" role="dialog">
        <div class="modal-dialog modal-sm">
            <div class="modal-content modal-message">
                <div class="modal-body modal-message-body">
                    <div id="message-update" style="display: none">${updateMessage}</div>
                    <div id="message-create" style="display: none">${createMessage}</div>
                    <div id="message-deleted" style="display: none">${deletedMessage}</div>
                </div>
            </div>
        </div>
    </div>
    <!-- Modal message danger-->
    <div class="modal fade" id="modal-confirm" role="dialog" data-backdrop="static">
        <div class="modal-dialog modal-sm">
            <div class="modal-content modal-danger">
                <div class="modal-body modal-message-body">
                    <div id="message-delete"><fmt:message key="label.deletecar"/></div>
                    <div class="col-sm-offset-3 col-sm-6" style="margin-top: 20px;" id="delete-div">
                        <input type="button" id="yes" class="yes btn btn-default" style="float: left; color: red" value="<fmt:message key="label.yes"/>"/>
                        <input type="button" id="no" class="no btn btn-default" style="float: right" value="<fmt:message key="label.no"/>"/>
                    </div>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
