package com.payroll.report.vo;


import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.payroll.Utils;
import com.payroll.employee.salary.vo.SalaryVO;

public class EmpSalaryReportVO implements Serializable{
	
	private String firstName;
	private String lastName;
	private String middleName;
	private String designation;
	/*private String email;
	private String phone;
	private String joiningDate;
	private String lastWorkingDate;
	private String address;
	private String dob;
	private String contactNo;
	private String pan;
	private String adharNo;
	private String lastPromotionDate;*/
	private int departmentId;
	private int designationId;
	private int employeeId;
	private String department;
	private String fullName;
	private String headName;
	/*private String addressLine1;
	private String addressLine2;
	private String addressLine3;
	private String status;
	private String gender;
	
	private Date rowUpdatedDate;
	private SalaryVO salaryVo;*/
	private double basic;
	private double gradePay;
	private double ca;
	private double ufa;
	private double fpa;
	private double ta;
	private boolean hraFlag;
	private boolean pfFlag;
	
	public EmpSalaryReportVO(){
		
	}
	public String getFullName() {
		return fullName;
	}
	private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public EmpSalaryReportVO (int employeeId, String firstName, String lastName, String middleName, double basic, double gradePay,
			double ca, double ufa, double fpa, double ta, boolean hraFlag, boolean pfFlag){
		this.employeeId = employeeId;
		this.fullName = getName(firstName, middleName, lastName);
		this.firstName = firstName;
		this.middleName = middleName;
		this.lastName = lastName;
		this.basic = basic;
		this.gradePay = gradePay;
		this.ca = ca;
		this.ufa = ufa;
		this.fpa = fpa;
		this.ta = ta;
		this.hraFlag = hraFlag;
		this.pfFlag = pfFlag;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public String getDesignation() {
		return designation;
	}
	public int getDepartmentId() {
		return departmentId;
	}
	public int getDesignationId() {
		return designationId;
	}
	public int getEmployeeId() {
		return employeeId;
	}
	public String getDepartment() {
		return department;
	}
	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}
	
	private String getName(String fName, String mName, String lName){
		StringBuffer fullNameSB = new StringBuffer(fName);
		mName = Utils.safeTrim(mName);
		if(!mName.equals(""))
			fullNameSB.append(" ");
		fullNameSB.append(Utils.safeTrim(mName)).append(" ").append(Utils.safeTrim(lName));
		return fullNameSB.toString();
	}
	 
	/*public String getValue(String val) {
	   return (val == null) ? "" : val.trim();
	}
	public SalaryVO getSalaryVo() {
		return salaryVo;
	}
	public void setSalaryVo(SalaryVO salaryVo) {
		this.salaryVo = salaryVo;
	}*/
	public String getHeadName() {
		return headName;
	}
	public void setHeadName(String headName) {
		this.headName = headName;
	}
	
	public double getBasic() {
		return basic;
	}
	public double getGradePay() {
		return gradePay;
	}
	public double getCa() {
		return ca;
	}
	public double getUfa() {
		return ufa;
	}
	public double getFpa() {
		return fpa;
	}
	public double getTa() {
		return ta;
	}
	public boolean isHraFlag() {
		return hraFlag;
	}
	public boolean isPfFlag() {
		return pfFlag;
	}
}

