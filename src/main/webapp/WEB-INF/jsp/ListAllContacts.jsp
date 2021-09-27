<%@page import="java.io.File"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1" isELIgnored="false"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Contacts App</title>

<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
<link href="static_resources/js/jquery-ui/jquery-ui.css" rel="stylesheet" />
<script type="text/javascript" src="static_resources/js/listAllContacts.js"></script>
<script type="text/javascript" src="static_resources/js/ContactsApp.js"></script>
<script type="text/javascript" src="static_resources/js/Details.js"></script>
<link href="static_resources/css/Details.css" rel="stylesheet" />
<link href="static_resources/css/Home.css" rel="stylesheet" />
<script type="text/javascript" src="static_resources/bootstrap_3.3.7/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="static_resources/js/jquery-ui/jquery-ui.js"></script>
<link href="static_resources/bootstrap_3.3.7/dist/css/bootstrap.min.css" rel="stylesheet" />
</head>
<body class="themeContent">
<%@ include file="Header.txt" %>
	<div class="container-fluid" style="background-image:url()">
		<button onclick="topFunction()" class="btn btn-primary" id="myBtn" title="Go to top">Top</button>
		<c:if test="${View ne 'thumbnail'}">
			<div style="overflow-x: auto;">
				<table class="table table-striped table-bordered table-hover table-condensed table-responsive"
					id="tableAfterDelete" style="text-align: center;">
					<thead>
						<tr class="info">
							<c:if test="${View eq 'advanced'}">
								<td><i><b>All</b></i>&nbsp&nbsp<input type="checkbox"
									onclick="selectAll(this);" /></td>
							</c:if>
							<td><i><b>Name</b></i></td>
							<td><i><b>Email</b></i></td>
							<td><i><b>Phone</b></i></td>
							<c:if test="${View ne 'advanced'}">
								<td><i><b>Gender</b></i></td>
								<td><i><b>City</b></i></td>
								<td><i><b>Address</b></i></td>
								<td colspan="4"><i><b>Operations</b></i></td>
							</c:if>
						</tr>
					</thead>
					<tbody>
						<c:forEach items="${cList}" var="ct">
							<tr id='${ct.getId()}a'>
								<c:if test="${View eq 'advanced'}">
									<td><input type="checkbox" value="${ct.getId()}"
										name="checkedbox" onclick="showDeleteIcon()" /></td>
									<td><a href="viewContact?mess=${ct.getId()}">${ct.getName()}</a></td>
									<td>${ct.getEmail()}</td>
									<td>${ct.getPhone()}</td>
								</c:if>
								<c:if test="${View ne 'advanced'}">
									<td>${ct.getName()}</td>
									<td>${ct.getEmail()}</td>
									<td>${ct.getPhone()}</td>
									<td>${ct.getGender()}</td>
									<td>${ct.getCity()}</td>
									<td>${ct.getAddress()}</td>
									<td><button name="update" id="update" onClick="window.location.replace('AddUpdate?action=update&&mess=${ct.getId()}')" class="btn btn-info btn-xs">
											<img alt="s" src="static_resources/images/edit.png" style="height: 1.8em; width: 1.5em;" />
										</button>
									</td>
									<td><button name="delete" id='${ct.getId()}' onclick="deletecontact('${ct.getId()}','${TableName}')" class="btn btn-danger btn-xs">
											<i><img alt="s" src="static_resources/images/delete.png" style="height: 1.8em; width: 1.5em;" /></i>
										</button>
									</td>
								</c:if>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</div>
		</c:if>
		<c:if test="${View eq 'thumbnail'}">
			<div class="listAlBoard row"
				style="">
				<div class="col-sm-5 alert alert-danger">
					<i><b>Select All</b></i>&nbsp&nbsp<input type="checkbox"
						onclick="selectAll(this);" /> <span>&nbsp;&nbsp;|&nbsp;&nbsp;
					</span> <a onclick="quickAddModal()"
						style="cursor: pointer; text-decoration: none;">Add new
						contact</a> <span>&nbsp;&nbsp;|&nbsp;&nbsp; </span> <a
						onmouseover="countContacts(this)" id="countC" data-toggle="count"
						style="cursor: pointer; text-decoration: none;">Count</a> <span
						id="countN"> = ${cList.size()}</span>
				</div>
			</div>
			<div class="thumbnailRow row" id="thumbnailView"
				style="width: 100%;">
				<%File file1 = null; %>
				<c:forEach items="${cList}" var="ct">
					<div class="col-sm-3" id='${ct.getId()}thumnailDiv'>
						<div class="card">
							<a href="viewContact?mess=${ct.getId()}"> <c:choose>
									<c:when test="${not empty ct.getDp()}">
										<img src="static_resources/user_images/${TableName}/${ct.getId()}.jpg" alt="Avatar"
											style="width: 100%; height: 100%" onerror=this.src="static_resources/images/user.png">
									</c:when>
									<c:otherwise>
										<c:choose>
											<c:when test="${ct.getGender() eq 'Male' }">
												<img src="static_resources/user_images/${TableName}/${ct.getId()}.jpg" onerror=this.src="static_resources/images/dp-male.png" alt="Avatar"
													style="width: 100%; height: 100%">
											</c:when>
											<c:when test="${ct.getGender() eq 'Female'}">
												<img src="static_resources/user_images/${TableName}/${ct.getId()}.jpg" onerror=this.src="static_resources/images/dp-female.png"
													alt="Avatar" style="width: 100%; height: 100%">
											</c:when>
											<c:otherwise>
												<img src="static_resources/images/user.png" alt="Avatar"
													style="width: 100%; height: 100%">
											</c:otherwise>
										</c:choose>
									</c:otherwise>
								</c:choose>
							</a>
							<div class="container-fluid">
								<h4>
									<a href="AddUpdate?action=update&&mess=${ct.getId()}"
										id='${ct.getId()}aname'><b
										id='${ct.getId()}bname'>${ct.getName()}</b></a> <input
										type="text" id='${ct.getId()}iname' class="form-control"
										value='${ct.getName()}' style="display: none; width: 80%"
										onblur="updateName('${ct.getId()}',this,'${TableName}')" /> <img
										alt="edit" src="static_resources/images/edit.png"
										style="height: 1.0em; width: 1.0em;"
										onclick="enableNameText(${ct.getId()})" />
								</h4>
								<p class="phNumber">${ct.getPhone()}&nbsp&nbsp<input
										type="checkbox" value="${ct.getId()}" name="checkedbox"
										onclick="showDeleteIcon()" />
								</p>
								<button class="btn btn-primary btn-xs"
									onClick="gotoupdate('${ct.getId()}','${TableName}')">
									<i>edit</i>
								</button>
								<button class="btn btn-danger btn-xs" id="thumbnailDelete"
									onclick="deletecontact('${ct.getId()}','${TableName}')">
									<i>delete</i>
								</button>
							</div>
						</div>
					</div>
				</c:forEach>
			</div>
		</c:if>
		<input type="hidden" id="checkedBoxes" name="checkedBoxes" />
		<div id="snackbar" style="display: none;">Contact has been deleted..</div><%--Div for snackbar showed upon delete contacts --%>
	</div>
	 <input type="hidden" name="ViewFromDb" id="ViewFromDb" value='${View}' />
<%@ include file="Footer.txt" %>
<%@ include file="importCDiv.txt" %>
<%@ include file="exportCdiv.txt" %>
</body>
</html>