package com.turing.category;

import java.io.IOException;
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
import com.turing.category.Category;
import com.turing.category.CategoriesDao;
import com.turing.exception.DAOException;
import com.turing.exception.ErrorObject;

/**
 * Servlet implementation class Categories
 */
@WebServlet("/categories/*")
public class Categories extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(com.turing.category.Categories.class);   
	private ErrorObject err;
	private Gson gson = new GsonBuilder().create();
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Categories() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String[] service = request.getRequestURI().split("/");
			List<Category> categories = new ArrayList<Category>();
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:   categories = new CategoriesDao().getCategories();
			Map<String,List<Category>> result = new HashMap<String,List<Category>>();
			result.put("rows",categories);
			response.getWriter().append(gson.toJson(result));
			break;
			case 4:  
				if (service[3].matches("\\d+")){
					Category c = new CategoriesDao().getCategory(Integer.parseInt(service[3]));
					if( c != null){
						response.getWriter().append(gson.toJson(c));
					} else {
						err = new ErrorObject(400,"CAT_01","category_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				} else {
					err = new ErrorObject(400,"URL_01","category_id");
					response.getWriter().append(gson.toJson(err.getError()));
				}
				break;

			case 5:  
				if (service[3].matches("inDepartment")){
					List<Category> clist = new CategoriesDao().getCategoryInDepartment(Integer.parseInt(service[4]));	
					if( !clist.isEmpty()){
						Map<String,List<Category>> finallist = new HashMap<String,List<Category>>();
						finallist.put("rows",clist);
						response.getWriter().append(gson.toJson(finallist));
					} else {
						err = new ErrorObject(400,"CAT_01","category_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}

				}; 
				if (service[3].matches("inProduct")){
					Category c = new CategoriesDao().getCategoryInProduct(Integer.parseInt(service[4]));	
					if( c != null){
						response.getWriter().append(gson.toJson(c.getCategoryInProduct()));
					} else {
						err = new ErrorObject(400,"CAT_01","category_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				};break;
			default: break;
			}
			 response.flushBuffer();
		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"CAT_01","category_id");
			response.getWriter().append(gson.toJson(err.getError()));
			 response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"CAT_03",e.getField());
			response.getWriter().append(gson.toJson(err.getError()));
			 response.flushBuffer();
		}   catch (Exception e){
			err = new ErrorObject(400,"CAT_02","Category");
			response.getWriter().append(gson.toJson(err.getError()));
			logger.error("Error Message:", e);
			 response.flushBuffer();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
