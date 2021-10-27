<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Contacts App</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="static_resources/bootstrap_3.3.7/dist/js/bootstrap.min.js" ></script>
<script type="text/javascript" src="static_resources/js/Details.js"></script>
<link href="static_resources/css/Details.css" rel="stylesheet" />
<script src="static_resources/choosen/chosen.jquery.js" type="text/javascript"></script>
<link rel="stylesheet" href="static_resources/choosen/chosen.css">
<link href="static_resources/bootstrap_3.3.7/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="themeContent">
<%@ include file="Header.txt" %>
<%
	if(!request.getHeader("referer").contains("AddUpdate")){
		request.getSession().setAttribute("goBackURL", request.getHeader("referer"));
	}
%>
<div class="container" style="">
	<c:choose>
		<c:when test="${empty nothing}">
			<div class="alert alert-info col-sm-9" >
				<h4 id="uHeader"><i><b>
					<c:choose>
						<c:when test="${param['action'] eq 'update'}">
							Update Contact
						</c:when>
						<c:otherwise>
							Add New Contact
						</c:otherwise>
					</c:choose>
				</b></i></h4>
				<label style="color: green;" id="status"></label>
			</div><div class="col-sm-2"></div>
			<div class="alert alert-warning col-sm-9" >
                <form action="addedDetails.jsp" method="get" name="details" id="details"  class="form-horizontal">
                	<div class="form-group">
                    	<label class="control-label col-sm-3">Name</label>
                    	<div class="col-sm-6">
                        	<input type="text" id="name" name="name" value="${contact.getName()}" class="form-control" required="required" maxlength="30" />
                    	</div>
                	</div>
                	<div class="form-group">
                    	<label class="control-label col-sm-3">Phone</label>
                    	<div class="col-sm-6">
                        	<input type="text" class="form-control" id="phone" name="phone" value="${contact.getPhone()}" required="required" />
                    	</div>
                	</div>
                	<div class="form-group">
                    	<label class="control-label col-sm-3">Email</label>
                    	<div class="col-sm-6">
                        	<input type="text" class="form-control" id="email" name="email" value="${contact.getEmail()}" />
                    	</div>
                	</div>
                	<div class="form-group">
                    	<label class="control-label col-sm-3">Address</label>
                    	<div class="col-sm-6">
                        	<input type="text" class="form-control" id="address" name="address" value="${contact.getAddress()}" />
                    	</div>
                	</div>
                	<div class="form-group">
                    	<label class="control-label col-sm-3">State</label>
                    	<div class="col-sm-6">
                        	<select name="state" class="form-control state chosen-select" id="state">
                        		<c:choose>
                        			<c:when test="${not empty contact.getState()}">
                        				<option value="${contact.getState()}">${contact.getState()}</option>
                        			</c:when>
                        			<c:otherwise>
                        				<option value="">Select your state</option>
                        			</c:otherwise>
                        		</c:choose>
                        		<c:forEach items="${staeList}" var="state">
                        			<option value="${state}">${state}</option>
                        		</c:forEach>
                        	</select>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-3">City</label>
                    <div class="col-sm-6">
                    <div id="response">
                        <select name="city" class="form-control " id="city" ${param['action'] == 'true' ? 'disabled' : ""}>
                        <c:choose>
                        		<c:when test="${not empty contact.getCity()}" >
                        			<option value="${contact.getCity()}">${contact.getCity()}</option>
                        		</c:when>
                        		<c:otherwise>
                        			<option value="">Select your city</option>
                        		</c:otherwise>
                        	</c:choose>
                        </select>
                    </div>
                    </div>
                </div>
                <div class="form-group">
                    	<label class="control-label col-sm-3">Gender</label>
                    	<div class="col-sm-6">
                        	<div class="radio ">
                            	<label><input type="radio" id="Male" name="radio" value="Male" ${contact.getGender()=="Male" ? 'checked' : ""} />Male</label>
                            	<label><input type="radio" id="Female" name="radio" value="Female" ${contact.getGender()=="Female" ? 'checked' : ""}  />Female</label>
                        	</div>
                    	</div>
                </div>
            </form>
            
            <div class=" col-sm-3"></div>
            <div class="col-sm-7" style="">
                <button type="button" name="cancel" class="btn btn-default" style="width:41.5%" onclick="window.location.replace('<%=request.getSession().getAttribute("goBackURL")%>')">Go back</button>
                <button type="button" name="save" class="btn btn-success" style="width:41.5%" onclick="validate();">Save</button>
                <img src="static_resources/images/YGgI.gif" alt="Loading." id="saveCLoading" style="display: none;">
                <img src="static_resources/images/tick.png" alt="Loading." id="saveCTick" style="display: none;">
                <input type="hidden" name="action" id="action" value="${param['action']}" />
                <input type="hidden" name="cid" id="cid" value="${param['mess']}" />
            </div>
</div><div class="col-sm-2"></div>
		</c:when>
		<c:otherwise>
			
		</c:otherwise>
	</c:choose>
</div>
<script src="static_resources/choosen/init.js" type="text/javascript" charset="utf-8"></script>
<%@ include file="Footer.txt" %>
<%-- <%@ include file="printPageContext.jsp" %> --%>
</body>
</html>