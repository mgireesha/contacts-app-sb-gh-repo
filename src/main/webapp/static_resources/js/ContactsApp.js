/*-- js code for Home.jsp -->>> Start --*/
function submitSignedInForm(theForm,actionItem){
if(actionItem=="listAllBtn"){
document.signedInForm.action="listAllContacts.jsp";
document.signedInForm.submit();
}
if(actionItem=="addNewBtn"){
theForm.action="Details.jsp";
theForm.submit();
}
if(actionItem=="Header"){
theForm.action="Home.jsp";
theForm.submit();
}
}
$.fn.bootstrapBtn = $.fn.button.noConflict(); // if bootstrap.min.js is loaded after jquery-ui/jquery-ui.js, button conflict will arises and 'x' button
// in jquery-ui dialog(used in below function) will be hidden. to avoid the conflict, above statement is used.
function importContactsDiv(){
$( function() {
$( "#importDiv" ).dialog({
autoOpen: true,
modal: true,
width: 350,
height: 230,
show: {
effect: "blind",
duration: 1000
},
hide: {
effect: "fade",
duration: 300
}
});
} );
toggleTogglrBar('2');
}
function closeImportpopup(){ $("#closeImport").closest('.ui-dialog-content').dialog('close');}
function exportContactsDiv(){
$( function() {
$( "#exportDiv" ).dialog({
autoOpen: true,
modal: true,
width: 360,
height: 260,
show: {
effect: "blind",
duration: 1000
},
hide: {
effect: "fade",
duration: 300
}
});
} );
toggleTogglrBar('2');
}
function closeExportpopup(){ $("#closeExport").closest('.ui-dialog-content').dialog('close');}
function submitImportExportForm(action,exportFileType) {
if(action==1){
if(document.getElementById("inputFile").value!=""){
document.importForm.action="upload?action=import";
document.importForm.submit();
}else{
alert("Please select a file to import");
}
}else if(action==2){
	document.exportForm.action="exportContacts?action=export&&exportFileType="+exportFileType;
    document.exportForm.submit();
var contnt = "";
$.ajax({url: "utility.jsp?action=checkContacts", 
        success: function(data){
                    contnt = jQuery(data).filter("#cAvbty").val();
                    if(contnt=="No"){
                        alert("No contacts available for export");
                    }else{
                        document.exportForm.action="exportContacts?action=export&&exportFileType="+exportFileType;
                        document.exportForm.submit();
                    }
                }, 
        error: function(xhr){
                   // alert("An error occured: " + xhr.status + " " + xhr.statusText);
                }
    });

}else if(action==3){
var id = exportFileType;
if(document.getElementById("file-1").value!=""){
document.dpUploadForm.action="upload?action=dp&&action=dp&&id="+id;
}else{
alert("Please select a file to upload");
}
}
}

