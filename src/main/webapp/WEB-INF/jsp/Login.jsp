<!DOCTYPE html>
<html>
<head>
<meta name="viewport" content="width=device-width, initial-scale=1">
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
  <meta charset="utf-8">
  <title>Contacts App</title>
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
  <script type="text/javascript" src="static_resources/js/SignIn.js"></script>
  <script type="text/javascript" src="static_resources/js/ContactsApp.js"></script>
  <script type="text/javascript" src="static_resources/js/Register.js"></script>
  <link href="static_resources/css/Login.css" rel="stylesheet" />
<script src="static_resources/bootstrap_3.3.7/dist/js/bootstrap.min.js" ></script>
<link href="static_resources/bootstrap_3.3.7/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body>
<%@ include file="/WEB-INF/jsp/Header.txt" %>
<div id="id01" class="container">
  
  <form class="animate" action="login" method="post">
    <div class="imgcontainer">
 	<img src="user.png" alt="Avatar" class="avatar" style="width: 10%">
    </div>

    <div class="lFormCtnr container">
      <label for="uname"><b>Username</b></label>
      <input class="lInput" type="text" placeholder="Enter Username" name="uname" required>

      <label for="psw"><b>Password</b></label>
      <input class="lInput" type="password" placeholder="Enter Password" name="psw" required>
     <c:if test="${not empty errorMsg}">
     	<c:if test="${errorMsg ne 'Success'}">
      	<label style="color:red">${errorMsg}</label>
      </c:if>
     </c:if>
      
      <button class="lbutton" type="submit">Login</button>
      <label>
        <input type="checkbox" checked="checked" name="remember"> Remember me
      </label>
    </div>

    <div class="container" style="background-color:#f1f1f1;margin-left: 1em;">
      <!-- <span class="show-reset">Forgot <a href="#">password?</a></span>
      <span>|</span>
      <span onclick="showRegModal()" >New User ? <a href="#">Register here</a></span> -->
      <div class="row">
      		<div class="col-sm-3" style="padding: 0.5em"><span class="show-reset">Forgot password ? <a href="#">Click here to reset</a></span></div>
      		<div class="col-sm-3" style="padding: 0.5em"><span onclick="showRegModal()" >New User ? <a href="#">Register here</a></span></div>
      		<div class="col-sm-2" style="padding: 0.5em"><span class=""><a href="#">About APP</a></span></div>
      		<div class="col-sm-4" style="padding: 0.5em"><span onclick="" ><a href="#" style="text-decoration: none;">Write to us @ Contactsapppwdreset@gmail.com</a></span></div>
      </div>
    </div>
  </form>
</div>

<%@ include file="/WEB-INF/jsp/Footer.txt" %>
<div id="testmodal2" class="modal fade " data-backdrop="static" style="padding-top:120px">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" style="color:red">Password Reset</h4>
            </div>
            <div class="modal-body respons" id="loadingmodal" style="display:none;">
    			<div id="loading-imag" ><p id="signtex">Please wait, we are verifying your email address.</p>
  					<div class="loadingDiv">
  						<img  src="static_resources/images/Loading.gif" alt="Loading." style="height:80px;width:80px;" />
  					</div>
  				</div>
			</div>
        
            <div class="modal-body respon" id="respon" style="display:none;">
            	<p id="theresponse" style="margin-left:10px"></p>
            	<!-- <p style="color:red;margin-left:10px" id="sendagainp">Didn't recieve your password, click below to send again</p> -->
            	<button class="btn btn-warning btn-xs invdiv pwdreset" id="sendagain"  style="margin-left:10px" onClick="sendPwdRs()" title="Didn't recieve your password, click this button to send again">Send again</button><button  class="btn btn-primary btn-xs show-reset1" id="reenter" style="display:none;margin-left:10px">Re-Enter Eamial</button>
            </div>
            <div class="modal-body respons" id="respons" style="display:block;">
                <h5>Please Enter Your Registered Email to Initiate Password Reset</h5>
                <input type="text" name="regEmail" id="regEmail" class="form-control" autofocus />
                <span id="invalidEmailMsg" style="color: red;display: none;">Please enter valid email</span>
            </div>
            <div class="modal-footer" id="modalfooter">
            	<button class="btn btn-default" data-dismiss="modal" id="cancel" aria-label="Close">Close</button>
                <button type="button" class="btn btn-primary invdiv pwdreset" id="invdiv"  onClick="sendPwdRs()" >Send Email</button><span>
                <button class="btn btn-default" data-dismiss="modal" id="cancel1" aria-label="Close" style="display:none">Close</button></span>
            </div>
        </div>
    </div>
</div>

<div id="RegisterModal" class="modal fade " data-backdrop="static" style="padding-top:2em">
    <div class="modal-dialog">
        <div class="modal-content" style="width: 100%">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title" style="color:green">Register</h4>
            </div>
            <div class="modal-body respons" id="loadingmodal" style="display:none;">
    			<div id="loading-imag" ><p id="signtex">Please wait, we are registering your user.....</p>
  					<img  src="static_resources/images/Loading.gif" alt="Loading..." style="height:80px;width:80px;margin-left:240px;margin-top:17px" /></div>
			</div>
        
            <div class="modal-body regSuccess" id="respon" style="display:none;">
            	<p id="theresponse" style="margin-left:10px"></p>
            	<p style="color:green;margin-left:10px" id="sendagainp">You are Successfully registered. Click cancel and login..</p>
            </div>
            <div class="modal-body regForm" id="regForm" style="display:block;">
                <div class="alert alert-warning">
                <form action="Registering.jsp" method="POST" name="register" id="register" class="form-horizontal">
                <div class="form-group">
                    <label class="control-label col-sm-4">First Name</label>
                    <div class="col-sm-7">
                        <input type="text" id="fname" name="fname" class="form-control"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4">Last Name</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="lname" name="lname"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4">Gender</label>
                    <div class="col-sm-7">
                        <div class="radio ">
                            <label>
                                <input type="radio" id="Male" name="radio" value="Male"/>
                                Male
                            </label>
                             
                            <label>
                                <input type="radio" id="Female" name="radio" value="Female"/>
                                Female
                            </label>
                        </div>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4">Email</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="email" name="email"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4">Phone</label>
                    <div class="col-sm-7">
                        <input type="text" class="form-control" id="phone" name="phone"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4">Create New Password</label>
                    <div class="col-sm-7">
                        <input type="password" class="form-control" id="crtpwd" name="crtpwd"/>
                    </div>
                </div>
                <div class="form-group">
                    <label class="control-label col-sm-4">Confirm Password</label>
                    <div class="col-sm-7">
                        <input type="password" class="form-control" id="crmpwd" name="crmpwd"/>
                    </div>
                </div>
                <input type="hidden" name="AfterSave" value="details.jsp"/>
                <input type="hidden" name="view" value="basic"/>
                
            </form>
           </div>
            </div>
            <div class="modal-footer" id="modalfooter">
            	<button type="button" name="save" id="saveBtn" class="btn btn-success" style="width:41.5%;" onclick="validate();">Submit</button>
                <button type="button" name="cancel" class="btn btn-default" style="width:41.5%;" onClick="closeRegModal()">Cancel</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>
