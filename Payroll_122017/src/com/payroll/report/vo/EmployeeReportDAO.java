package com.payroll.report.vo;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.payroll.HibernateConnection;
import com.payroll.Utils;
import com.payroll.report.vo.EmployeeReportVO;
import com.payroll.employee.salary.dataobjects.SalaryDAO;
import com.payroll.employee.salary.vo.SalaryVO;
import com.payroll.employee.vo.EmployeeVO;

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
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return employeeList;
	}
	
	public List<EmployeeReportVO> getEmployees(int deptId, int desgId){
		List<EmployeeReportVO> employeeList = null;
		Session session = null;
		
		try{
			String queryString = " select new com.payroll.report.vo.EmployeeReportVO(e.employeeId, e.firstName, e.lastName, e.middleName,"
					+ " e.email, e.phone, e.pan, e.adharNo, e.dob,(select dept.departmantName from Department dept "
					+ "where dept.departmentId = (select eDept.departmentId from EmpDepartment eDept where eDept.empId = e.employeeId)), "
					+ "(select desg.designationName from Designation desg where desg.designationId = "
					+ "(select eDesg.designationId from EmpDesignation eDesg where eDesg.empId = e.employeeId and eDesg.lastWokingDate is null)), "
					+ "e.addressLine1, e.addressLine2, e.addressLine3, e.gender) from Employee e where e.status= ? and "
					+ "e.employeeId = (select eDept.empId from EmpDepartment eDept where eDept.empId=e.employeeId and eDept.departmentId = ? ) and "
					+ "e.employeeId = (select eDesg.empId from EmpDesignation eDesg where eDesg.empId=e.employeeId and "
					+ " eDesg.designationId = ? and eDesg.lastWokingDate is null)";
			
			session = HibernateConnection.getSessionFactory().openSession();
			Query query = session.createQuery(queryString);
			query.setParameter(0, "A");
			query.setParameter(1, deptId);
			query.setParameter(2, desgId);
			
			employeeList = query.list();
			
			for (EmployeeReportVO employeeVo: employeeList) {
				SalaryDAO dao = new SalaryDAO();
				employeeVo.setSalaryVo(dao.getEmpSalary(employeeVo.getEmployeeId()));
			}
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			HibernateConnection.closeSession(session);
		}
		return employeeList;
	}
}
