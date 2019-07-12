package com.turing.attribute;

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


public class AttributeDao {

	private static final String GET_ALL_ATTRIBUTES = "call catalog_get_attributes();";
	private static final String GET_SINGLE_ATTRIBUTE = "call catalog_get_attribute_details(?)";
	private static final String GET_ALL_ATTRIBUTE_VALUES_IN_AN_ATTRIBUTE = "call catalog_get_attribute_values(?)";
	private static final String GET_ALL_ATTRIBUTES_OF_A_PRODUCT = "select a.name, b.attribute_value_id,b.value from attribute a, attribute_value b, product_attribute c where a.attribute_id = b.attribute_id and b.attribute_value_id = c.attribute_value_id and c.product_id = ?";
	private InitialContext ctx;
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private ArrayList<Attribute> list;
	private final Logger logger = Logger.getLogger(com.turing.attribute.AttributeDao.class);


	public AttributeDao() throws DAOException{
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
			throw new DAOException("Error in connecting to DB while getting Attribute details");
		}

	}

	public List<Attribute> getAttributes() throws DAOException {
		try{
			logger.info("Starting the AttributeDao - getAttributes()");

			PreparedStatement pstmt = conn.prepareStatement(GET_ALL_ATTRIBUTES);
			rs = pstmt.executeQuery();

			list = new ArrayList<Attribute>();

			while (rs.next()) {
				Attribute a = new Attribute(rs.getInt(1),rs.getString(2));
				list.add(a);
			}	    
			conn.close();
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Attribute information from DB");

		}
		return list;
	}

	public Attribute getAttribute(int attributeid) throws DAOException{
		try{
			logger.info("Starting the AttributeDao - getAttribute()" + attributeid);
			CallableStatement cst = conn.prepareCall(GET_SINGLE_ATTRIBUTE);	    
			cst.setInt(1,attributeid);
			rs = cst.executeQuery();
			Attribute a = null;
			

			while (rs.next()) {
				a = new Attribute(attributeid,rs.getString(2));
			}	

			conn.close();	
			return a;


		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Attribute details from DataBase");

		}

	}
	
	public List<AttributeValues> getAttributeValues(int attributeid) throws DAOException{
		try{
			logger.info("Starting the AttributeDao - getAttributeValues()" + attributeid);
			CallableStatement cst = conn.prepareCall(GET_ALL_ATTRIBUTE_VALUES_IN_AN_ATTRIBUTE);	    
			cst.setInt(1,attributeid);
			rs = cst.executeQuery();
			List<AttributeValues> avalues = new ArrayList<AttributeValues>();
			

			while (rs.next()) {
				avalues.add(new AttributeValues(attributeid,rs.getString(2)));
			}	

			conn.close();	
			return avalues;


		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Attribute details from DataBase");

		}

	}
	
	public List<AttributeInProduct> getAttributeInProduct(int productid) throws DAOException{
		try{
			logger.info("Starting the AttributeDao - getAttributeInProduct()" + productid);
			CallableStatement cst = conn.prepareCall(GET_ALL_ATTRIBUTES_OF_A_PRODUCT);	    
			cst.setInt(1,productid);
			rs = cst.executeQuery();
			List<AttributeInProduct> avalues = new ArrayList<AttributeInProduct>() ;
			

			while (rs.next()) {
				avalues.add(new AttributeInProduct(rs.getString(1),rs.getInt(2),rs.getString(3)));
			}	

			conn.close();	
			return avalues;


		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Attribute details from DataBase");

		}

	}
}
