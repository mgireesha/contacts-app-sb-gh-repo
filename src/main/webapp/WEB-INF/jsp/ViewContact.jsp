<%@page import="com.capp.springboot.object.Contact"%>
<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Contacts App</title>
<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script type="text/javascript" src="static_resources/bootstrap_3.3.7/dist/js/bootstrap.min.js"></script>
<link href="static_resources/bootstrap_3.3.7/dist/css/bootstrap.min.css" rel="stylesheet" />
<script type="text/javascript" src="static_resources/js/Details.js"></script>
<script type="text/javascript" src="static_resources/js/ContactsApp.js"></script>
<link href="static_resources/css/Details.css" rel="stylesheet" />
</head>
<body>
<%@ include file="Header.txt" %>
<%-- <%
	String tableName2 =(String) request.getSession().getAttribute("tableName");
System.out.println("view jsp tablename "+tableName2);
	Contact cont = (Contact)request.getAttribute("contact");
	File file1 = new File("D:\\SWorkspace\\ContactsAppSpringBoot\\src\\main\\webapp\\static_resources\\user_images\\"+tableName2+"\\"+cont.getId()+".jpg");
	boolean isImgExists = file1.exists();
	System.out.println("view jsp isImgExists "+isImgExists);
%> --%>
<div class="col-sm-1" ></div>
    <div class="well col-sm-9" style="height:100%;">
    
	<h2 style="width:30%;display:inline;" class="text-success"><i><b>Contact Details:</b></i></h2>
    
    <div class="col-sm-3" style="float:right;margin-top:1.0em">
    
    <c:choose>
		<c:when test="${isImgExists=='true'}">
    		<img src="static_resources/user_images/${sessionScope.tableName}/${contact.getId()}.jpg" alt="dp" style="width:100%;height:100%" />
    	</c:when>
    	<c:otherwise>
    		<c:choose>
    			<c:when test="${contact.getGender()=='Male'}">
    				<img src="static_resources/images/dp-male.png" alt="Avatar" style="width:100%" >
    			</c:when>
    			<c:when test="${contact.getGender()=='Female'}">
    				<img src="static_resources/images/dp-female.png" alt="Avatar" style="width:100%" >
    			</c:when>
    			<c:otherwise>
    				<img src="static_resources/images/user.png" alt="Avatar" style="width:100%" >
    			</c:otherwise>
    		</c:choose>
    	</c:otherwise>
    </c:choose>
    <form name="dpUploadForm" action="" method="POST" enctype = "multipart/form-data" style="margin-top:1.0em">
    <script>(function(e,t,n){var r=e.querySelectorAll("html")[0];r.className=r.className.replace(/(^|\s)no-js(\s|$)/,"$1js$2")})(document,window,0);</script>
    <div class="content">
    <div class="box">
					<input type="file" name="file-1[]" id="file-1" class="inputfile inputfile-1" data-multiple-caption="{count} files selected" multiple style="width:0em;height:0em;"  />
					<label for="file-1"><svg xmlns="http://www.w3.org/2000/svg" width="20" height="17" viewBox="0 0 20 17"><path d="M10 0l-5.2 4.9h3.3v5.1h3.8v-5.1h3.3l-5.2-4.9zm9.3 11.5l-3.2-2.1h-2l3.4 2.6h-3.5c-.1 0-.2.1-.2.1l-.8 2.3h-6l-.8-2.2c-.1-.1-.1-.2-.2-.2h-3.6l3.4-2.6h-2l-3.2 2.1c-.4.3-.7 1-.6 1.5l.6 3.1c.1.5.7.9 1.2.9h16.3c.6 0 1.1-.4 1.3-.9l.6-3.1c.1-.5-.2-1.2-.7-1.5z"/></svg> <span>Choose a file&hellip;</span></label>
                                        <button class="btn btn-info btn-xs" style="width:6.0em" onclick="submitImportExportForm('3','${param['mess']}')">update</button>
                                </div>
                                </div>
                                </form>

    </div>
    <div style="margin-top:25.0em" class="hidden visible-xs visible-sm"></div>
    <div class="row" style="margin-top:2.0em">
    <div class="col-sm-1" ></div>
    <div class="col-sm-7" >
    <div class="row">
    <label class="col-md-3">Name&nbsp&nbsp&nbsp&nbsp</label><label class="col-md-9">${contact.getName()}</label>
    </div>
    <div class="row">
    <label class="col-md-3">Email&nbsp&nbsp&nbsp&nbsp</label><label class="col-md-9">${contact.getEmail()}</label>
    </div>
    <div class="row">
    <label class="col-md-3">Phone&nbsp&nbsp&nbsp</label><label class="col-md-9">${contact.getPhone()}</label>
    <div class="row">
    </div>
    <label class="col-md-3">Gender&nbsp&nbsp</label><label class="col-md-9">${contact.getGender()}</label>
    </div>
    <div class="row">
    <label class="col-md-3">Address</label><label class="col-md-9">${contact.getAddress()}</label>
    </div>
    <div class="row">
    <label class="col-md-3">City&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp&nbsp</label><label class="col-md-9">${contact.getCity()}</label>
    </div>
    <div class="row">
    <label class="col-md-3">State&nbsp&nbsp&nbsp&nbsp&nbsp</label><label class="col-md-9">${contact.getState()}</label>
    </div>
    <div class="col-sm-3">
    </div>
    <button name="update" id="update" onClick="gotoupdate(${contact.getId()},'${TableName}')" class="btn btn-info btn-xs">Update</button>
    <button name="delete" id="delete" onclick="gotohome(${contact.getId()},'${TableName}')" class="btn btn-danger btn-xs">Delete</button>
    <button name="cancel" id="cancel" onclick="window.location.replace('listAll')" class="btn btn-warning btn-xs">cancel</button>
    </div>
    
   
   
   
   </div>
    </div>
<%@ include file="Footer.txt" %>
</body>
</html>