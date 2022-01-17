/*-- js code for Home.jsp -->>> Start --*/
function submitSignedInForm(theForm, actionItem) {
	if (actionItem == "listAllBtn") {
		document.signedInForm.action = "listAllContacts.jsp";
		document.signedInForm.submit();
	}
	if (actionItem == "addNewBtn") {
		theForm.action = "Details.jsp";
		theForm.submit();
	}
	if (actionItem == "Header") {
		theForm.action = "Home.jsp";
		theForm.submit();
	}
}
$.fn.bootstrapBtn = $.fn.button.noConflict(); // if bootstrap.min.js is loaded after jquery-ui/jquery-ui.js, button conflict will arises and 'x' button
// in jquery-ui dialog(used in below function) will be hidden. to avoid the conflict, above statement is used.
function importContactsDiv() {
	$(function() {
		$("#importDiv").dialog({
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
	});
	toggleTogglrBar('2');
}
function closeImportpopup() { $("#closeImport").closest('.ui-dialog-content').dialog('close'); }
function exportContactsDiv() {
	$(function() {
		$("#exportDiv").dialog({
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
	});
	toggleTogglrBar('2');
}
function closeExportpopup() { $("#closeExport").closest('.ui-dialog-content').dialog('close'); }
function submitImportExportForm(action, exportFileType) {
	if (action == 1) {
		if (document.getElementById("inputFile").value != "") {
			document.importForm.action = "upload?action=import";
			document.importForm.submit();
		} else {
			alert("Please select a file to import");
		}
	} else if (action == 2) {
		document.exportForm.action = "exportContacts?action=export&&exportFileType=" + exportFileType;
		document.exportForm.submit();
		var contnt = "";
		$.ajax({
			url: "utility.jsp?action=checkContacts",
			success: function(data) {
				contnt = jQuery(data).filter("#cAvbty").val();
				if (contnt == "No") {
					alert("No contacts available for export");
				} else {
					document.exportForm.action = "exportContacts?action=export&&exportFileType=" + exportFileType;
					document.exportForm.submit();
				}
			},
			error: function(xhr) {
				// alert("An error occured: " + xhr.status + " " + xhr.statusText);
			}
		});

	} else if (action == 3) {
		var id = exportFileType;
		if (document.getElementById("file-1").value != "") {
			document.dpUploadForm.action = "upload?action=dp&&action=dp&&id=" + id;
		} else {
			alert("Please select a file to upload");
		}
	}
}

function quickAddModal() {
	$('#addC').modal('show');
}
/*-- js code for Home.jsp -->>> End --*/
/*-- js code for header action dropdown -->>> --*/
function handleSelect(action) {
	var condition = action.value;
	var url;
	if (condition == 3) {
		url = "LogOut";
		window.location.replace(url);
	} else if (condition == 2) {
		url = "accountSettings.jsp";
		window.location.replace(url);
	}
}
$(document).ready(function() {
	var pwd_reset = $('.pwdreset');
	pwd_reset.click(function(event) {
		var xhttp;
		var email = document.getElementById("regEmail").value;
		var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/;
		var a = "a";
		if (email.match(email1) || a == "a") {
			var registeredEmail = email;
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
			xhttp.open("GET", "send.jsp?registeredEmail=" + registeredEmail, true);
			xhttp.send();
			document.getElementById("theresponse").style.display = "none";
			document.getElementById("sendagain").style.display = "none";
			document.getElementById("sendagainp").style.display = "none";
			document.getElementById("loadingmodal").style.display = "block";
			document.getElementById("invdiv").style.display = "none";
			document.getElementById("respons").style.display = "none";
			document.getElementById("reenter").style.display = "none";
		} else { alert("Please enter valid email"); }
	});
});
/*-- js code for accountSettings.jsp -->>> Start --*/
function updatePassword() {
	$('#delete-danger,#goodBye,#text-success,#view-success,#listContBtn,#daButton,#text-alertNull1,#text-alertNull,#text-alertNullCuPwd').hide();
	$('#nepwd,#cupwd,#copwd').css('border-color', '');
	document.getElementById("matchpwd").style.display = "none";
	document.getElementById("invalidpwd").style.display = "none";
	var cupwd = document.getElementById("cupwd").value;
	var nepwd = document.getElementById("nepwd").value;
	var copwd = document.getElementById("copwd").value;
	if (cupwd == "") {
		$('#cupwd').css('border-color', 'red');
		document.getElementById("text-alertNullCuPwd").style.display = "block";
		$('#cupwd').focus();
		return;
	}
	if (nepwd == copwd) {
		if (nepwd == "") {
			$('#nepwd').css('border-color', 'red');
			document.getElementById("text-alertNull1").style.display = "block";
			$('#nepwd').focus();
			return;
		}
		if (copwd == "") {
			$('#copwd').css('border-color', 'red');
			document.getElementById("text-alertNull").style.display = "block";
			$('#copwd').focus();
			return;
		}
		if (passwordStrength()) {
			$('#cPwdLoading').show();
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.status == 300) {
					$('#cupwd').css('border-color', 'red');
					$('#cupwd').focus();
					document.getElementById("invalidpwd").style.display = "block";
					$('#cPwdLoading').hide();
				}
				if (xhttp.status == 500) {
					$('#cupwd').css('border-color', 'red');
					$('#cupwd').focus();
					document.getElementById("invalidpwd").style.display = "block";
					$('#cPwdLoading').hide();
				}
				if (xhttp.status == 200) {
					$('#cupwd,#copwd,#nepwd').val('');
					$('#password_strength').css('color','');
					$('#password_strength').html('&nbsp;Password Strength');
					document.getElementById("text-success").style.display = "block";
					document.getElementById("view-success").style.display = "none";
					$('#accountSettingsUpdateModal').modal('show');
					$('#cPwdLoading').hide();
				}
			};
			xhttp.open("POST", "updatepwd?cupwd=" + cupwd + "&&copwd=" + copwd, true);
			xhttp.send();
		}
	} else {
		$('#copwd,#nepwd').css('border-color', 'red');
		$('#copwd').focus();
		document.getElementById("matchpwd").style.display = "block";
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
function updateView() {
	var xhttp = new XMLHttpRequest();
	var view = $("input[name='radio']:checked").val();
	var isFromView = "yes";
	xhttp.onreadystatechange = function() {
		if (xhttp.status == 200) {
			document.getElementById("text-success").style.display = "none";
			document.getElementById("view-success").style.display = "block";
			$('#listContBtn').show();
			$('#accountSettingsUpdateModal').modal('show');
		}
	};
	xhttp.open("POST", "updateView?view=" + view + "&&isFromView=" + isFromView, true);
	xhttp.send();
}

function openDeleteAccountModal() {
	//document.getElementById("text-success").style.display = "none";
	//document.getElementById("delete-danger").style.display = "block";
	//document.getElementById("daButton").style.display = "inline";
	$("#daButton").attr("disabled","disabled");
	$("#delACCmPwd").val("");
	$("#text-success,#delete-danger-confirm,#delACTick,#delACLoading,#delete-danger-wrg-pwd").hide();
	$("#delete-danger,#daButton,#deleACPwdVerifyBtn").show();
	$('#accountSettingsUpdateModal').modal('show');
}

function deleteAccount() {
	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && xhttp.status == 200) {
			
			window.location.replace("LogOut");
		}
		if (this.readyState == 4 && xhttp.status == 300) {
			$("#delACLoading").hide();
		}
	};

	xhttp.open("POST", "deleteAC", true);
	xhttp.send();

	$("#text-success,#delete-danger,daButton,#delete-danger-confirm,#delete-danger-wrg-pwd").hide();
	document.getElementById("goodBye").style.display = "inline";

}

function deleACPwdVerify() {
	$("#delACLoading").show();
	$("#deleACPwdVerifyBtn,#delete-danger-wrg-pwd").hide();
	$("#delACCmPwd").css("border-color","");
	var pwd = $("#delACCmPwd").val();
	if (!pwd == "") {
		var xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && xhttp.status == 200) {
				$("#delACLoading").hide();
				$("#delACTick").show();
				$("#daButton").removeAttr("disabled");
			}
			if (this.readyState == 4 && xhttp.status == 300) {
				$("#delACLoading").hide();
				$("#delete-danger-wrg-pwd").html(xhttp.responseText);
				$("#delete-danger-wrg-pwd,#deleACPwdVerifyBtn").show();
				$("#delACCmPwd").css("border-color","red");
				$("#delACCmPwd").focus();
			}
		};

		xhttp.open("GET", "deleteACPwdVerify?pwd=" + pwd, true);
		xhttp.send();
	} else {
		$("#delACLoading").hide();
		$("#deleACPwdVerifyBtn").show();
		//$("#delete-danger-wrg-pwd").html("Tis is required fied");
		$("#delACCmPwd").css("border-color","red");
		$("#delACCmPwd").focus();
	}
}

