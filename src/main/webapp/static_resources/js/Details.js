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
function validate() {
	if (allfields()) {
				if (validateemail()) {
					if (phonenumber()) {
						saveCOntact();
					}
				}
	}
}
function allfields() {
	var fname = document.details.name;
	var phone = document.details.phone;
	
	if (!fname.reportValidity()) {
		fname.reportValidity();
	} else if (!phone.reportValidity()) {
		phone.reportValidity();
	} else {
		return true;
	}
}
function phonenumber() {
	var phone = document.details.phone.value;
	var phoneno = /^[(]{0,1}[0-9]{3}[)]{0,1}[-\s\.]{0,1}[0-9]{3}[-\s\.]{0,1}[0-9]{4}$/;
	if (phone.match(phoneno)) {
		return true;
	}
	else {
		alert("Please Enter Valid Phone Number");
		return false;
	}
}
function validateemail() {
	var email = document.details.email.value;
	if(email==""){
		return true;
	}
	var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/;
	if (email.match(email1)) {
		return true;
	}
	else {
		alert("Please Enter Valid Email");
		return true;
	}
}

function searchContactdepricatedbynewnavbar(searchingFrom, signedin, TableNameFromDb, NameFromDb) {
	if (searchingFrom == "Header") {
		var search = document.getElementById("search").value;
		var searchby = document.getElementById("searchby").value;
		searchby = 0;
		if (search != "" && searchby != "") {
			window.location.replace("listAllContacts.jsp?search=" + search + "&&searchby=" + searchby + "&&TableNameFromDb=" + TableNameFromDb + "&&NameFromDb=" + NameFromDb)
		} else if (searchby == "") {
			alert("Please Select a Column to search");
		} else if (search == "") {
			alert("Please Enter Some text to search");
		}
	}
	else if (searchingFrom == "Body") {
		if (signedin == 1) {
			var searchFrmDiv = document.getElementById("searchFrmDiv").value;
			if (searchFrmDiv != "") {

				var xhttp = new XMLHttpRequest();
				xhttp.onreadystatechange = function() {
					if (this.readyState == 4 && this.status == 200) {
						document.getElementById('searchedModalBody').innerHTML = this.responseText;
						$('#searchedModalFromBody').modal('show');
					}
				};
				xhttp.open("POST", "process-request.jsp?search=" + searchFrmDiv + "&&isSearch=YES&&NameFromDb=" + NameFromDb, true);
				xhttp.send();
				//window.location.replace("listAllContacts.jsp?search="+searchFrmDiv+"&&TableNameFromDb="+TableNameFromDb+"&&NameFromDb="+NameFromDb);
			} else {
				alert("Please Enter Some text to search");
			}
		} else { alert("Please signin to perform this action"); }
	}
}
function searchFromDiv() {
	var search = document.getElementById("searchFrmDiv").value;
	window.location.replace("listAllContacts.jsp?search=" + search)
}
// ajax function for lov in details jsp
$(document).ready(function() {
	$("select.state").change(function() {
		var selectedState = $(".state option:selected").val();
		$.ajax({
			type: "GET",
			url: "getCityList?state=" + selectedState,
			data: { country: selectedState }
		}).done(function(data) {
			$("#response").html(data);
		});
	});
});

function saveCOntact() {
	var name = $("#name").val();
	var gender = $('input[name="radio"]:checked').val();
	if(gender == undefined || gender == "undefined"){
		gender = "";
	}
	var email = $("#email").val();
	var phone = $("#phone").val();
	var address = $("#address").val();
	var state = $("#state").val();
	var city = $("#city").val();
	var action = $("#action").val();
	var cid = $("#cid").val();
	$("#saveCLoading").show();
	$.ajax({
		type: "POST",
		url: "saveContact",
		data: "&action=" + action + "&name=" + name + "&email=" + email + "&gender=" + gender + "&phone=" + phone + "&address=" + address + "&state=" + state + "&city=" + city + "&cid=" + cid,
		success: function(id) {
			$('#status,#saveCTick').show();
			if (action == "update") {
				$("#status").html("Contact updated successfully!");
			} else {
				$("#status").html("Contact saved successfully!");
				$("#action").val("update");
				$("#cid").val(id);
			}
			$("#saveCLoading").hide();
			setTimeout(function() {
				$('#status').fadeOut('fast');
				$('#saveCTick').fadeOut('fast');
			}, 3000);
		},
		error: function(error) {$("#saveCLoading").hide(); }
	});
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