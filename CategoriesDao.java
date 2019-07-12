package com.turing.category;

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



public class CategoriesDao {
	private static final String GET_ALL_CATEGORIES = "select category_id,name,description,department_id from category";
	private static final String GET_SINGLE_CATEGORY = "select category_id,name,description,department_id from category where category_id = ?";
	private static final String GET_CATEGORY_INPRODUCT = "select a.category_id, a.name, a.description, a.department_id from category a, product_category b where a.category_id = b.category_id and b.product_id = ?";
	private static final String GET_CATEGORY_INDEPARTMENT = "select category_id,name,description,department_id from category where department_id = ?";
	private InitialContext ctx;
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private ArrayList<Category> list;
	private final Logger logger = Logger.getLogger(com.turing.category.CategoriesDao.class);

	public CategoriesDao() throws DAOException{
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
			throw new DAOException("Error in connecting to DB while getting Category details");
		}

	}

	public List<Category> getCategories() throws DAOException {
		try{
			logger.info("Starting the DepartmentDao - getDepartments()");

			PreparedStatement pstmt = conn.prepareStatement(GET_ALL_CATEGORIES);
			rs = pstmt.executeQuery();

			list = new ArrayList<Category>();

			while (rs.next()) {
				Category c = new Category(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
				list.add(c);

			}	    
			conn.close();	    
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Category information from DB");

		};

		return list;
	}

	public Category getCategory(int categoryid) throws DAOException{
		try{
			logger.info("Starting the CategoryDao - getCategory()" + categoryid);
			CallableStatement cst = conn.prepareCall(GET_SINGLE_CATEGORY);	    
			cst.setInt(1,categoryid);
			rs = cst.executeQuery();
			Category c = null;

			while (rs.next()) {
				c = new Category(categoryid,rs.getString(2),rs.getString(3),rs.getInt(4));
			}	

			conn.close();	
			return c;


		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Category details from DataBase");

		}

	}

	public Category getCategoryInProduct(int productid) throws DAOException{
		try{
			logger.info("Starting the CategoryDao - getCategoryInProduct()" + productid);
			CallableStatement cst = conn.prepareCall(GET_CATEGORY_INPRODUCT);	    
			cst.setInt(1,productid);
			rs = cst.executeQuery();

			while (rs.next()) {
				Category c = new Category(productid,rs.getString(2),rs.getString(3),rs.getInt(4));
				conn.close();
				return c;

			}	

			conn.close();	
			return null;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Category details from DataBase");

		}

	}

	public List<Category> getCategoryInDepartment(int departmentid) throws DAOException{
		try{
			logger.info("Starting the CategoryDao - getCategoryInDepartment()" + departmentid);
			CallableStatement cst = conn.prepareCall(GET_CATEGORY_INDEPARTMENT);	    
			cst.setInt(1,departmentid);
			rs = cst.executeQuery();
			List<Category> clist = new ArrayList<Category>();
			Category c;
			while (rs.next()) {
				c = new Category(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));
				clist.add(c);
			}	

			conn.close();	
			return clist;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Category details from DataBase");

		}

	}
}