function quickAddModal(){
 $('#addC').modal('show');
}
/*-- js code for Home.jsp -->>> End --*/
/*-- js code for header action dropdown -->>> --*/
function handleSelect(action){
var condition=action.value;
var url;
if(condition==3){
url="LogOut";
window.location.replace(url);
}else if(condition==2){
url="accountSettings.jsp";
window.location.replace(url);
// $(document).ready(function(event){
// $("#testmod").modal('show');
//});
//
}
}
// ajax function for Password reset
//$(document).ready(function(){
// var pwd_reset=$('.pwdreset');
// pwd_reset.click(function(){
// var email=document.getElementById("regEmail").value;
// var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/;
// alert(email);
// if(email.match(email1))
// {
// var theForm=document.refEmailForm;
// theForm.registeredEmail.value=email;
// theForm.actionItm.value="validEmail";
// var registeredEmail=email;
//
// $.ajax({
// type: "POST",
// url: "send.jsp?registeredEmail="+registeredEmail,
// data: { country : selectedState }
// }).done(function(data){
// $("#respons").html(data);
// });
// }
// });
//});
$(document).ready(function(){
var pwd_reset=$('.pwdreset');
pwd_reset.click(function(event){
// event.preventDefault();
var xhttp;
var email=document.getElementById("regEmail").value;
var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/;
//alert(email);
var a="a";
if(email.match(email1) || a=="a")
{
var registeredEmail=email;
xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
if (this.readyState == 4 && this.status == 200) {
document.getElementById("loadingmodal").style.display = "none";
document.getElementById("respon").style.display = "inline";
document.getElementById("theresponse").style.display = "block";
document.getElementById("sendagain").style.display = "inline";
document.getElementById("sendagainp").style.display = "block";
document.getElementById("reenter").style.display = "inline";
document.getElementById("theresponse").innerHTML = this.responseText;
}
};
xhttp.open("GET", "send.jsp?registeredEmail="+registeredEmail, true);
xhttp.send();
document.getElementById("theresponse").style.display = "none";
document.getElementById("sendagain").style.display = "none";
document.getElementById("sendagainp").style.display = "none";
document.getElementById("loadingmodal").style.display = "block";
document.getElementById("invdiv").style.display = "none";
document.getElementById("respons").style.display = "none";
document.getElementById("reenter").style.display = "none";
// $(function() {
// $('#testmodal2').modal('show');
// });
}else{alert("Please enter valid email");}
});
});
/*-- js code for accountSettings.jsp -->>> Start --*/
function updatePassword(){
$('#delete-danger,#goodBye,#text-success,#view-success,#listContBtn,#daButton,#text-alertNull1,#text-alertNull').hide();
document.getElementById("matchpwd").style.display="none";
document.getElementById("invalidpwd").style.display="none";
//var cupwd=$('#cupwd').value;
var cupwd=document.getElementById("cupwd").value;
var nepwd=document.getElementById("nepwd").value;
var copwd=document.getElementById("copwd").value;
if(nepwd==copwd){
if(copwd==""){
$('#copwd').css('border-color', '#red');
document.getElementById("text-alertNull").style.display="block";
return;
}
if(nepwd==""){
$('#nepwd').css('border-color', '#red');
document.getElementById("text-alertNull1").style.display="block";
return;
}
//$.ajax({
// type: "POST",
// url: "updatepwd?cupwd="+cupwd+"&&copwd="+copwd,
// data:true
// }).done(function(data){
// //alert("hi");
// if(this.status==200){
// alert("hi");
// }
// //location.reload();
// $("#invalidpwd").html(data);
// });
var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function(){
if(xhttp.status==300){
$('#cupwd').css('border-color', 'red');
$('#cupwd').focus();
document.getElementById("invalidpwd").style.display="block";
}
if(xhttp.status==200){
$('#cupwd').css('border-color', '#bebdbd');
$('#copwd').css('border-color', '#bebdbd');
document.getElementById("text-success").style.display="block";
document.getElementById("view-success").style.display="none";
$('#accountSettingsUpdateModal').modal('show');
}
};
xhttp.open("POST", "updatepwd?cupwd="+cupwd+"&&copwd="+copwd, true);
xhttp.send();
}else{
$('#copwd,#nepwd').css('border-color', 'red');
$('#copwd').focus();
document.getElementById("matchpwd").style.display="block";
}
}
function myFunction() {
var x = document.getElementById("nepwd");
var y = document.getElementById("copwd");
if (x.type == "password") {
x.type = "text";
y.type = "text";
} else {
x.type = "password";
y.type = "password";
}
}
function updateView(){
var xhttp = new XMLHttpRequest();
var view = $("input[name='radio']:checked").val();
var isFromView="yes";
//alert(view);
xhttp.onreadystatechange = function(){
if(xhttp.status==200){
document.getElementById("text-success").style.display="none";
document.getElementById("view-success").style.display="block";
$('#listContBtn').show();
$('#accountSettingsUpdateModal').modal('show');
}
};
xhttp.open("POST", "updateView?view="+view+"&&isFromView="+isFromView, true);
xhttp.send();
}

