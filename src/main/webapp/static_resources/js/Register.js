/*
*
*-------Modification History-------
* Sr No Date Change No Author Description
*------ ---- --------- ------ ------------
* 01 02 Nov 2017 CH01 Gireesh M T Initial Version
*
*
*
*@Author Gireesh M T*
*/
function validate()
{ //document.getElementById("register").submit();
if(matchPwd()){
if(allfields())
{
if(flength())
{
if(llength())
{
if(validateemail())
{
if(phonenumber())
{
	RegisterUser();
//document.register.submit();
//document.getElementById("register").submit();
}
}
}
}
}
}
}
function allfields()
{
var fname=document.register.fname.value;
var lname=document.register.lname.value;
var gender=document.register.radio.value;
var email=document.register.email.value;
var phone=document.register.phone.value;
var crtpwd=document.register.crtpwd.value;
var crmpwd=document.register.crmpwd.value;
if(fname && lname && gender && email && phone && crtpwd && crmpwd !=" ")
{
return true;
}
else
{
alert("All Fields are Mandatory");
return false;
}
}
function matchPwd(){
var crtpwd=document.register.crtpwd.value;
var crmpwd=document.register.crmpwd.value;
if(crtpwd==crmpwd){ return true;
}else{alert("Passwords should be same...");document.getElementById("crmpwd").focus();return false;}
}
function flength()
{
var fname=document.register.fname.value;
if(fname.length>2 && fname.length<16)
{
return true;
}
else
{
alert("First Name should contain 3 to 15 charactors only");
return false;
}
}
function llength()
{
var lname=document.register.lname.value;
if(lname.length>2 && lname.length<16)
{
return true;
}
else
{
alert("Last Name should contain 3 to 15 charactors only");
return false;
}
}
function phonenumber()
{
var phone=document.register.phone.value;
var phoneno = /^[(]{0,1}[0-9]{3}[)]{0,1}[-\s\.]{0,1}[0-9]{3}[-\s\.]{0,1}[0-9]{4}$/;
if(phone.match(phoneno))
{
return true;
}
else
{
alert("Please Enter Valid Phone Number");
return false;
}
}
function validateemail()
{
var email=document.register.email.value;
var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/;
if(email.match(email1))
{
return true;
}
else
{
alert("Please Enter Valid Email");
return true;
}
}
$(document).ready(function(){
var show_btn=$('.show-modal');
//var show_btn=$('.show-modal');
//$("#testmodal").modal('show');
show_btn.click(function(){
$("#testmodal").modal('show');
})
});
//$(function() {
// $('#element').on('click', function( e ) {
// Custombox.open({
// target: '#testmodal-1',
// effect: 'fadein'
// });
// e.preventDefault();
// });
// });

function RegisterUser(){
	var name = $("#fname").val()+$("#lname").val();
	var gender = $('input[name="radio"]:checked').val();
	var email = $("#email").val();
	var phone = $("#phone").val();
	var newPwd = $("#crtpwd").val();
	var crmPwd = $("#crmpwd").val();
	$(".respons").show();
	$('.regForm').hide();
	$("#saveBtn").hide();
	$.ajax({
		type : "POST",
		url : "registerUser",
		data : "&name="+name+"&email="+email+"&gender="+gender+"&phone="+phone+"&newPwd="+newPwd+"&crmPwd="+crmPwd,
		success : function(id){
			$('.respons').hide();
			$('.regSuccess').show();
		},
		error : function(error){}
	});
}