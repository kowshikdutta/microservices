package com.turing.department;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import com.turing.exception.DAOException;

public class DepartmentDao {
	private static final String GET_ALL_DEPARTMENTS = "CALL catalog_get_departments()";
	private static final String GET_SINGLE_DEPARTMENT = "CALL catalog_get_department_details(?)";
	private InitialContext ctx;
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private ArrayList<Department> list;
	private final Logger logger = Logger.getLogger(com.turing.department.DepartmentDao.class);

	public DepartmentDao() throws DAOException{
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/turing");
			conn = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Error Message:", e);
			throw new DAOException("Error in connecting to DB while getting department details");
		}
		
	}

	public List<Department> getDepartments() throws DAOException {
		try{
			logger.info("Starting the DepartmentDao - getDepartments()");
			PreparedStatement pstmt = conn.prepareStatement(GET_ALL_DEPARTMENTS);
			rs = pstmt.executeQuery();
			list = new ArrayList<Department>();
			while (rs.next()) {
				Department c = new Department(rs.getInt(1),rs.getString(2),rs.getString(3));
				list.add(c);
			}	    
			conn.close();	    
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Don'exist department");
		};
		return list;
	}

	public Department getDepartment(int deptid) throws DAOException{
		try{
			logger.info("Starting the DepartmentDao - getDepartment()" + deptid);
			CallableStatement cst = conn.prepareCall(GET_SINGLE_DEPARTMENT);	    
			cst.setInt(1,deptid);
			rs = cst.executeQuery();		
				while (rs.next()) {
					Department d = new Department(deptid,rs.getString(1),rs.getString(2));
					conn.close();
					return d;
				}				
				conn.close();	
				return null;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Don'exist department",String.valueOf(deptid));
		}

	}

}

