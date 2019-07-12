package com.turing.order;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import org.apache.log4j.Logger;
import com.turing.exception.DAOException;



public class OrderDao {
	private static final String SHOPPING_CART_CREATE_ORDER = "call shopping_cart_create_order(?,?,?,?)";
	private static final String GET_ORDER_DETAILS = "call orders_get_order_details(?)";
	private static final String ORDERS_BY_CUSTOMER = "call orders_get_by_customer_id(?)";
	private static final String ORDER_SHORT_DETAILS = "call orders_get_order_short_details(?)";
	
	private InitialContext ctx;
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private final Logger logger = Logger.getLogger(com.turing.order.OrderDao.class);
	
	public OrderDao() throws DAOException{
		try {
			ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:/comp/env/jdbc/turing");
			conn = ds.getConnection();
		} catch (NamingException e) {
			// TODO Auto-generated catch block
			logger.error("Error Message:", e);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Order details from DataBase");
		}
	}

	public int createOrder(String cartid,int customerid, int shippingid, int taxid) throws DAOException{
		try{
			logger.info("Starting the OrderDao - createOrder()");
			CallableStatement cst = conn.prepareCall(SHOPPING_CART_CREATE_ORDER);	    
			cst.setString(1,cartid);
			cst.setInt(2,customerid);
			cst.setInt(3,shippingid);
			cst.setInt(4,taxid);
			rs = cst.executeQuery();
			int orderid = 0;
			if (rs.next()) {
				orderid = rs.getInt(1);
			}	
			conn.close();	
			return orderid;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in creating Order");
		}
	}
	
	public List<OrderItems> getOrder(int orderid) throws DAOException{
		try{
			logger.info("Starting the OrderDao - getOrder()" + orderid);
			CallableStatement cst = conn.prepareCall(GET_ORDER_DETAILS);	    
			cst.setInt(1,orderid);
			rs = cst.executeQuery();
			List<OrderItems> itemlist = new ArrayList<OrderItems>();
			OrderItems item = null;
			while (rs.next()) {
				item = new OrderItems(rs.getInt(2),rs.getString(3),rs.getString(4), rs.getInt(5),rs.getString(6),rs.getString(7));
				itemlist.add(item);
			}	
			conn.close();	
			return itemlist;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Order");
		}
	}
	
	public List<OrderInCustomer> getCustomersOrder(int customerid) throws DAOException{
		try{
			logger.info("Starting the OrderDao - getCustomerOrder()" + customerid);
			CallableStatement cst = conn.prepareCall(ORDERS_BY_CUSTOMER);	    
			cst.setInt(1,customerid);
			rs = cst.executeQuery();
			List<OrderInCustomer> itemlist = new ArrayList<OrderInCustomer>();
			OrderInCustomer item = null;
			while (rs.next()) {
				item = new OrderInCustomer(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4),rs.getString(6));
				itemlist.add(item);
			}	
			conn.close();	
			return itemlist;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Order in Customer");
		}
	}
	
	public OrderShortDetails getOrderShortDetails(int orderid) throws DAOException{
		try{
			logger.info("Starting the OrderDao - getCustomerOrder()" + orderid);
			CallableStatement cst = conn.prepareCall(ORDERS_BY_CUSTOMER);	    
			cst.setInt(1,orderid);
			rs = cst.executeQuery();
//			List<OrderShortDetails> itemlist = new ArrayList<OrderShortDetails>();
			OrderShortDetails item = null;
			while (rs.next()) {
				item = new OrderShortDetails(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4),rs.getString(5),rs.getString(6));
//				itemlist.add(item);
			}	
			conn.close();	
			return item;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Order Short Details");
		}
	}

}
