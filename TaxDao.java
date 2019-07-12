package com.turing.tax;

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


public class TaxDao {
	private static final String GET_ALL_TAXES = "select tax_id, tax_type, tax_percentage from tax";
	private static final String GET_SINGLE_TAX = "select tax_id, tax_type, tax_percentage from tax where tax_id = ?" ;
	private InitialContext ctx;
	private Connection conn;
	private DataSource ds;
	private ResultSet rs, rs1;
	private List<Tax> list = new ArrayList<Tax>();
	private final Logger logger = Logger.getLogger(com.turing.tax.TaxDao.class);
	
	public TaxDao() throws DAOException{
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
			throw new DAOException("Error in connecting to DB while processing Tax details");
		}

	}
	
	public Tax getTaxById(int taxid) throws DAOException {
		try{
			logger.info("Starting the TaxDao - getTaxById()" + taxid);
			CallableStatement cst = conn.prepareCall(GET_SINGLE_TAX);	    
			cst.setInt(1,taxid);
			rs = cst.executeQuery();
			Tax t = null;
			if(rs.next()) {
				t = new Tax(taxid,rs.getString(2),rs.getString(3));
			}	
			conn.close();	
			return t;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in processing Customer","tax_id");
		}
	}
	
	public List<Tax> getAllTax() throws DAOException {
		try{
			logger.info("Starting the TaxDao - getAllTax()" );
			PreparedStatement cst = conn.prepareStatement(GET_ALL_TAXES);    
			rs = cst.executeQuery();
			Tax t = null;
			while(rs.next()) {
				t = new Tax(rs.getInt(1),rs.getString(2),rs.getString(3));
				list.add(t);
			}	
			conn.close();	
			return list;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in processing Tax","tax_id");
		}
	}
	
}
