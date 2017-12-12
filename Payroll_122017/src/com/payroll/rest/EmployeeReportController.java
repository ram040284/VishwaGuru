package com.payroll.rest;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.payroll.department.business.DepartmentService;
import com.payroll.department.dataobjects.Department;
import com.payroll.employee.SearchCriteria;
import com.payroll.employee.salary.vo.SalaryVO;
import com.payroll.report.business.EmployeeReportService;
import com.payroll.report.vo.EmpSalaryReportVO;
import com.payroll.report.vo.EmployeeReportVO;

@Controller
public class EmployeeReportController {
	@RequestMapping(value="/employeeSearch",method=RequestMethod.GET)
	public ModelAndView getEmployeesSearch(HttpServletRequest request, ModelMap modelMap){
	   ObjectMapper mapper = new ObjectMapper();
	   List<Department> deptList = new DepartmentService().getDepartments();
		   
	   String depJSON = "";
		try {
			depJSON = mapper.writeValueAsString(deptList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	   request.getSession().setAttribute("employees", new ArrayList());
	   request.getSession().setAttribute("departments", depJSON);
	   
	   SearchCriteria employee = new SearchCriteria();
	   employee.setSearchType("employeeReport");
	   ModelAndView model = new ModelAndView("employeeReport", "command", employee);
	   model.addObject("search", employee);
	   return model;
    }
	  
	@RequestMapping(value="/empSalarySearch",method=RequestMethod.GET)
	public ModelAndView getEmployeesSalarySearch(HttpServletRequest request, ModelMap modelMap){
	   ObjectMapper mapper = new ObjectMapper();
	   List<Department> deptList = new DepartmentService().getDepartments();
		   
	   String depJSON = "";
		try {
			depJSON = mapper.writeValueAsString(deptList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	   request.getSession().setAttribute("empSalaryReport", new ArrayList());
	   request.getSession().setAttribute("departments", depJSON);
	   
	   SearchCriteria employee = new SearchCriteria();
	   employee.setSearchType("empSalaryReport");
	   ModelAndView model = new ModelAndView("employeeSalaryReport", "command", employee);
	   model.addObject("search", employee);
	   return model;
    }
	
   @RequestMapping(value="/employeeReport", method=RequestMethod.POST)
   public ModelAndView getEmployeesReport(HttpServletRequest request, SearchCriteria search){
			
	   List<EmployeeReportVO> employeesList = new EmployeeReportService().getEmployees(search.getDepartmentId(), search.getHeadId()); 
	   
	   List<EmployeeReportVO> employees = new ArrayList<EmployeeReportVO>();
	   String name = search.getFirstName()!= null? search.getFirstName().trim(): "";
	   if(!name.equals("") && employeesList != null && employeesList.size() != 0) {
		   for (EmployeeReportVO empVO : employeesList) {
			   if (empVO.getFullName().toUpperCase().contains(name.toUpperCase())) {
				   employees.add(empVO);
			   }
		   }
	   } else {
		   employees = employeesList;
	   }

	   request.getSession().setAttribute("employees", employees);
	   ModelAndView model = new ModelAndView("employeeReport", "command", search);
	   model.addObject("search", search);
	   return model;
   }
   
   @RequestMapping(value="/empSalaryReport", method=RequestMethod.POST)
   public ModelAndView getEmployeesSalaryReport(HttpServletRequest request, SearchCriteria search){
			
	   List<EmpSalaryReportVO> employeesList = new EmployeeReportService().getEmpSalaryReport(search.getDepartmentId(), search.getHeadId()); 
	   
	   List<EmpSalaryReportVO> employees = new ArrayList<EmpSalaryReportVO>();
	   String name = search.getFirstName()!= null? search.getFirstName().trim(): "";
	   if(!name.equals("") && employeesList != null && employeesList.size() != 0) {
		   for (EmpSalaryReportVO empVO : employeesList) {
			   if (empVO.getFullName().toUpperCase().contains(name.toUpperCase())) {
				   employees.add(empVO);
			   }
		   }
	   } else {
		   employees = employeesList;
	   }
	   
	   System.out.println("Salary Report Size:" + employees.size());

	   request.getSession().setAttribute("empSalaryReport", employees);
	   ModelAndView model = new ModelAndView("employeeSalaryReport", "command", search);
	   model.addObject("search", search);
	   return model;
   }
   
   @RequestMapping(value = "/employeeRptDownload", method = RequestMethod.GET, produces = "text/csv")
   public @ResponseBody void downloadEmployeeReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
	   DateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
	   String csvFileName = "EmployeeReport_"+format.format(new Date())+".csv";
	   
	   List<EmployeeReportVO> employeesList = (List<EmployeeReportVO>)request.getSession().getAttribute("employees");
	   StringBuilder fileContent =new StringBuilder("Name, Department, Head, Designation, Gender, Date of Birth, Joining Date, Phone, Email, Address, PAN, Aadhar, Year, Basic Pay, Grade Pay, Scale Pay, Scale Increment,").append("\n");
	   for (EmployeeReportVO employeeVO : employeesList) {
		   fileContent.append(employeeVO.getFullName()).append(", ");
		   fileContent.append(employeeVO.getDepartment()).append(", ");
		   fileContent.append(employeeVO.getHeadName()).append(", ");
		   fileContent.append(employeeVO.getDesignation()).append(", ");
		   fileContent.append(employeeVO.getGender()).append(", ");
		   fileContent.append(employeeVO.getDob()).append(", ");
		   fileContent.append(employeeVO.getJoiningDate()).append(", ");
		   fileContent.append(employeeVO.getPhone()).append(", ");
		   fileContent.append(employeeVO.getEmail()).append(", ");
		   fileContent.append(employeeVO.getAddress()).append(", ");
		   fileContent.append(employeeVO.getPan()).append(", ");
		   fileContent.append(employeeVO.getAdharNo()).append(", ");
		   SalaryVO salVo = employeeVO.getSalaryVo();
		   if (salVo != null) {
			   fileContent.append(salVo.getYear()).append(", ");
			   fileContent.append(salVo.getBasic()).append(", ");
			   fileContent.append(salVo.getGradePay()).append(", ");
			   fileContent.append(salVo.getScalePay()).append(", ");
			   fileContent.append(salVo.getScaleInc()).append(", ");
		   }
		   fileContent.append("\n");
		   
	   }
	   
       response.setContentType("text/csv");
       response.setHeader("Content-Disposition", "attachment; filename=" + csvFileName);

       ServletOutputStream os = response.getOutputStream();
	   os.write(fileContent.toString().getBytes());
	   os.flush();
	   os.close();
   }

   @RequestMapping(value = "/empSalaryRptDownload", method = RequestMethod.GET, produces = "text/csv")
   public @ResponseBody void downloadEmpSalReport(HttpServletRequest request, HttpServletResponse response) throws IOException {
	   DateFormat format = new SimpleDateFormat("yyyyMMdd_HHmmss");
	   String csvFileName = "EmployeeSalaryReport_"+format.format(new Date())+".csv";
	   
	   List<EmpSalaryReportVO> employeesList = (List<EmpSalaryReportVO>)request.getSession().getAttribute("empSalaryReport");
	   StringBuilder fileContent =new StringBuilder("Name, Basic, Grade Pay, CA, UFA, FPA, TA, HRA, PF,").append("\n");
	   for (EmpSalaryReportVO employeeVO : employeesList) {
		   fileContent.append(employeeVO.getFullName()).append(", ");
		   fileContent.append(employeeVO.getBasic()).append(", ");
		   fileContent.append(employeeVO.getGradePay()).append(", ");
		   fileContent.append(employeeVO.getCa()).append(", ");
		   fileContent.append(employeeVO.getUfa()).append(", ");
		   fileContent.append(employeeVO.getFpa()).append(", ");
		   fileContent.append(employeeVO.getTa()).append(", ");
		   fileContent.append(employeeVO.isHraFlag()).append(", ");
		   fileContent.append(employeeVO.isPfFlag()).append(", ");
		   fileContent.append("\n");
		   
	   }
	   
       response.setContentType("text/csv");
       response.setHeader("Content-Disposition", "attachment; filename=" + csvFileName);

       ServletOutputStream os = response.getOutputStream();
	   os.write(fileContent.toString().getBytes());
	   os.flush();
	   os.close();
   }
}	
