package com.turing.shipping;


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
import com.turing.tax.Tax;


public class ShippingDao {
	private static final String GET_ALL_SHIPPING_REGION = "select shipping_region_id, shipping_region from shipping_region";
	private static final String GET_ALL_SHIPPINGS_IN_A_REGION = "select shipping_id, shipping_type,shipping_cost,shipping_region_id from shipping where shipping_region_id = ?" ;
	private InitialContext ctx;
	private Connection conn;
	private DataSource ds;
	private ResultSet rs, rs1;
	private List<ShippingRegion> list = new ArrayList<ShippingRegion>();
	private final Logger logger = Logger.getLogger(com.turing.shipping.ShippingDao.class);
	
	public ShippingDao() throws DAOException{
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
			throw new DAOException("Error in connecting to DB while processing Shipping details");
		}

	}
	
	public List<ShippingRegion> getAllShippingRegion() throws DAOException {
		try{
			logger.info("Starting the ShippingDao - getAllShippingRegion()");
			PreparedStatement cst = conn.prepareStatement(GET_ALL_SHIPPING_REGION);    
			rs = cst.executeQuery();
			ShippingRegion s = null;
			while(rs.next()) {
				s = new ShippingRegion(rs.getInt(1),rs.getString(2));
				list.add(s);
			}	
			conn.close();	
			return list;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in processing Customer","tax_id");
		}
	}
	
	public ShippingInfo getShippingRegionInfo(int shipping_region_id) throws DAOException {
		try{
			logger.info("Starting the ShippingDao - getShippingRegionInfo()" + shipping_region_id);
			CallableStatement cst = conn.prepareCall(GET_ALL_SHIPPINGS_IN_A_REGION);
			cst.setInt(1, shipping_region_id);
			rs = cst.executeQuery();
			ShippingInfo s = null;
			if(rs.next()) {
				s = new ShippingInfo(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getInt(4));	
			}	
			conn.close();	
			return s;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in processing ShippingInfo","shipping_region_id");
		}
	}
}
