<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Contacts App</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="static_resources/bootstrap_3.3.7/dist/js/bootstrap.min.js" ></script>

<script type="text/javascript" src="static_resources/js/ContactsApp.js"></script>
<link href="static_resources/css/Details.css" rel="stylesheet" />
<link href="static_resources/css/accountSettings.css" rel="stylesheet" />
<link href="static_resources/bootstrap_3.3.7/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<%@ include file="Header.txt"%>
	<div class="container-fluid ">
		<c:if test="${isFromPmail eq 'Yes' }">
			<div class="col-sm-8 well" style="margin-top:0.5em">
    <form action="UpdatePwd" method="POST" name="updatePwd" id="updatePwd" class="form-horizontal">
        <div class="alert alert-danger" style="height:50px;width:80%">
            <p class="text-success"><strong>Reset Password:</strong></p>
        </div>
    <div class="form-group" style="margin-top:3em;">
            <label class="control-label col-sm-3">One Time Password</label>
        <div class="col-sm-6" >
             <input type="password" id="otp" name="otp" class="form-control" style="" autofocus="autofocus" />  
        </div>
        <div class="col-sm-3"><p id="invalidpwd" style="color:red;margin-left:1em;margin-top:0.3em;display:none">Invalid OTP</p></div>
        <div class="col-sm-3"><p id="emptypwd" style="color:red;margin-left:1em;margin-top:0.3em;display:none">Field cannot be empty</p></div>
    </div>
    <div class="form-group" style="margin-top:2em;">
            <label class="control-label col-sm-3">New Password</label>
        <div class="col-sm-6">
            <input type="password" class="form-control" id="newpwd" name="newpwd" style="" />
        </div>
    </div>
    <div class="form-group" style="margin-top:2em;">
            <label class="control-label col-sm-3">Confirm Password</label>
        <div class="col-sm-6">
            <input type="password" class="form-control" id="conpwd" name="conpwd" style="" />
        </div>
        <div class="col-sm-3"><p id="matchpwd" style="color:red;margin-left:0.0em;display:none">Password doesn't match</p></div>
    </div>
    <div class="form-group" style="margin-top:0.1em;">
        <div class="col-sm-3">
        </div>
        <div class="col-sm-7">
            <input type="checkbox" onclick="myFunction('r')" />Show Password
        </div>
    </div>
    <div class="form-group" style="margin-top:2em;">
            <label class="control-label col-sm-3"></label>
        <div class="col-sm-6">
            <button type="button" class="btn btn-primary active" id="uppwd" name="uppwd" 
                    style="width:100%;" onclick="ResetPassword()"><b><i>Update Password</i></b></button>
        </div>
    </div>
    <input type="hidden" name="userName" id="userName" value="${userEmail}" />
    </form>
</div>
		</c:if>
	</div>
	<div id="accountSettingsUpdateModal" class="modal fade" data-backdrop="static" style="top: 25%;left:10%">
    <div class="modal-dialog">
        <div class="modal-content" style="width:85%">
            <div class="modal-header" style="background-color:#333333">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" style="color:#428bca"><i><b>Account Settings</b></i></h4>
            </div>
            <div class="modal-body">
                <h5 class="text-success" id="text-success" style="color:Green;display:none">Your Password has been updated succesfully...</h5>
                <h5 class="text-success" id="view-success" style="display:none">View has been updated succesfully...</h5>  
                <h5 class="text-success" id="CV-success" style="display:none">CV settings have been updated succesfully...</h5>
            </div> 
            <div class="modal-footer">
            <button class="btn btn-primary" id="listContacts" onclick="window.location.replace('listAllContacts.jsp')" style="display:none">List Contacts</button>
            <button class="btn btn-primary" id="signIn" onclick="window.location.replace('https://contacts-app-sb.herokuapp.com/')" style="display:none">Goto LogIn</button>
            <button class="btn btn-danger" id="delete" onclick="deleteAccount()" style="display:none">Delete</button>
            <button class="btn btn-default"  id="ASCancel" onclick="ASCancelfn()">Cancel</button>            
            </div>
        </div>
    </div>
</div>
	<%@ include file="Footer.txt" %>
</body>
</html>