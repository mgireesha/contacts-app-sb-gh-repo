/*
*
*-------Modification History-------
* Sr No Date Change No Author Description
*------ ---- --------- ------ ------------
* 01 01 Oct 2017 CH01 Gireesh M T Initial Version
*
*
*
*@Author Gireesh M T*
*/
function validate()
{
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
	saveCOntact();
//document.details.submit();
//document.getElementById("details").submit();
}
}
}
}
}
}
function validateUpdate()
{
if(allfieldsUpdate())
{
if(validateemailUpdate())
{
if(phonenumberUpdate())
{
document.getElementById("update").submit();
}
}
}
}
function allfields()
{
var fname=document.details.name.value;
//var lname=document.details.lname.value;
var gender=document.details.radio.value;
var email=document.details.email.value;
var phone=document.details.phone.value;
var city=document.details.city.value;
var address=document.details.address.value;
var state=document.details.state.value;
if(fname && gender && email && phone && city && address && state !=" ")
{
return true;
}
else
{
alert("All Fields are Mandatory");
return false;
}
}
function allfieldsUpdate()
{
var name=document.update.name.value;
var gender=document.update.radio.value;
var email=document.update.email.value;
var phone=document.update.phone.value;
var city=document.update.city.value;
var address=document.update.address.value;
var state=document.update.state.value;
if(name && gender && email && phone && city && address && state !=" ")
{
return true;
}
else
{
alert("All Fields are Mandatory");
return false;
}
}
function flength()
{
var fname=document.details.name.value;
if(fname.length>2 && fname.length<31)
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
var lname=document.details.name.value;
if(lname.length>2 && lname.length<31)
{
return true;
}
else
{
//alert("Last Name should contain 3 to 15 charactors only");
return true;
}
}
function phonenumber()
{
var phone=document.details.phone.value;
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
function phonenumberUpdate()
{
var phone=document.update.phone.value;
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
var email=document.details.email.value;
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
function validateemailUpdate()
{
var email=document.update.email.value;
//var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,9})+$/;
var email1 = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})+$/;
if(email.match(email1))
{
return true;
}
else
{
alert("Please Enter Valid Email");
return false;
}
}
function gotohome(s,TableNameFromDb){
var mess=s;
$.ajax({
	type : "DELETE",
	url : "deleteContact?mess="+mess,
	data : true,
	Success : function(data){
		window.location.replace("listAll");
	},
	error : function(error){}
}).done(function(data){
	window.location.replace("listAll");
});

}
function gotodetails(s,TableNameFromDb){
var mess=s;
window.location.replace("/ViewController/Details.jsp?mess="+mess+"&&TableNameFromDb="+TableNameFromDb);
}
function gotoupdate(s,TableNameFromDb){
var mess=s;
window.location.replace("AddUpdate?mess="+mess+"&&action=update");
}
function sortlist(TableNameFromDb){
var order=document.getElementById("sort").value;
window.location.replace("listAllContacts.jsp?sort="+order+"&&TableNameFromDb="+TableNameFromDb)
}
function searchContactdepricatedbynewnavbar(searchingFrom,signedin,TableNameFromDb,NameFromDb){
if(searchingFrom=="Header"){
var search=document.getElementById("search").value;
var searchby=document.getElementById("searchby").value;
searchby=0;
if(search!="" && searchby!=""){
window.location.replace("listAllContacts.jsp?search="+search+"&&searchby="+searchby+"&&TableNameFromDb="+TableNameFromDb+"&&NameFromDb="+NameFromDb)
}else if(searchby==""){alert("Please Select a Column to search");
}else if(search==""){alert("Please Enter Some text to search");
}
}
else if(searchingFrom=="Body"){
if(signedin==1){
var searchFrmDiv=document.getElementById("searchFrmDiv").value;
if(searchFrmDiv!=""){

var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function(){
if(this.readyState == 4 && this.status == 200){
document.getElementById('searchedModalBody').innerHTML = this.responseText;
$('#searchedModalFromBody').modal('show');
}
};
xhttp.open("POST", "process-request.jsp?search="+searchFrmDiv+"&&isSearch=YES&&NameFromDb="+NameFromDb, true);
xhttp.send();



//window.location.replace("listAllContacts.jsp?search="+searchFrmDiv+"&&TableNameFromDb="+TableNameFromDb+"&&NameFromDb="+NameFromDb);
}else{
alert("Please Enter Some text to search");
}
}else{alert("Please signin to perform this action");}
}
}
function searchFromDiv(){
var search=document.getElementById("searchFrmDiv").value;
window.location.replace("listAllContacts.jsp?search="+search)
}
// ajax function for lov in details jsp
$(document).ready(function(){
$("select.state").change(function(){
var selectedState = $(".state option:selected").val();
$.ajax({
type: "GET",
url: "getCityList?state="+selectedState,
data: { country : selectedState }
}).done(function(data){
$("#response").html(data);
});
});
});

function saveCOntact(){
	var name = $("#name").val();
	var gender = $('input[name="radio"]:checked').val();
	var email = $("#email").val();
	var phone = $("#phone").val();
	var address = $("#address").val();
	var state = $("#state").val();
	var city = $("#city").val();
	var action = $("#action").val();
	var cid = $("#cid").val();
	
	$.ajax({
		type : "POST",
		url : "saveContact",
		data : "&action="+action+"&name="+name+"&email="+email+"&gender="+gender+"&phone="+phone+"&address="+address+"&state="+state+"&city="+city+"&cid="+cid,
		success : function(id){
			$('#status').show();
			if(action=="update"){
				$("#status").html("Contact updated successfully!");
			}else{
				$("#status").html("Contact saved successfully!");
				$("#action").val("update");
				$("#cid").val(id);
			}
			setTimeout(function() {
                $('#status').fadeOut('fast');
            }, 3000); 
		},
		error : function(error){}
	});
}