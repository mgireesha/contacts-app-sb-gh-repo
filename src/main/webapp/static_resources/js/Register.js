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
function validate() {
	if (matchPwd()) {
		if (allfields()) {
			if (validateemail()) {
				if (phonenumber()) {
					RegisterUser();
				}
			}
		}
	}
}
function allfields() {
	var fname = document.register.fname.value;
	var email = document.register.email.value;
	var phone = document.register.phone.value;
	var crtpwd = document.register.crtpwd.value;
	var crmpwd = document.register.crmpwd.value;
	if (fname && email && phone && crtpwd && crmpwd != " ") {
		return true;
	}
	else {
		alert("All Fields are Mandatory");
		return false;
	}
}
function matchPwd() {
	var crtpwd = document.register.crtpwd.value;
	var crmpwd = document.register.crmpwd.value;
	if (crtpwd == crmpwd) {
		return true;
	} else { alert("Passwords should be same..."); document.getElementById("crmpwd").focus(); return false; }
}
function phonenumber() {
	var phone = document.register.phone.value;
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
	var email = document.register.email.value;
	var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/;
	if (email.match(email1)) {
		return true;
	}
	else {
		alert("Please Enter Valid Email");
		return true;
	}
}
$(document).ready(function() {
	var show_btn = $('.show-modal');
	show_btn.click(function() {
		$("#testmodal").modal('show');
	})
});

function RegisterUser() {
	var name = $("#fname").val();
	var gender = $('input[name="radio"]:checked').val();
	var email = $("#email").val();
	var phone = $("#phone").val();
	var newPwd = $("#crtpwd").val();
	var crmPwd = $("#crmpwd").val();
	$(".respons").show();
	$('.regForm').hide();
	$("#saveBtn").hide();
	$.ajax({
		type: "POST",
		url: "registerUser",
		data: "&name=" + name + "&email=" + email + "&phone=" + phone + "&newPwd=" + newPwd + "&crmPwd=" + crmPwd,
		success: function(id) {
			$("#RegisterModal").css("top","4em");
			$('.respons').hide();
			$('.regSuccess').show();
		},
		error: function(error) { }
	});
}