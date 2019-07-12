package com.turing.attribute;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.turing.attribute.Attribute;
import com.turing.attribute.AttributeDao;
import com.turing.exception.DAOException;
import com.turing.exception.ErrorObject;


/**
 * Servlet implementation class Attributes
 */
@WebServlet("/attributes/*")
public class Attributes extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private Gson gson = new GsonBuilder().create();
    private ErrorObject err;
    private final Logger logger = Logger.getLogger(com.turing.attribute.Attributes.class);
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Attributes() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		try {
			String[] service = request.getRequestURI().split("/");
			// Check if User is requesting for all departments

			List<Attribute> attributes = new ArrayList<Attribute>();


			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:  attributes = new AttributeDao().getAttributes();
			response.getWriter().append(gson.toJson(attributes));
			break;
			case 4:  
				if (service[3].matches("\\d+")){
					Attribute a = new AttributeDao().getAttribute(Integer.parseInt(service[3]));
					if (a != null) {
						response.getWriter().append(gson.toJson(a));
					} else {
						 err = new ErrorObject(400,"ATT_01","");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				} else {
					err = new ErrorObject(400,"URL_01","");
					response.getWriter().append(gson.toJson(err.getError()));
				}

				break;


			case 5:  
				if (service[3].matches("values")){
					List<AttributeValues> avalues = new AttributeDao().getAttributeValues(Integer.parseInt(service[4]));
					if (avalues != null) {
						response.getWriter().append(
								gson.toJson(avalues));
					} else {
						err = new ErrorObject(400,"ATT_01","");
						response.getWriter().append(gson.toJson(err.getError()));
					}

				}; 
				if (service[3].matches("inProduct")){
					List<AttributeInProduct> alist = new AttributeDao().getAttributeInProduct(Integer.parseInt(service[4]));
					if (!alist.isEmpty()) {
						response.getWriter().append(gson.toJson(alist));
					} else {
						err = new ErrorObject(400,"ATT_01","");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				};break;
			default: break;
			}
          response.flushBuffer();
		} catch (NumberFormatException e) {
			err = new ErrorObject(400,"ATT_01","attribute_id");
			response.getWriter().append(gson.toJson(err.getError()));
			 response.flushBuffer();
		} catch (DAOException e) {
			err = new ErrorObject(400,"ATT_03","attribute_id");
			response.getWriter().append(gson.toJson(err.getError()));
			 response.flushBuffer();
		} catch (Exception e) {
			err = new ErrorObject(400,"ATT_02","attribute_id");
			response.getWriter().append(gson.toJson(err.getError()));
			logger.error("Error Message:", e);
			response.flushBuffer();
		}
	}


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
