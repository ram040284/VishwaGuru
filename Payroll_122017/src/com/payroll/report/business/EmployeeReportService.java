package com.payroll.report.business;

import java.util.List;

import com.payroll.report.vo.EmployeeReportDAO;
import com.payroll.report.vo.EmployeeReportVO;

public class EmployeeReportService {
	
	public List<EmployeeReportVO> getEmployees(int deptId, int headId){
		/*if (deptId != 0 && headId != 0)
			return new EmployeeReportDAO().getEmployees(deptId, headId);
		else if (deptId != 0)
			return new EmployeeReportDAO().getEmployeesByDepartment(deptId);
		else if (headId != 0 )
			return new EmployeeReportDAO().getEmployeesByDesignation(headId);
		else return new EmployeeReportDAO().getEmployees();*/
		
		return new EmployeeReportDAO().getEmployees(deptId, headId, "");
			
	}
}
