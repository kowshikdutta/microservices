package com.turing.product;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.turing.exception.DAOException;
import com.turing.exception.ErrorObject;
import com.turing.review.ReviewDao;
import com.turing.review.ReviewInput;
import com.turing.review.ReviewOutput;

/**
 * Servlet implementation class Products
 */
@WebServlet(description = "All operations on Products", urlPatterns = { "/products/*" })
public class Products extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new GsonBuilder().create();
	private ErrorObject err;
	private final Logger logger = Logger.getLogger(com.turing.product.Products.class);
	private Map<String,Object> resultset = new HashMap<String,Object>();
	private Map<String,Object> pagemeta = new HashMap<String,Object>();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Products() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		int pagesize = Integer.parseInt(getServletContext().getInitParameter("pagesize"));
		int lastindex = 0;
		if(((Integer) request.getSession().getAttribute("lastindex")) != null){
			lastindex = (int) request.getSession().getAttribute("lastindex");
		}
		
		try {
			String[] service = request.getRequestURI().split("/");
			// Check if User is requesting for all departments
			List<Product> products = new ArrayList<Product>();
			List<ProductSubset> productsubset = new ArrayList<ProductSubset>();
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:  
			int count = new ProductDao().getProductCount();
			if(lastindex < count){
			products = new ProductDao().getProducts(lastindex,pagesize);
			getPageMeta(count,pagesize, lastindex);
			resultset.put("paginationMeta",pagemeta);
			resultset.put("rows",products);
			response.getWriter().append(gson.toJson(resultset));
			request.getSession().setAttribute("lastindex", lastindex + pagesize);
			} else {
				products = new ProductDao().getProducts(0,pagesize);
				getPageMeta(count,pagesize, 0);
				resultset.put("paginationMeta",pagemeta);
				resultset.put("rows",products);
				response.getWriter().append(gson.toJson(resultset));
				request.getSession().setAttribute("lastindex", pagesize);
			}
			break;
			case 4:  
				if (service[3].matches("\\d+")){
					Product p = new ProductDao().getProduct(Integer.parseInt(service[3]));
					if( p != null){
						response.getWriter().append(gson.toJson(p));
					} else {
						err = new ErrorObject(400,"PRD_01","product_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				} else if(service[3].matches("search")){
					String parameter = request.getParameter("query_string");
					if(parameter != null){
						productsubset = new ProductDao().getProductSearch(parameter);
						Map<String,List<ProductSubset>> searchresult = new HashMap<String,List<ProductSubset>>();
						searchresult.put("rows", productsubset);
						response.getWriter().append(gson.toJson(searchresult));
					} else{
						err = new ErrorObject(400,"PRD_01","product_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}

				} else {
					err = new ErrorObject(400,"URL_01","");
					response.getWriter().append(gson.toJson(err.getError()));
				}
				break;

			case 5:  
				if (service[3].matches("inCategory")){
					productsubset = new ProductDao().getProductsInCategory(Integer.parseInt(service[4]));
					Map<String,List<ProductSubset>> finallist = new HashMap<String,List<ProductSubset>>();
					if( productsubset != null){
						finallist.put("rows",productsubset);
						response.getWriter().append(gson.toJson(finallist));
					} else {
						err = new ErrorObject(400,"PRD_01","product_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}

				}; 
				if (service[3].matches("inDepartment")){
					productsubset = new ProductDao().getProductsInDepartment(Integer.parseInt(service[4]));
					Map<String,List<ProductSubset>> finallist = new HashMap<String,List<ProductSubset>>();
					if( productsubset != null){
						finallist.put("rows",productsubset);
						response.getWriter().append(gson.toJson(finallist));
					} else {
						err = new ErrorObject(400,"PRD_01","product_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}

				};				
			break;
			default: break;
			} response.flushBuffer();

		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"PRD_01","product_id");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"PRD_03",e.getField());
			logger.error("Error Message:", e);
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}   catch (Exception e){
			err = new ErrorObject(400,"PRD_02","");
			logger.error("Error Message:", e);
			response.flushBuffer();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			String[] service = request.getRequestURI().split("/");
			// Check if User is requesting for all departments
			List<ReviewOutput> review_output = new ArrayList<ReviewOutput>();
			ReviewInput review_input = new ReviewInput();
			String input;
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
//			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(response.getOutputStream()));
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:  break;
			case 4:  break;
			case 5:  
				if (service[3].matches("\\d+") && service[4].matches("review")){
  					if((input = br.readLine()) != null){
  						review_input = gson.fromJson(input, ReviewInput.class);
  						review_output = new ReviewDao().postReview(review_input.getCustomer_id(), review_input.getProduct_id(), review_input.getReview(),review_input.getRating());
  						if( review_output != null){
  							response.getWriter().append(gson.toJson(review_output));
  							response.flushBuffer();
  						} else {
  							err = new ErrorObject(400,"PRD_01","product_id");
  							response.getWriter().append(gson.toJson(err.getError()));
  						}
  				}
			}
			break;
			default: break;
			} response.flushBuffer();

		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"PRD_01","product_id");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"PRD_03",e.getField());
			logger.error("Error Message:", e);
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
			
		}   catch (Exception e){
			err = new ErrorObject(400,"PRD_02","");
			logger.error("Error Message:", e);
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}
	}

private void getPageMeta(int count,int pagesize,int lastindex){
	int currentpage = 1;
	if(lastindex!= 0){
	currentpage = (int) Math.ceil(((double) (lastindex + 1)) / pagesize);
	}
	int totalpage = (int) Math.ceil(((double) (count)) / pagesize);
	pagemeta.put("currentPage", currentpage);
	pagemeta.put("currentPageSize", pagesize);
	pagemeta.put("totalPages", totalpage);
	pagemeta.put("totalRecords", count);
}
}
