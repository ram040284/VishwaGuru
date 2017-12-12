package com.payroll.report.vo;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;

import com.payroll.HibernateConnection;
import com.payroll.Utils;
import com.payroll.employee.salary.dataobjects.SalaryDAO;
import com.payroll.employee.salary.vo.SalaryVO;

public class EmployeeReportDAO {

	Session session = null;
	public List<EmployeeReportVO> getEmployees(int deptId, int headId, String name){
		List<EmployeeReportVO> employeeList = null;
		Session session = null;
		try{
			//String queryString = " from Employee";
			StringBuffer searchCriteria = new StringBuffer("");
			searchCriteria.append(" select new com.payroll.report.vo.EmployeeReportVO(e.employeeId, e.firstName, e.lastName, e.middleName,"
					+ " e.email, e.phone, e.pan, e.adharNo, e.dob,"
					+ "(select dept.departmantName from Department dept where dept.departmentId = (select eDept.department.departmentId from EmpDepartment eDept where eDept.employee.employeeId = e.employeeId)),"
					+ "(select h.headName from HeadInfo h where h.headId = (select eMas.headInfo.headId from EmpHeadInfo eMas where eMas.employee.employeeId = e.employeeId)),"
					+ "(select desg.designationName from Designation desg where desg.designationId = "
					+ "(select eDesg.designation.designationId from EmpDesignation eDesg where eDesg.employee.employeeId = e.employeeId)), "
					+ "e.addressLine1, e.addressLine2, e.addressLine3, e.gender, e.joiningDate) from Employee e where e.status= ?");		
			
			if(deptId != 0)
				searchCriteria.append(" and e.employeeId = (select eDept.employee.employeeId from EmpDepartment eDept where e.employeeId = eDept.employee.employeeId and eDept.department.departmentId = ?)");
			if(headId != 0){
				searchCriteria.append(" and e.employeeId = (select eMas.employee.employeeId from EmpHeadInfo eMas where e.employeeId = eMas.employee.employeeId and eMas.headInfo.headId = ?)");
			}
			if(!Utils.isEmpty(name))
				searchCriteria.append(" and (e.firstName like :fname or e.middleName like :mname or e.lastName like :lname)");
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(searchCriteria.toString());
			int i=0;
			query.setParameter(i++, "A");
			if(deptId != 0)
				query.setParameter(i++, deptId);
			if(headId != 0)
				query.setParameter(i++, headId);
			if(!Utils.isEmpty(name)){
				query.setParameter("fname", "%"+name+"%");
				query.setParameter("mname", "%"+name+"%");
				query.setParameter("lname", "%"+name+"%");
			}
			employeeList = query.list();
			
			for (EmployeeReportVO employeeVo: employeeList) {
				SalaryDAO dao = new SalaryDAO();
				employeeVo.setSalaryVo(getEmpSalary(employeeVo.getEmployeeId()));
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return employeeList;
	}
	
	public SalaryVO getEmpSalary(int empId){
		SalaryVO salVO = null;
		Session session = null;
		try{
			String queryString = " select new com.payroll.employee.salary.vo.SalaryVO(s.empId, '' , '', "
					+ "s.year, s.basic, s.gradePay, s.scalePay, s.scaleInc) from Salary s where s.empId = ?";		
			
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, empId);
			salVO = (SalaryVO)(!(query.list().isEmpty()) ? query.list().get(0) : null);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return salVO;
	}
	
	public List<EmpSalaryReportVO> getEmpSalaryReport(int deptId, int headId){
		SalaryVO salVO = null;
		Session session = null;
		List<EmpSalaryReportVO> empSalList = new ArrayList<EmpSalaryReportVO>();
		try{
			String queryString = "SELECT EMP_FNAME, EMP_MNAME, EMP_LNAME, BASIC, GRD_PAY, CA, UFALW, FPALW, TALW, HRA_FLAG, PF_FLAG, SUM(empLeave.NO_OF_LEAVES) as LWP "  
				+ "FROM PAYROLL_DEV.EMP_MASTER empMaster "
				+ "LEFT OUTER JOIN  PAYROLL_DEV.EMP_SAL_MASTER empSal "  
				+ "ON empMaster.EMP_ID =empSal.EMP_ID "
				+ "LEFT OUTER JOIN  PAYROLL_DEV.EMP_ALLOWANCES empAllowance " 
				+ "ON empMaster.EMP_ID =empAllowance.EMP_ID "
				+ "LEFT OUTER JOIN  PAYROLL_DEV.EMP_LEAVE_MASTER empLeave " 
				+ "ON empMaster.EMP_ID =empLeave.EMP_ID "
				+ "GROUP BY empMaster.EMP_ID;";		
			
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createSQLQuery(queryString);
			//query.setParameter(0, deptId);
			List<Object[]> rows = query.list();
			System.out.println("rows size:" + rows.size());
			for(Object[] row : rows){
				EmpSalaryReportVO empSalVo = new EmpSalaryReportVO(0, (String)row[0], (String)row[1], (String)row[2], 
						(double)row[3], row[4]==null? 0.0:(double)row[4], row[5]==null? 0.0:(double)row[5], row[6]==null? 0.0:(double)row[6], 
								row[7]==null? 0.0:(double)row[7], row[8]==null? 0.0:(double)row[8], 
										row[9]==null? false:(boolean)row[9], row[10]==null? false:(boolean)row[10]);
				empSalList.add(empSalVo);
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return empSalList;
	}
}
