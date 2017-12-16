<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Reports</title>
<link href="../Payroll/resources/css/dataTables.bootstrap.min.css" rel="stylesheet"/>
<style type="text/css">
select {
	min-width: 200px;
	min-height: 30px;
}

.buttonPadding {
	padding: 5px;
}
.btn-color{
	background-color: #0101DF;
}

.rptTblClass table {
	border-collapse: collapse;
	width: 100%;
	float: left;
	margin: 0;
  	padding: 0;
	border: 1px solid #aaa;
	table-layout: auto;
}

.rptTblClass th, td {
	text-align: left;
	padding: 5px;
}

.rptTblClass tr:nth-child(even) {
	background-color: #f2f2f2;
}

.rptTblClass th {
	background-color: #8B9DC3;
	color: white;
}

table.dataTable thead .sorting:after, table.dataTable thead .sorting_asc:after, table.dataTable thead .sorting_desc:after, table.dataTable thead .sorting_asc_disabled:after, table.dataTable thead .sorting_desc_disabled:after {
display: none;
}

.dataTables_wrapper .dataTables_paginate .paginate_button{
color: white;
}

.dataTables_wrapper .dataTables_paginate .paginate_button a:{
  	color: #fff;
	border-radius:5px;
	background-color: #3b589a;
}

.dataTables_wrapper .dataTables_paginate .paginate_button a:hover{
  	color: #fff;
	border-radius:5px;
	background-color: #3b589a;
}
 
.dataTables_wrapper .dataTables_paginate .paginate_button a:active {
  	color: #fff; 
	background-color: #8B9DC3;
	border-radius:5px;
	margin-right: 5px;
}
</style>
<jsp:include page="../jsp/public/postHeader.jsp" />
<script src="../Payroll/resources/js/jquery.dataTables.min.js"></script>
<script src="../Payroll/resources/js/dataTables.bootstrap.min.js"></script>
<script type="text/javascript">
$(document).ready(function() {
	$("#downloadLink").click(function(event) {
		window.location = "../Payroll/employeeRptDownload";
	});
	
	$('#empRptTable').DataTable({
		"info" : false,
		"paging" : false,
	  	"filter" : false,
	  	"lengthChange" : false,
	  	"autoWidth" : true,
	  	"pagingType" : "full"
 	});
	
	$('#searchBtn').click(function(event) {
		$("#formSearch").attr("action", "../Payroll/employeeReport");
		$("#formSearch").submit();
	});
});
      </script>
</head>
<body >
	<div class="contain-wrapp bodyDivCss">	
	
	<jsp:include page="../jsp/employeeSearch.jsp" />
	
	<c:if test="${sessionScope.employees.size() gt 0}">
	<div  class="container" class="row" style ="position: relative;">
	<div id="empListDiv" style ="width:100%;overflow-x: auto;overflow-y: auto;min-height:10px;max-height:380px;">
		<table id="empRptTable" class="rptTblClass table table-striped table-bordered table-hover table-responsive">
		<thead>
			<tr>
			<th>Name</th>
			<th>Department</th>
			<th>Head</th>
			<th>Designation</th>
			<th>Address</th>
			<th>Date of Birth</th>
			<th>Gender</th>
			<th>Joining Date</th>
			<th>Phone</th>
			<th>Email</th>
			<th>Aadhar Number</th>
			<th>PAN</th>
			<th>Year</th>
			<th>Basic Pay</th>
			<th>Grade Pay</th>
			<th>Scale Pay</th>
			<th>Scale Increment</th>
			</tr></thead>
			<c:forEach var="employee" items="${sessionScope.employees}">
			<tr>
			<td> ${employee.fullName} </td>
			<td> ${employee.department}</td>
			<td> ${employee.headName}</td>
			<td> ${employee.designation}</td>
			<td> ${employee.address}</td>
			<td> ${employee.dob} </td>
			<td> ${employee.gender}</td>
			<td> ${employee.joiningDate}</td>
			<td> ${employee.phone}</td>
			<td> ${employee.email}</td>
			<td> ${employee.adharNo} </td>
			<td> ${employee.pan} </td>
			<td> ${employee.salaryVo.year}</td>
			<td> ${employee.salaryVo.basic}</td>
			<td> ${employee.salaryVo.gradePay}</td>
			<td> ${employee.salaryVo.scalePay}</td>
			<td> ${employee.salaryVo.scaleInc}</td>
			</tr>
			</c:forEach>
		</table>
		</div>
		</div>
	</c:if>
	</div>			
	
	<jsp:include page="../jsp/public/postFooter.jsp" />
</body>
</html>