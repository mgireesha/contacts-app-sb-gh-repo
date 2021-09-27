/*
*
*-------Modification History-------
* Sr No Date Change No Author Description
*------ ---- --------- ------ ------------
* 01 06 Jan 2018 CH01 Gireesh M T Initial Version
*
*
*
*@Author Gireesh M T*
*/
function setSearchByInput(inputValue) {
	if (inputValue == 'name') {
		$("#searchBy").replaceWith("<a data-toggle='dropdown' id='searchBy' class='dropdown-toggle ' href='#' >Name<b class='caret'></b></a>");
		document.getElementById("searchByInput").value = '1';
	} else if (inputValue == 'city') {
		$("#searchBy").replaceWith("<a data-toggle='dropdown' id='searchBy' class='dropdown-toggle ' href='#' >City<b class='caret'></b></a>");
		document.getElementById("searchByInput").value = '2';
	} else if (inputValue == 'phone') {
		$("#searchBy").replaceWith("<a data-toggle='dropdown' id='searchBy' class='dropdown-toggle ' href='#' >Phone<b class='caret'></b></a>");
		document.getElementById("searchByInput").value = '3';
	}
}
function searchContact() {
	var search = document.getElementById("search").value;
	var searchby = document.getElementById("searchByInput").value;
	if (search != "" && searchby != "") {
		window.location.replace("listAllContacts.jsp?search=" + search + "&&searchby=" + searchby);
	} else if (searchby == "") {
		window.location.replace("listAllContacts.jsp?search=" + search + "&&searchby=1");
	} else if (search == "") {
		alert("Please Enter Some text to search");
	}
}
function sortContact(order) {
	window.location.replace("listAllContacts.jsp?sort=" + order);
}
function toggleTogglrBar(t) {
	if (t == 1) {
		$("#toggle").removeClass("hidden");
		$("#toggle").removeClass("visible-xs");
		$("#toggle").removeClass("visible-sm");
		document.getElementById("toggle").style.display = "none";
		document.getElementById("cross").style.display = "inline";
		$("#navbarCollapse").show();
	}
	if (t == 2) {
		$("#toggle").removeClass("hidden");
		$("#toggle").removeClass("visible-xs");
		$("#toggle").removeClass("visible-sm");
		document.getElementById("toggle").style.display = "inline";
		document.getElementById("cross").style.display = "none";
		$("#toggle").addClass("hidden");
		$("#toggle").addClass("visible-xs");
		$("#toggle").addClass("visible-sm");
		$("#navbarCollapse").hide();
	}
}
function togleDarkTheme() {
	var currentTheme = $("#currentTheme").val();
	if (currentTheme == "dark") {
		$("#currentTheme").val("light");
		$(".themetext").removeClass("darkthemetextColor");
		$(".themeContent").removeClass("darkThemeClass");
		$("#darkThemeButton").show();
		$("#lightThemeButton").hide();
	} else {
		$("#currentTheme").val("dark");
		$(".themetext").addClass("darkthemetextColor");
		$(".themeContent").addClass("darkThemeClass");
		$("#darkThemeButton").hide();
		$("#lightThemeButton").show();
	}
}