$(document).ready(function() {
	$("#cancelBtn").click(function() {
		$('#delete-danger,#goodBye,#text-success,#view-success,#listContBtn,#daButton').hide();
		$('#accountSettingsUpdateModal').modal('hide');
	})
});

function closeAccountSettingsUpdateModal(){
	$('#delete-danger,#goodBye,#text-success,#view-success,#listContBtn,#daButton,#delete-danger-confirm').hide();
	$('#accountSettingsUpdateModal').modal('hide');
	$("#delACCmPwd").val("");
}

/*-- js code for accountSettings.jsp -->>> End --*/
function getSession() {
	var xhttp;
	var email = document.getElementById("email").value;
	var password = document.getElementById("password").value;
	var email1 = /^([a-zA-Z0-9_\-\.]+)@([a-zA-Z0-9_\-\.]+)\.([a-zA-Z]{2,5})+$/;
	if (email.match(email1)) {
		var registeredEmail = email;
		xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (xhttp.readyState == 4 && xhttp.status == 200) {
				location.reload();
				location.reload();
			} else if (xhttp.status == 300) {
				alert("hi ;-)");
			}
		};
		xhttp.open("POST", "signIn.jsp?email=" + registeredEmail + "&&password=" + password + "&&createSessionForAjax=true", true);
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

function openChangeColour() {
	$("#colourPicker").modal();
}

function ResetPassword() {
	$("#matchpwd,#invalidpwd,#emptypwd,#emptyNewPwd").hide();
	$('#newpwd,#otp,#conpwd').css('border-color', '');
	var otp = document.getElementById("otp").value;
	var newpwd = document.getElementById("newpwd").value;
	var conpwd = document.getElementById("conpwd").value;
	var userName = $("#userName").val();
	
	if (otp == "") {
		$('#otp').css('border-color', 'red');
		document.getElementById("emptypwd").style.display = "block";
		$('#otp').focus();
		return;
	}
	
	if (newpwd == conpwd) {
		if (newpwd == "") {
			$('#newpwd').css('border-color', 'red');
			document.getElementById("emptyNewPwd").style.display = "block";
			$('#newpwd').focus();
			return;
		}
		if (passwordStrength()) {
			$('#cPwdLoading').show();
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (xhttp.status == 300) {
					document.getElementById("invalidpwd").style.display = "block";
					$('#cupwd').css('border-color', 'red');
					$('#cupwd').focus();
					$('#cPwdLoading').hide();
				}
				if (xhttp.status == 200) {
					$("#text-success").show();
					$("#signIn").show();
					$('#accountSettingsUpdateModal').modal('show');
					$('#cPwdLoading').hide();
				}
			};
			xhttp.open("POST", "updatepwd?cupwd=" + otp + "&&copwd=" + conpwd + "&&registeredEmail=" + userName + "&&isFromForgoPwdMail=yes", true);
			xhttp.send();
		}
	} else {
		document.getElementById("matchpwd").style.display = "block";
		$('#conpwd').css('border-color', 'red');
		$('#conpwd').focus();
	}
}
function ASCancelfn() {
	$('#listContacts,CV-success').hide();
	$('#accountSettingsUpdateModal').modal('hide');
	hideAll();
}

