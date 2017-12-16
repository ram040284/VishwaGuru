<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

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

table {
	border-collapse: collapse;
	width: 100%;
}

th, td {
	text-align: left;
	padding: 8px;
}

tr:nth-child(even) {
	background-color: #f2f2f2
}

th {
	background-color: #4CAF50;
	color: white;
}
</style>
<script type="text/javascript">
$(document).ready(function() {
	
	<%--$("#collapse").hide();
	$("#expand").show(); --%>
	var departmentList = ${sessionScope.departments};
	$.each(departmentList, function( index, value ) {
		$('<option>').val(value.departmentId).text(value.departmantName).appendTo('#departmentId');
	});
	
	var deptId = "${search.departmentId}";
	$('#departmentId').val(deptId);
	var headId = "${search.headId}";
	$('#headId').val(headId);
	
	$('#closeBtn').click(function(event) {
		   $("#searchDiv").toggle();
		   $("#collapse").toggle();
		   $("#expand").toggle();
	});
	
	$('#closeSearch').hide();

	$('#printLink').click(function(event) {
		var newWin = window.frames["rptPrintFrame"];
		
		var frameDoc = newWin.document;
        if (newWin.contentWindow)
            frameDoc = newWin.contentWindow.document;

      //  frameDoc.open();
      frameDoc.getElementById("printReport").innerHTML = document.getElementById("empListDiv").innerHTML;
      frameDoc.getElementById("reportName").innerHTML = document.getElementById("reportName").innerHTML;
       // frameDoc.writeln($('#empListDiv').html());
      //  frameDoc.close();
        if (newWin.contentWindow) {
		newWin.contentWindow.focus();
		newWin.contentWindow.print();
        } else {
        	newWin.focus();
    		newWin.print();
        }
	});
	
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
	
	<c:if test="${sessionScope.recordsSize gt 0}">
	$('#searchDiv').hide();
	</c:if>	
	
});

function getHeads(){
	if($('#departmentId').val() == 0){
		$('#headId').val(0);
		$('#departmentId').focus();
		return false;
	}
	var deptId = $('#departmentId').val();
	getHeadsByDept(deptId, 0);
}

function getHeadsByDept(deptId, headId) {
	var inputJson = { "departmentId" : deptId};
	  $.ajax({
	    url: '../Payroll/loadHeads',
	    data: JSON.stringify(inputJson),
	    type: "POST",           
	    beforeSend: function(xhr) {
	        xhr.setRequestHeader("Accept", "application/json");
	        xhr.setRequestHeader("Content-Type", "application/json");
	    },
	    success: function(data){ 
	    	$('#headId').empty();
	    	$('<option>').val(0).text("-- Select Head --").appendTo('#headId');
	    	<%--$('#designationId').empty();
	    	$('<option>').val(0).text("-- Select Designation --").appendTo('#designationId'); --%>
	    	$(data).each(function(i, headInfo){
	    		$('<option>').val(headInfo.headId).text(headInfo.headName).appendTo('#headId');
	    	});
	    	<%--var headId = "${salary.headId}";--%>
	    	if(headId !=0) {
	  			$('#headId').val(headId);
	  		}
	    },
	    failure: function (){
	    	alert('Unable to load Heads');
	    }
	});
}
</script>

<%-- 
<div style="width:100%;"> 
<h6 style="color: #0101DF;margin-bottom:0px;"><a id="closeBtn" href="#" style="color: blue;"><label id="collapse" style="color: blue;" class="glyphicon-plus"></label> <label id="expand" class="glyphicon-minus" style="color: blue;"></label> Show / Hide Search</a></h6>
</div>  --%>

	<form:form id="formSearch" method = "POST" action = "" >
	<div id="searchDiv" class="container" style ="position: relative;">
	 <div class="panel panel-primary">
      <div class="panel-heading" style="margin:0px;padding:5px;background-color: #8B9DC3;"><b>${sessionScope.reportName}</b></div>
<div  class="panel-body" style="padding:10px;margin:0px;border: 1px solid #8B9DC3;">
	<div class="col-sm-3">
		<label>Department </label> 
		<select id="departmentId" class="form-control" name="departmentId" onchange="getHeads()">
		<option value="0">-- Select Department --</option></select>
	</div>
	<div class="col-sm-3">
		<label>Head </label> 
		<select id="headId" class="form-control" name="headId"><option value="0">-- Select Head --</option></select>
	</div>
	<div class="col-sm-3">
		<label>Name</label>
		<form:input type="text" id="fname" path="firstName" placeholder="Enter First Name" class="form-control"/>
		<%-- <label>Designation:</label>
		<select id="designationBox" class="form-control" name="designationId"><option value="0">-- Select Designation --</option></select> --%>
	</div>
	<div class="col-sm-3">
		
	</div><div class="col-sm-3">
		<br>
		<button type="submit" id="searchBtn"  class="btn" >Search</button>
		<button type="reset"  class="btn">Reset</button>	
	</div></div>
	</div></div>
	<form:hidden id="searchType" path="searchType"/>
	</form:form> 
<c:if test="${sessionScope.recordsSize gt 0}">
<div class="container" style ="position: relative;">
<div class="col-sm-12" style ="width:100%;padding-left:0px;margin-top:0px;margin-left:0px;">
	<div class="col-sm-4" style ="padding-left:0px;margin-left:0px;"><h6 style="color: #0101DF;margin-bottom:0px;" id="reportName">${sessionScope.reportName}</h6></div> 
	<div class="col-sm-4" style ="text-align:right;">
	<a id="modifySearch" href="javascript:void(0)" style="color: #0101DF;text-decoration: underline;margin-right:15px;"><b>Modify Search</b></a>
	<a id="closeSearch" href="javascript:void(0)" style="color: #0101DF;text-decoration: underline;margin-right:15px;"><b>Close Search</b></a>
	</div><div class="col-sm-2" ></div>
	<div class="col-sm-2" style ="text-align:right;">
	<a id="downloadLink" href="javascript:void(0)" style="color: #0101DF;text-decoration: underline;margin-right:15px;"><b>Download</b></a>
	<a id="printLink" href="javascript:void(0)" style="color: #0101DF;text-decoration: underline;"><b>Print</b></a></div>
</div></div>
</c:if>	

<iframe id="rptPrintFrame" name="rptPrintFrame" src="../Payroll/printTemplate" style="display:none;">
</iframe>	
