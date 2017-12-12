<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<title>Reports</title>

<%-- <link href="../Payroll/resources/css/jquery.dataTables.min.css" rel="stylesheet"/> --%>
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
		window.location = "../Payroll/empSalaryRptDownload";
	});
	
	$('#empRptTable').DataTable({
		"info" : false,
		"paging" : false,
	  	"filter" : false,
	  	"lengthChange" : false,
	  	"autoWidth" : true,
	  	"pagingType" : "full"
 	});
	
	<c:if test="${sessionScope.empSalaryReport.size() gt 0}">
		$('#searchDiv').hide();
	</c:if>
	
	$('#modifySearch').click(function(event) {
		$('#searchDiv').show();
		$('#closeSearch').show();
		$('#modifySearch').hide();
	});
	
	$('#closeSearch').click(function(event) {
		$('#searchDiv').hide();
		$('#closeSearch').hide();
		$('#modifySearch').show();
	});
	
});
      </script>
</head>
<body >
	<div class="contain-wrapp bodyDivCss">	
	<div  class="container" class="row" style ="position: relative;">
	<jsp:include page="../jsp/employeeSearch.jsp" />
	
	<c:if test="${sessionScope.empSalaryReport.size() gt 0}">
	<div class="col-sm-12" style ="width:100%;padding-left:0px;margin-top:0px;margin-left:0px;">
	<div class="col-sm-4" style ="padding-left:0px;margin-left:0px;"><h6 style="color: #0101DF;margin-bottom:0px;">Employee Salary Information</h6></div> 
	<div class="col-sm-4" style ="text-align:right;">
	<a id="modifySearch" href="javascript:void(0)" style="color: #0101DF;text-decoration: underline;margin-right:15px;"><b>Modify Search</b></a>
	<a id="closeSearch" href="javascript:void(0)" style="color: #0101DF;text-decoration: underline;margin-right:15px;"><b>Close Search</b></a>
	</div><div class="col-sm-2" ></div>
	<div class="col-sm-2" style ="text-align:right;">
	<a id="downloadLink" href="javascript:void(0)" style="color: #0101DF;text-decoration: underline;margin-right:15px;"><b>Download</b></a>
	<a id="printLink" href="javascript:void(0)" style="color: #0101DF;text-decoration: underline;"><b>Print</b></a></div>
	</div>
	<div id="empListDiv" style ="width:100%;overflow-x: auto;overflow-y: auto;min-height:10px;max-height:380px;">
		<table id="empRptTable" class="rptTblClass table table-striped table-bordered table-hover table-responsive">
		<thead>
			<tr>
			<th>Name</th>
			<th>Basic</th>
			<th>Grade Pay</th>
			<th>CA</th>
			<th>UFA</th>
			<th>FPA</th>
			<th>TA</th>
			<th>HRA Flag</th>
			<th>PF Flag</th>
			</tr></thead>
			<c:forEach var="employee" items="${sessionScope.empSalaryReport}">
			<tr>
			<td> ${employee.fullName} </td>
			<td> ${employee.basic}</td>
			<td> ${employee.gradePay}</td>
			<td> ${employee.ca}</td>
			<td> ${employee.ufa}</td>
			<td> ${employee.fpa} </td>
			<td> ${employee.ta}</td>
			<td> ${employee.hraFlag}</td>
			<td> ${employee.pfFlag}</td>
			</tr>
			</c:forEach>
		</table>
		</div>
	</c:if>
	</div>			
	</div>

	<iframe id="empRptFrame" name="empRptFrame" src="javascript:false" style="display:none;">
	</iframe>
	<jsp:include page="../jsp/public/postFooter.jsp" />
</body>
</html>