function sendPwdRs() {
	var xhttp;
	$("#invalidEmailMsg").hide();
	var email = document.getElementById("regEmail").value;
	var email1 = /^\w+([\.-]?\ w+)*@\w+([\.-]?\ w+)*(\.\w{2,3})+$/;
	if (email.match(email1) && email != "") {
		var registeredEmail = email;
		xhttp = new XMLHttpRequest();
		xhttp.onreadystatechange = function() {
			if (this.readyState == 4 && this.status == 200) {
				document.getElementById("loadingmodal").style.display = "none";
				document.getElementById("respon").style.display = "block";
				document.getElementById("theresponse").style.display = "block";
				if (this.responseText == "NO_REG") {
					document.getElementById("theresponse").innerHTML = "The email you have entered is not registered.";
				} else {
					document.getElementById("theresponse").innerHTML = this.responseText;
					document.getElementById("sendagain").style.display = "inline";
				}

				document.getElementById("sendagainp").style.display = "block";
				document.getElementById("reenter").style.display = "inline";
			}
		};
		xhttp.open("GET", "send-pw-reset-link?registeredEmail=" + registeredEmail, true);
		xhttp.send();
		document.getElementById("theresponse").style.display = "none";
		document.getElementById("sendagain").style.display = "none";
		document.getElementById("sendagainp").style.display = "none";
		document.getElementById("loadingmodal").style.display = "block";
		document.getElementById("invdiv").style.display = "none";
		document.getElementById("respons").style.display = "none";
		document.getElementById("reenter").style.display = "none";
	} else {
		if (email == "") {
			document.getElementById("regEmail").setCustomValidity("This is required field.");
			document.getElementById("regEmail").reportValidity();
		} else {
			$("#invalidEmailMsg").show();
		}
	}
}

function passwordStrength() {
	var pStrength = $("#pwdMeter").val();
	if(pStrength<80){
		$('#nepwd').css('border-color', 'red');
		$('#nepwd').focus();
		alert("Provided password is weak. Please provide a strong password");
		return false;
	}else{
		$('#nepwd').css('border-color', '');
		return true;
	}
}
function CheckPasswordStrength(password) {
	var password_strength = document.getElementById("password_strength");
	var pwdMeterV = 0;
	//if textBox is empty
	if (password.length == 0) {
		password_strength.innerHTML = "Password Strength";
		password_strength.style.color = '';
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
			$('#nepwd').css('border-color', '');
			break;
		case 5:
			passwordStrength = "Password is Very Strong.";
			color = "#27a727";
			pwdMeterV = 100;
			break;
	}
	password_strength.innerHTML = passwordStrength;
	password_strength.style.color = color;
	$("#pwdMeter").val(pwdMeterV);
}

function deleACConfirm(){
	$('#delete-danger').hide();
	$('#delete-danger-confirm').show();
}

function viewHidePwd() {
	var x = document.getElementById("newpwd");
	var y = document.getElementById("conpwd");
	if (x.type == "password") {
		x.type = "text";
		y.type = "text";
	} else {
		x.type = "password";
		y.type = "password";
	}
}

