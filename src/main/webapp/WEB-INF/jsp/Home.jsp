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
<script type="text/javascript" src="static_resources/js/jquery-ui/jquery-ui.js" ></script>
<link href="static_resources/js/jquery-ui/jquery-ui.css" rel="stylesheet" />
<script type="text/javascript" src="static_resources/js/ContactsApp.js"></script>
<script type="text/javascript" src="static_resources/js/Details.js"></script>
<script type="text/javascript" src="static_resources/js/SignIn.js"></script>
<link href="static_resources/css/Details.css" rel="stylesheet" />
<link href="static_resources/css/Home.css" rel="stylesheet" />
<link href="static_resources/bootstrap_3.3.7/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<%@ include file="Header.txt" %>
<c:choose>
	<c:when test="${errorMsg eq 'Success'}">
	<div class="container-fluid">
		<div class="col-sm-7 well" style="height:100%;border-radius:5px;">
			<div style="padding-top:1.0em;margin-left:5%;margin-right:5%;">
        	<button class="btn btn-info home-btn button" onclick="window.location.replace('listAll')">List All Contacts</button>
        	<button class="btn btn-info home-btn button" onclick="window.location.replace('AddUpdate')">Add a New Contact</button>
        	<button class="btn btn-warning home-btn button" onclick="importContactsDiv()" style="margin-top:1.0em">Import Contacts</button>
        	<button class="btn btn-warning home-btn button" onclick="exportContactsDiv()" style="margin-top:1.0em">Export Contacts</button>    
    	</div>
				<%@ include file="importCDiv.txt" %>
				<%@ include file="exportCdiv.txt" %>
		</div>
	</div>
	</c:when>
	<c:otherwise>
		<div class="alert alert-danger">
			<h1>Login Failed ${errorMsg}</h1>
		</div>
	</c:otherwise>
</c:choose>
<%@ include file="Footer.txt" %>
</body>
</html>