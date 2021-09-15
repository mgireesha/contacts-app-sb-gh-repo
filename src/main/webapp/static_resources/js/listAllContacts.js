/*
*
*-------Modification History-------
* Sr No Date Change No Author Description
*------ ---- --------- ------ ------------
* 01 18 Jan 2018 CH01 Gireesh M T Initial Version
*
*
*
*@Author Gireesh M T*
*/
// Global Variables -----start-----//
var hiddenArr=[];
// Global Variables -----End-----//

//  functions  to delete contact -----*----- Start -----***-----

function selectAll(source) {
//document.getElementById("DeleteIcon").style.display = "inline";
//document.getElementById("DeleteIconMobile").style.display = "inline";
$('#DeleteIcon,#DeleteIconMobile').show();
var checkboxes = document.querySelectorAll('input[type="checkbox"]');
for (var i = 0; i < checkboxes.length; i++) {
if (checkboxes[i] != source)
checkboxes[i].checked = source.checked;
}
showDeleteIcon();
}
function showDeleteIcon(){ // function to show/hide delete icon in header onclick of checkbox
$('#DeleteIcon,#DeleteIconMobile').show();
$(document).ready(function(){
$('input[type="checkbox"]').click(function(){
var checkedCheckBoxes = [];
$.each($("input[name='checkedbox']:checked"), function(){
checkedCheckBoxes.push($(this).val());
//alert(checkedCheckBoxes.length);
});
if(checkedCheckBoxes.length<=0){
document.getElementById("DeleteIcon").style.display = "none";
document.getElementById("DeleteIconMobile").style.display = "none";
}else{
document.getElementById("DeleteIcon").style.display = "inline";
document.getElementById("DeleteIconMobile").style.display = "inline";
}
});
});
}
function deleteCkeckBoxed(){ //delete function to delete contacts seleted by checkbox onclick of delete icon in header
var selectedCheckBoxes = [];
$.each($("input[name='checkedbox']:checked"), function(){
selectedCheckBoxes.push($(this).val());
});
document.getElementById("checkedBoxes").value=selectedCheckBoxes;
var checkedBoxes = document.getElementById("checkedBoxes").value;
var checkedBoxesArray = checkedBoxes.split(',');
//alert(checkedBoxesArray);
//var t = document.getElementById("tableNameDeleteCkeckBoxed").value;
//if (confirm("Are you sure to delete permanently...???")) {
$.ajax({
type: "POST",
url: "deleteSelected",
data: { checkedBoxes: checkedBoxes},
})
.done(function(data){
// location.reload();
//$("#tableAfterDelete").html(data);
for (var i in checkedBoxesArray){
//alert(checkedBoxesArray[i]);
$('#'+checkedBoxesArray[i]+'a').hide(); //to hide deleted rows after deleting from database
//$('#'+checkedBoxesArray[i]+'thumnailDiv').hide();
$('#'+checkedBoxesArray[i]+'thumnailDiv').remove();
countContacts();
$('#DeleteIconMobile,#DeleteIcon').popover('hide');
}
document.getElementById("DeleteIcon").style.display = "none";
document.getElementById("DeleteIconMobile").style.display = "none";
});
//}
//Code for snackbar showed upon delete contacts ----- start
var x = document.getElementById("snackbar")
x.className = "show";
setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
//Code for snackbar showed upon delete contacts ----- End
}
function deletecontact(id,tableName){ //delete function to delete single contact from table row and thumbnail view
$.ajax({
type: "DELETE",
url: "deleteContact?mess="+id,
data:true
})
.done(function(data){
// location.reload();
//$("#tableAfterDelete").html(data);
//alert('#'+s+'a');
$('#'+id+'a').hide();
//$('#'+id+'thumnailDiv').hide();
$('#'+id+'thumnailDiv').remove();
countContacts();
document.getElementById("deletePopOver").style.display="none";
});
//Code for snackbar showed upon delete contacts ----- start
var x = document.getElementById("snackbar")
x.className = "show";
setTimeout(function(){ x.className = x.className.replace("show", ""); }, 3000);
//Code for snackbar showed upon delete contacts ----- End
}

$(document).ready(function(){
$('#DeleteIconMobile,#DeleteIcon').popover({
placement : 'bottom',
html : true,
title : '<i><b>Are you sure to delete..??</b></i>',
content : '<div style="width:13.0em" id="deletePopOver"><button class="btn btn-danger btn-xs " style="margin-left:5%" onclick="deleteCkeckBoxed()"><i>delete</i></button><button class="btn btn-default btn-xs pull-right " id="cancel" data-dismiss="alert"><i>Cancel</i></button></div>'
});
$(document).on("click", "#close" , function(){
$('#DeleteIconMobile,#DeleteIcon').popover('hide');
//$(this).parents(".popover").popover('hide');
});
$(document).on("click", "#cancel" , function(){
$(this).parents(".popover").popover('hide');
});
});
//  functions  to delete contact -----*----- End -----***-----


// When the user scrolls down 20px from the top of the document, show the button
window.onscroll = function() {scrollFunction()};

function scrollFunction() {
    if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
        document.getElementById("myBtn").style.display = "block";
    } else {
        document.getElementById("myBtn").style.display = "none";
    }
}

// When the user clicks on the button, scroll to the top of the document
function topFunction() {
    document.body.scrollTop = 0;
    document.documentElement.scrollTop = 0;
}


