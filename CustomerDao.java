package com.turing.customer;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.turing.category.Category;
import com.turing.exception.DAOException;
import com.turing.security.Token;

public class CustomerDao {
	private static final String CREATE_CUSTOMER = "call customer_add(?,?,?)";
	private static final String GET_SINGLE_CUSTOMER = "call customer_get_customer(?)";
	private static final String CUSTOMER_LOGIN_INFO = "call customer_get_login_info(?)";
	private static final String CUSTOMER_ACCOUNT_UPDATE = "call customer_update_account(?,?,?,?,?,?,?)";
	private static final String CUSTOMER_ADDRESS_UPDATE = "call customer_update_address(?,?,?,?,?,?,?,?)";
	private static final String CUSTOMER_CARD_UPDATE = "call customer_update_credit_card(?,?)";
	private InitialContext ctx;
	private Connection conn;
	private DataSource ds;
	private ResultSet rs, rs1;
	private ArrayList<Customer> list;
	private final Logger logger = Logger.getLogger(com.turing.customer.CustomerDao.class);
	private Map<String,List<Object>> customer = new HashMap<String,List<Object>>();
	private Token token;
	private List<Object> result;

	public CustomerDao() throws DAOException{
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
			throw new DAOException("Error in connecting to DB while processing Customer details");
		}

	}

	public Customer createCustomer(String name,String email,String password) throws DAOException{
		Customer c = null;
		try{
			logger.info("Starting the CustomerDao - createCustomer()");
			CallableStatement docustomerexist = conn.prepareCall(CUSTOMER_LOGIN_INFO);
			docustomerexist.setString(1, email);
			ResultSet rstemp = docustomerexist.executeQuery();
			if(rstemp.next()){
				logger.info("Customer exist" + email);
				conn.close();
				throw new DAOException("Customer already exists","CUS_04");				
			}
			CallableStatement pstmt = conn.prepareCall(CREATE_CUSTOMER);
			pstmt.setString(1, name);
			pstmt.setString(2, email);
			pstmt.setString(3, password);
			rs = pstmt.executeQuery();
			if(rs.next()){
				c = getCustomer(rs.getInt(1));
			}
			return c;		    
		} catch (DAOException d){
			throw new DAOException(d.getMessage(),d.getField());
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException(e.getMessage());
		}
	}

	public Customer loginCustomer(String email,String password) throws DAOException{
		Customer c = null;
		try{
			logger.info("Starting the CustomerDao - loginCustomer()" + email);
			CallableStatement docustomerexist = conn.prepareCall(CUSTOMER_LOGIN_INFO);
			docustomerexist.setString(1, email);
			ResultSet rstemp = docustomerexist.executeQuery();
			if(rstemp.next()){
				if(rstemp.getString(2).matches(password)){
					c = getCustomer(rstemp.getInt(1));
					logger.info(c.getEmail());
				}else {
					conn.close();
					throw new DAOException("Invalid password","password");				
				}}		
			return c;		    
		} catch (DAOException d){
			throw new DAOException(d.getMessage(),d.getField());
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException(e.getMessage());
		}
	}	


	public Customer getCustomer(int customerid) throws DAOException {
		try{
			logger.info("Starting the CustomerDao - getCustomer()" + customerid);
			CallableStatement cst = conn.prepareCall(GET_SINGLE_CUSTOMER);	    
			cst.setInt(1,customerid);
			rs = cst.executeQuery();
			Customer c = null;
			if(rs.next()) {
				c = new Customer(customerid,rs.getString(2),rs.getString(3),rs.getString(6),
						rs.getString(7),rs.getString(8),rs.getString(9),
						rs.getString(10),rs.getInt(12),rs.getString(5),
						rs.getString(13),rs.getString(14),rs.getString(15));
			}	
			conn.close();	
			return c;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in processing Customer",String.valueOf(customerid));
		}
	}

	public Customer updateCustomer(String email, String name, String day_phone, String eve_phone, String mobile_phone) throws DAOException {
		Customer c = null;
		try{
			logger.info("Starting the CustomerDao - updateCustomer()" + email);
			CallableStatement cst = conn.prepareCall(CUSTOMER_LOGIN_INFO);
			CallableStatement cupdate = conn.prepareCall(CUSTOMER_ACCOUNT_UPDATE);
			cst.setString(1,email);
			rs = cst.executeQuery();
			if(rs.next())
			{
				int customer_id = rs.getInt(1);
				cupdate.setInt(1,customer_id);
				cupdate.setString(2,name);
				cupdate.setString(3,email);
				cupdate.setString(4,rs.getString(2));
				cupdate.setString(5,day_phone);
				cupdate.setString(6,eve_phone);
				cupdate.setString(7,mobile_phone);
				rs = cupdate.executeQuery();
				c = getCustomer(customer_id);
			}	
			conn.close();	
			return c;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in processing Customer","email");
		}
	}
	
	public Customer updateCustomerAddress(int customer_id, String address_1, String address_2, String city, String region, String postalcode, int shipping_region_id) throws DAOException {
		Customer c = null;
		try{
			logger.info("Starting the CustomerDao - updateCustomerAddress()" + customer_id);

			CallableStatement cupdate = conn.prepareCall(CUSTOMER_ADDRESS_UPDATE);
				cupdate.setInt(1,customer_id);
				cupdate.setString(2,address_1);
				cupdate.setString(3,address_2);
				cupdate.setString(4,city);
				cupdate.setString(5,region);
				cupdate.setString(6,postalcode);
				cupdate.setString(7,null);
				cupdate.setInt(8,shipping_region_id);
				rs = cupdate.executeQuery();
				c = getCustomer(customer_id);					
			return c;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in processing Customer","customerid");
		}
	}
	
	public Customer updateCustomerCard(int customer_id, String card) throws DAOException {
		Customer c = null;
		try{
			logger.info("Starting the CustomerDao - updateCustomerAddress()" + customer_id);
			CallableStatement cupdate = conn.prepareCall(CUSTOMER_CARD_UPDATE);
				cupdate.setInt(1,customer_id);
				cupdate.setString(2,card);
				rs = cupdate.executeQuery();
				c = getCustomer(customer_id);					
			return c;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in processing Customer","customerid");
		}
	}
}

