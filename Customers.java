package com.turing.customer;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.apache.log4j.Logger;

import com.turing.exception.DAOException;
import com.turing.exception.ErrorObject;
import com.turing.security.Token;

/**
 * Servlet implementation class Customers
 */
@WebServlet(description = "Customer Service", urlPatterns = { "/customers/*","/customer/*"})
public class Customers extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private GsonBuilder builder = new GsonBuilder();
	private Gson gson = builder.serializeNulls().create();
	private ErrorObject err;
	private Map<String,Object> customer = new HashMap<String,Object>();
	private final Logger logger = Logger.getLogger(com.turing.customer.Customers.class);
	private Customer c;
	private Token t;
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Customers() {
		super();
		// TODO Auto-generated constructor stub
	}
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String input;
		String name, email, password;
		int userid;

		try{
			String[] service = request.getRequestURI().split("/");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3: 
				int customer_id = (int) request.getSession().getAttribute("userid");				
				c = new CustomerDao().getCustomer(customer_id);	
				response.getWriter().append(gson.toJson(c));
			    break;
			default:break;
			}
			 response.flushBuffer();
		 } catch(DAOException d){
			err = new ErrorObject(400,"CUS_03",d.getField());
			response.getWriter().append(gson.toJson(err.getError()));
			 response.flushBuffer();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String input;
		String name, email, password;
		JsonParser parser;
		JsonElement element;
		JsonObject jsonObject;
		try{
			String[] service = request.getRequestURI().split("/");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3: if((input = br.readLine()) != null){
				parser = new JsonParser();
				element = parser.parse(input); 
				jsonObject = element.getAsJsonObject();
				name = jsonObject.get("name").getAsString();
				email = jsonObject.get("email").getAsString();
				password = jsonObject.get("password").getAsString(); 
				c = new CustomerDao().createCustomer(name,email, password);
				t = new Token(c.getCustomer_id());				
				customer.put("expiresIn", t.getExpirytime());
				customer.put("accessToken", t.getToken());
				customer.put("customer", c);
				request.getSession().setAttribute("userid", c.getCustomer_id());
				request.getSession().setAttribute("token", t.getToken());	
				request.getSession().setAttribute("expiry", t.getExpirytime());	
				response.getWriter().append(gson.toJson(customer));
			}; break;
			
		case 4: if(service[3].matches("login")){
			if((input = br.readLine()) != null){
			parser = new JsonParser();
			element = parser.parse(input); 
			jsonObject = element.getAsJsonObject();
			email = jsonObject.get("email").getAsString();
			password = jsonObject.get("password").getAsString(); 
			Customer c = new CustomerDao().loginCustomer(email, password);
			t = new Token(c.getCustomer_id());				
			customer.put("expiresIn", t.getExpirytime());
			customer.put("accessToken", t.getToken());
			customer.put("customer", c);
			request.getSession().setAttribute("userid", c.getCustomer_id());
			request.getSession().setAttribute("token", t.getToken());	
			request.getSession().setAttribute("expiry", t.getExpirytime());	
			response.getWriter().append(gson.toJson(customer));
			}
		}
		} 
			 response.flushBuffer();
		}catch(DAOException d){
			if(!d.getField().matches("CUS_04")){
			err = new ErrorObject(400,"CUS_03",d.getField());
			} else {
				err = new ErrorObject(400,d.getField(),"email");
			}
			response.getWriter().append(gson.toJson(err.getError()));
			 response.flushBuffer();
		}
	}
	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String input;
		String email, name, day_phone,mob_phone,eve_phone,address_1,address_2,city,region,postal_code,card;
		int customer_id, shipping_region_id;
		JsonParser parser;
		JsonElement element;
		JsonObject jsonObject;
		try{
			String[] service = request.getRequestURI().split("/");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3: if((input = br.readLine()) != null){
				parser = new JsonParser();
				element = parser.parse(input); 
				jsonObject = element.getAsJsonObject();
				name = jsonObject.get("name").getAsString();
				email = jsonObject.get("email").getAsString();
				day_phone = jsonObject.get("day_phone").getAsString();
				eve_phone = jsonObject.get("eve_phone").getAsString(); 
				mob_phone = jsonObject.get("mob_phone").getAsString(); 
				c = new CustomerDao().updateCustomer(email, name, day_phone, eve_phone, mob_phone);			
				response.getWriter().append(gson.toJson(c));
			}; break;
			
		case 4: if(service[3].matches("address")){
			if((input = br.readLine()) != null){
			parser = new JsonParser();
			element = parser.parse(input); 
			jsonObject = element.getAsJsonObject();
			address_1 = jsonObject.get("address_1").getAsString();
			address_2 = jsonObject.get("address_2").getAsString(); 
			city = jsonObject.get("city").getAsString();
			region = jsonObject.get("region").getAsString();
			postal_code = jsonObject.get("postal_code").getAsString();
			region = jsonObject.get("region").getAsString();
			shipping_region_id = jsonObject.get("shipping_region_id").getAsInt();
			customer_id = (int) request.getSession().getAttribute("userid");
			Customer c = new CustomerDao().updateCustomerAddress(customer_id, address_1, address_2, city, region, postal_code, shipping_region_id);
			response.getWriter().append(gson.toJson(c));
			}
		};
		
		if(service[3].matches("creditCard")){
			if((input = br.readLine()) != null){
			parser = new JsonParser();
			element = parser.parse(input); 
			jsonObject = element.getAsJsonObject();
			card = jsonObject.get("credit_card").getAsString();
			customer_id = (int) request.getSession().getAttribute("userid");
			Customer c = new CustomerDao().updateCustomerCard(customer_id, card);			
			response.getWriter().append(gson.toJson(c));
			}
		}; break;
		}  response.flushBuffer();
		}catch(DAOException d){
			err = new ErrorObject(400,"CUS_03",d.getField());
			response.getWriter().append(gson.toJson(err.getError()));
			 response.flushBuffer();
		}
	}

}