function openDeleteAccountModal(){
document.getElementById("text-success").style.display="none";
document.getElementById("delete-danger").style.display="block";
document.getElementById("daButton").style.display="inline";
$('#accountSettingsUpdateModal').modal('show');
}

function deleteAccount(TableNameFromDb){
var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function(){
if(this.readyState == 4 && xhttp.status==200){
window.location.replace("LogOut");
}
};

xhttp.open("POST", "deleteAC?TableNameFromDb="+TableNameFromDb+"&&deleteAC=YES", true);
xhttp.send();

document.getElementById("text-success").style.display="none";
document.getElementById("delete-danger").style.display="none";
document.getElementById("daButton").style.display="none";
document.getElementById("goodBye").style.display="inline";

}

$(document).ready(function (){
$("#cancelBtn").click(function (){
$('#delete-danger,#goodBye,#text-success,#view-success,#listContBtn,#daButton').hide();
$('#accountSettingsUpdateModal').modal('hide');
})
});


/*-- js code for accountSettings.jsp -->>> End --*/
function hi(str){
//var Base64={_keyStr:"ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789+/=",encode:function(e){var t="";
//var n,r,i,s,o,u,a;var f=0;e=Base64._utf8_encode(e);while(f<e.length){n=e.charCodeAt(f++);r=e.charCodeAt(f++);i=e.charCodeAt(f++);s=n>>2;o=(n&3)<<4|r>>4;u=(r&15)<<2|i>>6;a=i&63;
//if(isNaN(r)){u=a=64}else if(isNaN(i)){a=64}t=t+this._keyStr.charAt(s)+this._keyStr.charAt(o)+this._keyStr.charAt(u)+this._keyStr.charAt(a)}return t},decode:
//function(e){var t="";var n,r,i;var s,o,u,a;var f=0;e=e.replace(/[^A-Za-z0-9+/=]/g,"");while(f<e.length){s=this._keyStr.indexOf(e.charAt(f++));o=this._keyStr.indexOf(e.charAt(f++));
//u=this._keyStr.indexOf(e.charAt(f++));a=this._keyStr.indexOf(e.charAt(f++));n=s<<2|o>>4;r=(o&15)<<4|u>>2;i=(u&3)<<6|a;t=t+String.fromCharCode(n);if(u!=64){t=t+String.fromCharCode(r)}if(a!=64){t=t+String.fromCharCode(i)}}t=Base64._utf8_decode(t);
//return t},_utf8_encode:function(e){e=e.replace(/rn/g,"n");var t="";for(var n=0;n<e.length;n++){var r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r)}
//else if(r>127&&r<2048){t+=String.fromCharCode(r>>6|192);t+=String.fromCharCode(r&63|128)}else{t+=String.fromCharCode(r>>12|224);t+=String.fromCharCode(r>>6&63|128);t+=String.fromCharCode(r&63|128)}}return t},_utf8_decode:
//function(e){var t="";var n=0;var r=c1=c2=0;while(n<e.length){r=e.charCodeAt(n);if(r<128){t+=String.fromCharCode(r);n++}else if(r>191&&r<224){c2=e.charCodeAt(n+1);t+=String.fromCharCode((r&31)<<6|c2&63);n+=2}
//else{c2=e.charCodeAt(n+1);c3=e.charCodeAt(n+2);t+=String.fromCharCode((r&15)<<12|(c2&63)<<6|c3&63);n+=3}}return t}}
var decodedString = atob(str);
alert(str);
alert(decodedString);
console.log(decodedString);
}
function getSession(){
var xhttp;
var email=document.getElementById("email").value;
var password=document.getElementById("password").value;
//var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,9})+$/;
var email1 = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})+$/;
// alert(email);
if(email.match(email1))
{
var registeredEmail=email;
xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
if (xhttp.readyState == 4 && xhttp.status == 200) {
location.reload();
location.reload();
}else if(xhttp.status == 300){
alert("hi ;-)");
}
};
xhttp.open("POST", "signIn.jsp?email="+registeredEmail+"&&password="+password+"&&createSessionForAjax=true", true);
xhttp.send();
document.getElementById("pwdId").style.display = "none";
document.getElementById("emailId").style.display = "none";
document.getElementById("sessionExpiredLogIn").style.display = "none";
document.getElementById("sessionExpiredCancel").style.display = "none";
document.getElementById("loading").style.display = "inline";
document.getElementById("sessionExpire1").style.display = "none";
document.getElementById("sessionExpire2").style.display = "inline";
}
}

