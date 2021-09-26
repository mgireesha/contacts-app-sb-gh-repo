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
	//gidocument.getElementById("fname").reportValidity();
	if (allfields()) {
		if (passwordStrength()) {
			if (matchPwd()) {
				if (validateemail()) {
					if (phonenumber()) {
						RegisterUser();
					}
				}
			}
		}
	}
}
function allfields() {
	var fname = document.register.fname;
	var email = document.register.email;
	var crtpwd = document.register.crtpwd;
	var crmpwd = document.register.crmpwd;
	if (!fname.reportValidity()) {
		//fname.setCustomValidity("Please fill in the full name.");
		fname.reportValidity();
	} else if (!email.reportValidity()) {
		//email.setCustomValidity("Please fill in a valid email id.");
		email.reportValidity();
	} else if (!crtpwd.reportValidity()) {
		//crtpwd.setCustomValidity("Please provide a strong new password");
		crtpwd.reportValidity();
	} else if (!crmpwd.reportValidity()) {
		//crmpwd.setCustomValidity("Please retype the password.");
		crmpwd.reportValidity();
	} else {
		return true;
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
function passwordStrength() {
	var pStrength = $("#pwdMeter").val();
	if(pStrength<100){
		alert("Provided password is weak. Please provide a strong password");
		return false;
	}else{
		return true;
	}
}
function CheckPasswordStrength(password) {
	var password_strength = document.getElementById("password_strength");
	var pwdMeterV = 0;
	//if textBox is empty
	if (password.length == 0) {
		password_strength.innerHTML = "&nbsp;Password Strength";
		$("#pwdMeter").val(0);
		return;
	}

	//Regular Expressions
	var regex = new Array();
	regex.push("[A-Z]"); //For Uppercase Alphabet
	regex.push("[a-z]"); //For Lowercase Alphabet
	regex.push("[0-9]"); //For Numeric Digits
	regex.push("[$@$!%*#?&]"); //For Special Characters

	var passed = 0;

	//Validation for each Regular Expression
	for (var i = 0; i < regex.length; i++) {
		if ((new RegExp(regex[i])).test(password)) {
			passed++;
		}
	}

	//Validation for Length of Password
	if (passed > 2 && password.length > 8) {
		passed++;
	}

	//Display of Status
	var color = "";
	var passwordStrength = "";
	switch (passed) {
		case 0:
			break;
		case 1:
			passwordStrength = "Password is Weak.";
			color = "Red";
			pwdMeterV = 20;
			break;
		case 2:
			passwordStrength = "Password is Good.";
			color = "darkorange";
			pwdMeterV = 40;
			break;
		case 3:
			passwordStrength = "Password is Good.";
			color = "darkorange";
			pwdMeterV = 60;
			break;
		case 4:
			passwordStrength = "Password is Strong.";
			color = "Green";
			pwdMeterV = 80;
			break;
		case 5:
			passwordStrength = "Password is Very Strong.";
			color = "darkgreen";
			pwdMeterV = 100;
			break;
	}
	password_strength.innerHTML = passwordStrength;
	password_strength.style.color = color;
	$("#pwdMeter").val(pwdMeterV);
}