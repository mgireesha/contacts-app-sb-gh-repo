<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
    <%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Contacts App</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<script src="static_resources/bootstrap_3.3.7/dist/js/bootstrap.min.js" ></script>

<script type="text/javascript" src="static_resources/js/ContactsApp.js"></script>
<link href="static_resources/css/Details.css" rel="stylesheet" />
<link href="static_resources/css/accountSettings.css" rel="stylesheet" />
<link href="static_resources/bootstrap_3.3.7/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<%@ include file="Header.txt"%>
<div class="container-fluid well">
<div class="col-sm-7" style="margin-top:2.5em">
    <form action="UpdatePwd" method="POST" name="updatePwd" id="updatePwd" class="form-horizontal">
        <div class="alert alert-danger" style="height:50px;width:80%">
            <p class="text-success"><strong>Update Password:</strong></p>
        </div>
    <div class="form-group" style="margin-top:3em;">
            <label class="control-label col-sm-3">Current Password</label>
        <div class="col-sm-6" >
             <input type="password" id="cupwd" name="cupwd" class="form-control" style="" autofocus="autofocus" />  
        </div>
        <div class="col-sm-3"><p id="invalidpwd" style="color:red;margin-left:1em;margin-top:0.3em;display:none">Invalid Password</p></div>
    </div>
    <div class="form-group" style="margin-top:2em;">
            <label class="control-label col-sm-3">New Password</label>
        <div class="col-sm-6">
            <input type="password" class="form-control" id="nepwd" name="nepwd" style="" />
        </div>
        <div class="col-sm-3"><p id="text-alertNull1" style="color:red;margin-left:0.0em;display:none">Field cannot be empty</p>
        </div>
    </div>
    <div class="form-group" style="margin-top:2em;">
            <label class="control-label col-sm-3">Confirm Password</label>
        <div class="col-sm-6">
            <input type="password" class="form-control" id="copwd" name="copwd" style="" />
        </div>
        <div class="col-sm-3"><p id="matchpwd" style="color:red;margin-left:0.0em;display:none">Password doesn't match</p>
                              <p id="text-alertNull" style="color:red;margin-left:0.0em;display:none">Field cannot be empty</p>
        </div>
    </div>
    <div class="form-group" style="margin-top:0.1em;">
        <div class="col-sm-3">
        </div>
        <div class="col-sm-7">
            <input type="checkbox" onclick="myFunction()">Show Password
        </div>
    </div>
    <div class="form-group" style="margin-top:2em;">
            <label class="control-label col-sm-3"></label>
        <div class="col-sm-6">
            <button type="button" class="btn btn-primary active" id="uppwd" name="uppwd" 
                    style="width:100%;" onclick="updatePassword()"><b><i>Update Password</i></b></button>
        </div>
    </div>   
    <div class="form-group" style="margin-top:2em;">
            <label class="control-label col-sm-3"></label>
        <div class="col-sm-6">
            <button type="button" class="btn btn-primary active" id="uppwd" name="uppwd" style="width:100%;" onclick="openChangeColour()"><b><i>Change Colour</i></b></button>
        </div>
    </div>
    </form>
    
    
</div>
<div class="col-sm-1 hidden visible-lg">
    <img src="static_resources/images/divider1.png" alt="verticle" class="wel" />
</div>

<div class="col-sm-4" style="margin-top:3em">
    <div class="alert alert-danger" style="height:50px;width:100%">
        <p class="text-success"><strong>Update View:</strong></p>
    </div>
    <div style="margin-top:2.0em">                          
        <label class="containe">Basic
            <input type="radio" ${sessionScope.View eq 'basic' ? 'checked' : ""} name="radio" value="basic">
            <span class="checkmark"></span>
        </label>
        <label class="containe">Advanced
            <input type="radio" ${sessionScope.View eq 'advanced' ? 'checked' : ""} name="radio" value="advanced">
            <span class="checkmark"></span>
        </label>
        <label class="containe">Thumbnail
            <input type="radio" ${sessionScope.View eq 'thumbnail' ? 'checked' : ""} name="radio" value="thumbnail">
            <span class="checkmark"></span>
        </label>
        <button type="button" class="btn btn-primary active col-sm-6" id="upv" name="upv" 
                style="margin-bottom:5.0em;width:100%;" onclick="updateView()"><b><i>Update View</i></b></button>
    </div>
    
    
    <div style="margin-top:1.5em">                          
        
        <button type="button" class="btn btn-primary active col-sm-6" id="da" name="da" 
                style="margin-bottom:5.0em;width:100%;" onclick="openDeleteAccountModal()"><b><i>Delete your account</i></b></button>
    </div>
    
</div>
    
</div> 
    
    
    
<div id="accountSettingsUpdateModal" class="modal fade" data-backdrop="static" style="top: 25%;left:10%">
    <div class="modal-dialog">
        <div class="modal-content" style="width:85%">
            <div class="modal-header" style="">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" style="color:#428bca"><i><b>Account Settings</b></i></h4>
            </div>
            <div class="modal-body">
                <h5 class="text-success" id="text-success" style="color:Green;">Your Password has been updated succesfully...</h5>
                <h5 class="" id="view-success" style="display:none">View has been updated succesfully...</h5>
                <h5 class="text-danger" id="delete-danger" style="display:none">You will lose all your contacts.., <br/> Are you sure to delete your account...</h5>
                <h5 class="text-danger" id="goodBye" style="display:none"><b><i>Good Bye......</i></b></h5>
            </div> 
            <div class="modal-footer">
            <button class="btn btn-primary" onclick="window.location.replace('listAll')" style="display:none" id="listContBtn">list Contacts</button>
            <button class="btn btn-danger" id="daButton" onclick="deleteAccount('${sessionScope.TableName}')" style="display:none">Delete</button>
            <button class="btn btn-default" id="cancelBtn" aria-label="Close" >Cancel</button>
            </div>
        </div>
    </div>
</div>
	<input type="hidden" name="TableNameFromDb" id="TableNameFromDb" value="${sessionScope.tableName}" />
    <%@ include file="colourPicker.html" %>
     <%@ include file="Footer.txt" %>
</body>
</html>