function openChangeColour(){
    $("#colourPicker").modal();
}

function ResetPassword(){
document.getElementById("matchpwd").style.display="none";
document.getElementById("invalidpwd").style.display="none";
//var cupwd=$('#cupwd').value;
var otp=document.getElementById("otp").value;
var newpwd=document.getElementById("newpwd").value;
var conpwd=document.getElementById("conpwd").value;
var userName=$("#userName").val();

if(newpwd==conpwd){
 var xhttp = new XMLHttpRequest();
 xhttp.onreadystatechange = function(){
 if(xhttp.status==300){
        document.getElementById("invalidpwd").style.display="block";
        $('#cupwd').css('border-color', 'red');
        $('#cupwd').focus();
         }
         if(xhttp.status==200){
         $("#text-success").show();
         $("#signIn").show();
         $('#accountSettingsUpdateModal').modal('show');
         }
 };
 xhttp.open("POST", "updatepwd?cupwd="+otp+"&&copwd="+conpwd+"&&registeredEmail="+userName+"&&isFromForgoPwdMail=yes", true);
  xhttp.send();

}else{
    document.getElementById("matchpwd").style.display="block";
    $('#copwd').css('border-color', 'red');
    $('#copwd').focus();
}
}
function ASCancelfn(){
$('#listContacts,CV-success').hide();
$('#accountSettingsUpdateModal').modal('hide');
hideAll();
}

function sendPwdRs(){
var xhttp;
$("#invalidEmailMsg").hide();
var email=document.getElementById("regEmail").value;
var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/;
if(email.match(email1) && email!="")
{
var registeredEmail=email;
xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
if (this.readyState == 4 && this.status == 200) {
document.getElementById("loadingmodal").style.display = "none";
document.getElementById("respon").style.display = "block";
document.getElementById("theresponse").style.display = "block";
if(this.responseText=="NO_REG"){
	document.getElementById("theresponse").innerHTML ="The email you have entered is not registered.";
}else{
	document.getElementById("theresponse").innerHTML = this.responseText;
	document.getElementById("sendagain").style.display = "inline";
}

document.getElementById("sendagainp").style.display = "block";
document.getElementById("reenter").style.display = "inline";
}
};
xhttp.open("GET", "send-pw-reset-link?registeredEmail="+registeredEmail, true);
xhttp.send();
document.getElementById("theresponse").style.display = "none";
document.getElementById("sendagain").style.display = "none";
document.getElementById("sendagainp").style.display = "none";
document.getElementById("loadingmodal").style.display = "block";
document.getElementById("invdiv").style.display = "none";
document.getElementById("respons").style.display = "none";
document.getElementById("reenter").style.display = "none";
// $(function() {
// $('#testmodal2').modal('show');
// });
}else{
	if(email==""){
		document.getElementById("regEmail").setCustomValidity("This is required field.");
		document.getElementById("regEmail").reportValidity();
	}else{
		$("#invalidEmailMsg").show();
	}
}
}
function togleDarkTheme(){
	var currentTheme = $("#currentTheme").val();
	if(currentTheme == "dark"){
		$("#currentTheme").val("light");
		$(".control-label,.containe,.themetext").removeClass("darkthemetextColor");
		$(".mailHtml, .container-fluid").removeClass("darkThemeClass");
		$("#darkThemeButton").show();
		$("#lightThemeButton").hide();
	}else{
		$("#currentTheme").val("dark");
		$(".control-label,.containe,.themetext").addClass("darkthemetextColor");
		$(".mailHtml, .container-fluid").addClass("darkThemeClass");
		$("#darkThemeButton").hide();
		$("#lightThemeButton").show();
	}
}