function enableNameText(id){

var idOfaNameFromInput=$("#idOfaName").val();
$("#idOfaName").val(id);

$('#'+idOfaNameFromInput+'aname').show();
$('#'+idOfaNameFromInput+'iname').hide();

$('#'+id+'aname').hide();
$('#'+id+'iname').css('display','inline');
}

function updateName(id,name,TableNameFromDb){
name=name.value;
var xhttp = new XMLHttpRequest();
xhttp.onreadystatechange = function() {
if (xhttp.readyState == 4 && xhttp.status == 200) {
$('#'+id+'bname').replaceWith('<b id="'+id+'bname">'+name+'</b>');
$('#'+id+'iname').hide();
$('#'+id+'aname').show();
}else if(xhttp.status == 300){
alert("hi ;-)");
}
};
xhttp.open("POST", "process-request.jsp?id="+id+"&&name="+name+"&&isFromUpdateName=YES", true);
xhttp.send();
}

function searchTable(searchVal,ViewFromDb){
var t = document.getElementById('tableAfterDelete');
var NameIndex = 0;
if(ViewFromDb=="advanced"){
    NameIndex = 1;
}
var i = 1;
    searchVal=searchVal.toLowerCase();
    $("#tableAfterDelete tr").each(function() {
    try{
        var val1 = $(t.rows[i].cells[NameIndex]).text();
        var val2 = $(t.rows[i].cells[NameIndex+1]).text();
        var val3 = $(t.rows[i].cells[NameIndex+2]).text();
        if(val1!=undefined && val1!=""){
            val1=val1.toLowerCase();
        }
        if(val2!=undefined && val2!=""){
            val2=val2.toLowerCase();
        }
        if(val3!=undefined && val3!=""){
            val3=val3.toLowerCase();
        }
        if(val1.indexOf(searchVal)==-1 && val2.indexOf(searchVal)==-1 && val3.indexOf(searchVal)==-1){
            if(!jQuery(t.rows[i]).is(":hidden")){
                jQuery(t.rows[i]).hide();
                hiddenArr.push(jQuery(t.rows[i]).attr("id"));
            }
        }else{
            if(hiddenArr.includes(jQuery(t.rows[i]).attr("id"))){
                jQuery(t.rows[i]).show();
            }   
        }
    }catch(exception){
    }    
    i++;
}); 
}

function searchThumbnailView(searchVal){
    var id = 0;
    var thumbId;
    var cName;
    var phNum;
    jQuery("#thumbnailView > div ").each(function(){
    try{
        thumbId = jQuery(this).attr("id");
        id = thumbId.substring(0,6);
        cName = jQuery(this).find("#"+id+"aname").text();
        phNum = jQuery(this).find(".phNumber").text();
        if(cName!=undefined && cName!=""){
            cName=cName.toLowerCase();
        }
        if(phNum!=undefined && phNum!=""){
            phNum=phNum.toLowerCase();
        }
        if(cName.indexOf(searchVal)==-1 && phNum.indexOf(searchVal)==-1){
            if(!jQuery(this).is(":hidden")){
                jQuery(this).hide();
                hiddenArr.push(thumbId);
            }
        }else{
            if(hiddenArr.includes(thumbId)){
                jQuery(this).show();
            }   
        }
    }catch(exception){
    }
    });
}

function searchListedContacts(){
    var searchVal=jQuery("#search").val();
    var ViewFromDb = jQuery("#ViewFromDb").val();
    if(searchVal!=undefined && searchVal!=""){
        if(ViewFromDb=="thumbnail"){
            searchThumbnailView(searchVal,ViewFromDb);
        }else{
            searchTable(searchVal,ViewFromDb);
        }
    }else{
        for (var i in hiddenArr){
            jQuery("#"+hiddenArr[i]).show();
        }
        hiddenArr=[];
    }   
}

function countContacts(element){
    var count = jQuery("#thumbnailView > div ").length;
        /*$(element).attr("data-original-title",count);
	$(element).attr("rel","count");
	jQuery('[data-toggle="count"]').tooltip({placement : 'auto top'});*/
    jQuery("#countN").html(" = "+count);
}

function sortTable(index) {
	  var table, rows, switching, i, x, y, shouldSwitch;
	  table = document.getElementById("tableAfterDelete");
	  switching = true;
	  /*Make a loop that will continue until
	  no switching has been done:*/
	  while (switching) {
	    //start by saying: no switching is done:
	    switching = false;
	    rows = table.rows;
	    /*Loop through all table rows (except the
	    first, which contains table headers):*/
	    for (i = 1; i < (rows.length - 1); i++) {
	      //start by saying there should be no switching:
	      shouldSwitch = false;
	      /*Get the two elements you want to compare,
	      one from current row and one from the next:*/
	      x = rows[i].getElementsByTagName("TD")[index];
	      y = rows[i + 1].getElementsByTagName("TD")[index];
	      //check if the two rows should switch place:
	      if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
	        //if so, mark as a switch and break the loop:
	        shouldSwitch = true;
	        break;
	      }
	    }
	    if (shouldSwitch) {
	      /*If a switch has been marked, make the switch
	      and mark that a switch has been done:*/
	      rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
	      switching = true;
	    }
	  }
	}


var thArr = $("#tableAfterDelete > thead > tr > td");
for (let i = 0; i < thArr.length; i++) {
	thArr[i].addEventListener("click", sortTable(i));
}
	

