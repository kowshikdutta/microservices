package com.turing.product;

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

import com.turing.attribute.Attribute;
import com.turing.exception.DAOException;



public class ProductDao {
	private static final String GET_ALL_PRODUCTS = "SELECT product_id, name, description,price, discounted_price,thumbnail FROM product order by product_id asc limit ?,?";
	private static final String GET_SINGLE_PRODUCT = "call catalog_get_product_info(?)";
	private static final String GET_PRODUCT_SEARCH = "select product_id, name, description, price, discounted_price, thumbnail from product where name like ? or description like ?";
	private static final String GET_ALL_PRODUCT_IN_CATEGORY = "select a.product_id, a.name, a.description, a.price, a.discounted_price, a.thumbnail from product a, product_category b where a.product_id = b.product_id and b.category_id = ?";
	private static final String GET_ALL_PRODUCT_IN_DEPARTMENT = "select a.product_id, a.name, a.description, a.price, a.discounted_price, a.thumbnail from product a, product_category b, category c where a.product_id = b.product_id and b.category_id = c.category_id and c.department_id = ?";
	private static final String GET_ALL_PRODUCT_REVIEW = "call catalog_get_product_reviews(?)";
	private static final String GET_PRODUCT_METADATA = "select count(*) from product";
	
	
	private InitialContext ctx;
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private ArrayList<Product> list;
	private final Logger logger = Logger.getLogger(com.turing.product.ProductDao.class);


	public ProductDao() throws DAOException{
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
			throw new DAOException("Error in getting Product details from DataBase");
		}

	}
	
	public List<Product> getProducts(int index, int pagesize) throws DAOException {
		try{
			logger.info("Starting the AttributeDao - getProducts()");

			PreparedStatement pstmt = conn.prepareStatement(GET_ALL_PRODUCTS);
			pstmt.setInt(1, index);
			pstmt.setInt(2, pagesize);
			
			rs = pstmt.executeQuery();

			list = new ArrayList<Product>();

			while (rs.next()) {
				Product a = new Product(rs.getInt(1),rs.getString(2),rs.getString(3),rs.getDouble(4), rs.getDouble(5),rs.getString(6));
				list.add(a);
			}	    
			conn.close();
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Product details from DataBase");

		}
		return list;
	}

	public Product getProduct(int productid) throws DAOException {
		// TODO Auto-generated method stub
		try{
			logger.info("Starting the ProductDao - getProduct()" + productid);
			CallableStatement cst = conn.prepareCall(GET_SINGLE_PRODUCT);	    
			cst.setInt(1,productid);
			rs = cst.executeQuery();
			Product p = null;
			while (rs.next()) {
				p = new Product(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getDouble(4), rs.getDouble(5), rs.getString(6),rs.getString(7),rs.getString(8),rs.getInt(9));
			}	
			conn.close();	
			return p;


		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Product details from DataBase");

		}

	}

	public List<ProductSubset> getProductSearch(String parameter) throws DAOException {
		// TODO Auto-generated method stub
		try{
			logger.info("Starting the ProductDao - getProductSearch()" + parameter);
			CallableStatement cst = conn.prepareCall(GET_PRODUCT_SEARCH);	    
			cst.setString(1,"%" + parameter + "%");
			cst.setString(2,"%" + parameter + "%");
			rs = cst.executeQuery();
			List<ProductSubset> p = new ArrayList<ProductSubset>();
			while (rs.next()) {
				p.add(new ProductSubset(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getDouble(4), rs.getDouble(5), rs.getString(6)));
			}	
			conn.close();	
			return p;


		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Product details from DataBase");

		}
	}

	public List<ProductSubset> getProductsInCategory(int categoryid) throws DAOException  {
		// TODO Auto-generated method stub
		try{
			logger.info("Starting the ProductDao - getProductInCategory()" + categoryid);
			CallableStatement cst = conn.prepareCall(GET_ALL_PRODUCT_IN_CATEGORY);	    
			cst.setInt(1,categoryid);
			rs = cst.executeQuery();
			List<ProductSubset> p = new ArrayList<ProductSubset>();
			while (rs.next()) {
				p.add(new ProductSubset(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getDouble(4), rs.getDouble(5), rs.getString(6)));
			}	
			conn.close();	
			return p;


		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Product details from DataBase");

		}
	}

	public List<ProductSubset> getProductsInDepartment(int departmentid) throws DAOException {
		// TODO Auto-generated method stub
		try{
			logger.info("Starting the ProductDao - getProductInDepartment()" + departmentid);
			CallableStatement cst = conn.prepareCall(GET_ALL_PRODUCT_IN_DEPARTMENT);	    
			cst.setInt(1,departmentid);
			rs = cst.executeQuery();
			List<ProductSubset> p = new ArrayList<ProductSubset>();
			while (rs.next()) {
				p.add(new ProductSubset(rs.getInt(1), rs.getString(2), rs.getString(3),
						rs.getDouble(4), rs.getDouble(5), rs.getString(6)));
			}	
			conn.close();	
			return p;


		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Product details from DataBase");

		}
	}

	public List<ProductReview> getProductReview(int productid)  throws DAOException {
		// TODO Auto-generated method stub
		try{
			logger.info("Starting the ProductDao - getProductReview()" + productid);
			CallableStatement cst = conn.prepareCall(GET_ALL_PRODUCT_REVIEW);	    
			cst.setInt(1,productid);
			rs = cst.executeQuery();
			List<ProductReview> p = new ArrayList<ProductReview>();
			while (rs.next()) {
				p.add(new ProductReview(rs.getString(1), rs.getString(2),
						rs.getInt(3), rs.getString(4)));
			}	
			conn.close();	
			return p;


		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Product Review details from DataBase");

		}
	}
	
	public int getProductCount()  throws DAOException {
		// TODO Auto-generated method stub
		try{
			logger.info("Starting the ProductDao - getProductCount()");
			PreparedStatement pst = conn.prepareStatement(GET_PRODUCT_METADATA);	    
			rs = pst.executeQuery();
			int count = 0;
			while (rs.next()) {
				count = rs.getInt(1);
			}	
			conn.close();	
			return count;


		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in getting Product Count details from DataBase");

		}
	}
}
