package com.turing.review;

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





import com.turing.category.Category;
import com.turing.exception.DAOException;
import com.turing.product.ProductReview;

public class ReviewDao {
	private static final String SUBMIT_REVIEW = "call catalog_create_product_review(?, ?, ?, ?);";
	private static final String GET_REVIEW = "call catalog_get_product_reviews(?)";	
	private InitialContext ctx;
	private Connection conn;
	private DataSource ds;
	private ResultSet rs;
	private ArrayList<ReviewOutput> list;
	private ReviewInput review;
	private final Logger logger = Logger.getLogger(com.turing.review.ReviewDao.class);

	public ReviewDao() throws DAOException{
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
			throw new DAOException("Unable to connect to DB");
		}
	}
	
	public List<ReviewOutput> postReview(int customerid,int productid,String review,int rating) throws DAOException {
		try{
			logger.info("Starting the ReviewDao - postReview()");
			List<ReviewOutput> p = new ArrayList<ReviewOutput>();
			PreparedStatement cst = conn.prepareStatement(SUBMIT_REVIEW);	    
			cst.setInt(1,customerid);
			cst.setInt(2,productid);
			cst.setString(3, review);
			cst.setInt(4, rating);
			rs = cst.executeQuery();						
			PreparedStatement cstoutput = conn.prepareStatement(GET_REVIEW);	    
			cstoutput.setInt(1,productid);		
//			if(rs.rowInserted() || rs.rowUpdated()){
				rs = cstoutput.executeQuery();
				while (rs.next()) {
					p.add(new ReviewOutput(rs.getString(1), rs.getString(2),
							rs.getInt(3), rs.getString(4)));					
				}	
//			}			
			conn.close();	
			return p;
		} catch (Exception e){
			logger.error("Error Message:", e);
			throw new DAOException("Error in submitting Review","");

		}

	}
	
	

}
