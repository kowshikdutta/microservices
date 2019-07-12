package com.turing.shoppingcart;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.turing.exception.DAOException;
import com.turing.order.OrderItems;

public class ShoppingCartDao {
	private static final String ADD_PRODUCT_TO_SHOPPING_CART = "insert INTO shopping_cart (`cart_id`, `product_id`, `attributes`, `quantity`, `added_on`) VALUES (?, ?, ?, ?, now())";
	private static final String GET_LIST_OF_PRODUCTS_IN_A_SHOPPING_CART = "select sc.item_id, sc.cart_id, p.name, sc.attributes,p.product_id, p.image,p.price,p.discounted_price, sc.quantity, COALESCE(NULLIF(p.discounted_price, 0), p.price) * sc.quantity AS subtotal from shopping_cart sc, product p where sc.product_id = p.product_id and sc.cart_id = ?" ;
	private static final String UPDATE_CART_ITEM_QUANTITY ="call shopping_cart_update(?,?)";
	private static final String GET_CART_ITEM_QUANTITY ="select item_id, cart_id,product_id,attributes,quantity FROM turing.shopping_cart where item_id  = ?";
	private static final String EMPTY_SHOPPING_CART = "call shopping_cart_empty(?)";
	private static final String REMOVE_ITEM_FROM_SHOPPING_CART = "call shopping_cart_remove_product(?)";
	
	private InitialContext ctx;
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private final Logger logger = Logger.getLogger(com.turing.order.OrderDao.class);
	
	public ShoppingCartDao() throws DAOException{
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
			throw new DAOException("Error in processsing Shopping Cart");
		}
	}
	
	public Boolean emptyShoppingCart(String cartid) throws DAOException{
		try{
			logger.info("Starting the ShoppingCartDao - emptyShoppingCart()" + cartid);
			CallableStatement cst = conn.prepareCall(EMPTY_SHOPPING_CART);	    
			cst.setString(1,cartid);
			int rowsdeleted = cst.executeUpdate();
			
			if (rowsdeleted > 0) {
				return true;
			}	
			conn.close();	
			return false;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in deleting Shopping Cart items");
		}
	}
	
	public int insertProductInShoppingCart(String cartid,int product_id, String attributes, int quantity) throws DAOException{
		try{
			logger.info("Starting the ShoppingCartDao - insertProductInShoppingCart()" + cartid);
			CallableStatement cst = conn.prepareCall(ADD_PRODUCT_TO_SHOPPING_CART);	   
			PreparedStatement pst = conn.prepareStatement("select last_insert_id()");
			cst.setString(1,cartid);
			cst.setInt(2,product_id);
			cst.setString(3,attributes);
			cst.setInt(4,quantity);
			int returnvalue  = cst.executeUpdate();
			int item = 0;
			if (returnvalue > 0) {
			   rs = pst.executeQuery();
			   if(rs.next()){
				   item = rs.getInt(1);
			   }
			}	
			conn.close();	
			return item;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Shopping Cart items");
		}
	}
	
	public List<HashMap<String,Object>> updateCartItem(int itemid,int quantity) throws DAOException{
		try{
			logger.info("Starting the ShoppingCartDao - updateCartItem()" + itemid);
			CallableStatement cst = conn.prepareCall(UPDATE_CART_ITEM_QUANTITY);	    
			cst.setInt(1,itemid);
			cst.setInt(2,quantity);
			cst.executeUpdate();
			cst = conn.prepareCall(GET_CART_ITEM_QUANTITY);
			cst.setInt(1, itemid);
			rs = cst.executeQuery();
			List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();
			ResultSetMetaData md = rs.getMetaData();
		    int columns = md.getColumnCount();
			while (rs.next()) {
				HashMap<String,Object> row = new HashMap<String, Object>(columns);
		        for(int i=1; i<=columns; ++i) {
		            row.put(md.getColumnName(i),rs.getObject(i));
		        }
		        list.add(row);
			}	
			conn.close();	
			return list;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in updating Shopping Cart items");
		}
	}
	
	public List<ShoppingCartItem> getProductsInCart(String cartid) throws DAOException{
		try{
			logger.info("Starting the ShoppingCartDao - getProductsInCart()" + cartid);
			CallableStatement cst = conn.prepareCall(GET_LIST_OF_PRODUCTS_IN_A_SHOPPING_CART);	    
			cst.setString(1,cartid);
			rs = cst.executeQuery();
			ShoppingCartItem item = null;
			List<ShoppingCartItem> list = new ArrayList<ShoppingCartItem>();
			while (rs.next()) {
				item = new ShoppingCartItem(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getString(4),rs.getInt(5),rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(9),rs.getString(10));
				list.add(item);
			}
			conn.close();	
			return list;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in deleting Shopping Cart items");
		}
	}
	
	public Boolean removeItemShoppingCart(int itemid) throws DAOException{
		try{
			logger.info("Starting the ShoppingCartDao - removeItemShoppingCart()" + itemid);
			CallableStatement cst = conn.prepareCall(REMOVE_ITEM_FROM_SHOPPING_CART);	    
			cst.setInt(1,itemid);
			int rowsdeleted = cst.executeUpdate();
			
			if (rowsdeleted > 0) {
				return true;
			}	
			conn.close();	
			return false;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in deleting Shopping Cart items");
		}
	}
}