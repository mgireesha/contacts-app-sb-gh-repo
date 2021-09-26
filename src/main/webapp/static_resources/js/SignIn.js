/*
*
*-------Modification History-------
* Sr No Date Change No Author Description
*------ ---- --------- ------ ------------
* 01 05 Dec 2017 CH01 Gireesh M T Initial Version
*
*
*
*@Author Gireesh M T*
*/
function signIn(){
userName=document.getElementById("email").value;
passWord=document.getElementById("password").value;
if(userName!="" && passWord!=""){
document.getElementById("signInForm").submit();
}else if(userName==""){
alert("Please Enter Your User Name");
}else if(passWord==""){
alert("Please Enter Your Password");
}
}
//$(document).ready(function() {
// //$('#loader').hide();
// $("#testmodal1").modal('show');
//});
//$(document).ready(function() {
// //$('#loader').hide();
// $("#testmodal").modal('show');
//});
//$(document).ready(function(){
// $("#testmodal").load(function(){
// $("#testmodal").modal('show');
// });
//});
$(document).ready(function(event){
// event.preventDefault();
var show_reset=$('.show-reset');
//var show_btn=$('.show-modal');
//$("#testmodal").modal('show');
show_reset.click(function(){
$( '#testmodal2' ).modal();
// $("#testmodal2").modal('show');
})
});
$(document).ready(function(){
// event.preventDefault();
var show_reset1=$('.show-reset1');
//var show_btn=$('.show-modal');
//$("#testmodal").modal('show');
show_reset1.click(function(){
//alert(show_reset1);
document.getElementById("cancel1").style.display = "inline";
document.getElementById("invdiv").style.display = "inline";
$("#cancel,#sendagain,#theresponse,#sendagainp,#reenter,#respon,#invalidEmailMsg").hide();
$("#respons").show();
// $("#testmodal2").modal('show');
})
});
//$(document).ready(function(){
// var show_btn=$('.invdiv');
// //var show_btn=$('.show-modal');
// //$("#testmodal").modal('show');
//
// show_btn.click(function(){
// $("#testmodal1").modal('show');
// })
//});
function validateregemail()
{
var email=document.getElementById("regEmail").value;
var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/;
if(email.match(email1))
{
var theForm=document.refEmailForm;
theForm.registeredEmail.value=email;
theForm.actionItm.value="validEmail";
//alert(theForm.registeredEmail.value);
//alert(theForm.actionItm.value);
theForm.submit();
}
else
{
alert("Please Enter Valid Email Format");
return false;
}
}
function validateregemail1()
{
var email=document.getElementById("regEmail1").value;
var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/;
if(email.match(email1))
{
var theForm=document.refEmailForm;
theForm.registeredEmail.value=email;
theForm.actionItm.value="validEmail";
//alert(theForm.registeredEmail.value);
//alert(theForm.actionItm.value);
theForm.submit();
}
else
{
alert("Please Enter Valid Email Format");
return false;
}
}
function attachCheckboxHandlers() {
// get reference to element containing toppings checkboxes
var el = document.getElementById('toppings');
// get reference to input elements in toppings container element
var tops = el.getElementsByTagName('input');
// assign updateTotal function to onclick property of each checkbox
for (var i=0, len=tops.length; i<=len; i++) {
if ( tops[i].type === 'checkbox' ) {
tops[i].onclick = updateTotal;
}
}
}
// called onclick of toppings checkboxes
function updateTotal(e) {
// 'this' is reference to checkbox clicked on
var form = this.form;
// get current value in total text box, using parseFloat since it is a string
var val = parseFloat( form.elements['total'].value );
// if check box is checked, add its value to val, otherwise subtract it
if ( this.checked ) {
val += parseFloat(this.value);
} else {
val -= parseFloat(this.value);
}
// format val with correct number of decimal places
// and use it to update value of total text box
form.elements['total'].value = formatDecimal(val);
}
// format val to n number of decimal places
// modified version of Danny Goodman's (JS Bible)
function formatDecimal(val, n) {
n = n || 2;
var str = "" + Math.round ( parseFloat(val) * Math.pow(10, n) );
while (str.length <= n) {
str = "0" + str;
}
var pt = str.length - n;
return str.slice(0,pt) + "." + str.slice(pt);
}
// in script segment below form
attachCheckboxHandlers();

function showRegModal(){
	$("#RegisterModal").modal('show');
}

function closeRegModal(){
	$("#RegisterModal").modal("hide");
}