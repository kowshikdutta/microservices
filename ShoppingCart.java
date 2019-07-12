package com.turing.shoppingcart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.turing.exception.DAOException;
import com.turing.exception.ErrorObject;
import com.turing.order.OrderDao;
import com.turing.order.OrderItems;
import com.turing.order.OrderShortDetails;
import com.turing.shipping.ShippingDao;
import com.turing.shipping.ShippingInfo;
import com.turing.shipping.ShippingRegion;

/**
 * Servlet implementation class ShoppingCart
 */
@WebServlet("/shoppingcart/*")
public class ShoppingCart extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new GsonBuilder().create();
	private ErrorObject err;
	private final Logger logger = Logger.getLogger(com.turing.shoppingcart.ShoppingCart.class);   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ShoppingCart() {
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
			switch (service.length) {
			case 1:  break;
			case 2:  break;		
			case 3:  break;
			case 4:  if (service[3].matches("generateUniqueId")){
					DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
					Date date = new Date();
					Integer userid = (Integer) request.getSession().getAttribute("userid");
					if( userid.intValue() > 0 ){
						response.getWriter().append(gson.toJson(dateFormat.format(date) + "C" + userid.intValue()));
						response.flushBuffer();
					} else {
						err = new ErrorObject(400,"CRT_06","");
						response.getWriter().append(gson.toJson(err.getError()));
						response.flushBuffer();
					}; 
				
			}else{
				List<ShoppingCartItem> items = new ShoppingCartDao().getProductsInCart(service[3]);
				if(!items.isEmpty()){						
					response.getWriter().append(gson.toJson(items));
				} else {
					err = new ErrorObject(400,"CRT_01","cart_id");
					response.getWriter().append(gson.toJson(err.getError()));
				}
			} ;break;
			
			default: err = new ErrorObject(400,"URL_01","");
			         response.getWriter().append(gson.toJson(err.getError()));
			         break;
			} response.flushBuffer();
		}  catch (Exception e){
			err = new ErrorObject(400,"CRT_01","");
			logger.error("Error Message:", e);
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] service = request.getRequestURI().split("/");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		int userid = (int) request.getSession().getAttribute("userid");
		int quantity,product_id;
		String input,cart_id,attributes;
		JsonParser parser;
		JsonElement element;
		JsonObject jsonObject;
		Map<String,Object> list = new HashMap<String,Object>();
		try {
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:  break; 
			case 4:	
				if((input = br.readLine()) != null){
					parser = new JsonParser();
					element = parser.parse(input); 
					jsonObject = element.getAsJsonObject();
					cart_id = jsonObject.get("cart_id").getAsString();
					product_id = jsonObject.get("product_id").getAsInt();
					attributes = jsonObject.get("attributes").getAsString(); 
					quantity = jsonObject.get("quantity").getAsInt();
					int item_id = new ShoppingCartDao().insertProductInShoppingCart(cart_id, product_id, attributes, quantity);
					if(item_id > 0){
						list.put("item_id", item_id);
						list.put("cart_id", cart_id);
						list.put("product_id", product_id);
						list.put("attributes", attributes);
						list.put("quantity", quantity);
						response.getWriter().append(gson.toJson(list));
					} else {
						err = new ErrorObject(400,"CRT_04","cart_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}			
				};	break;
			default:err = new ErrorObject(400,"URL_01","cart_id");
			response.getWriter().append(gson.toJson(err.getError()));break;
			}	response.flushBuffer();	
		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"URL_01","URL");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"CRT_03",e.getField());
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}   catch (Exception e){
			err = new ErrorObject(400,"CRT_02","");
			response.getWriter().append(gson.toJson(err.getError()));
			logger.error("Error Message:", e);
			response.flushBuffer();
		}

	}


	/**
	 * @see HttpServlet#doPut(HttpServletRequest, HttpServletResponse)
	 */
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] service = request.getRequestURI().split("/");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		String input;
		int quantity;
		JsonParser parser;
		JsonElement element;
		JsonObject jsonObject;
		try {
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:  break;
			case 4:  break;
			case 5:  if (service[3].matches("update") && service[4].matches("\\d+")){
				    int itemid = Integer.parseInt(service[4]);
				    if((input = br.readLine()) != null){
						parser = new JsonParser();
						element = parser.parse(input); 
						jsonObject = element.getAsJsonObject();
						quantity = jsonObject.get("quantity").getAsInt();
						List<HashMap<String,Object>> item = new ShoppingCartDao().updateCartItem(itemid, quantity);
					if( !item.isEmpty()){
						response.getWriter().append(gson.toJson(item));
					} else {
						err = new ErrorObject(400,"CRT_05","item_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				}}; break;
			default:err = new ErrorObject(400,"URL_01","cart_id");
			response.getWriter().append(gson.toJson(err.getError()));break;
			} response.flushBuffer();		
		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"CRT_01","cart_id");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"CRT_03",e.getField());
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}   catch (Exception e){
			err = new ErrorObject(400,"CRT_02","");
			response.getWriter().append(gson.toJson(err.getError()));
			logger.error("Error Message:", e);
			response.flushBuffer();
		}
		
	}

	/**
	 * @see HttpServlet#doDelete(HttpServletRequest, HttpServletResponse)
	 */
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String[] service = request.getRequestURI().split("/");
		try {
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:  break;
			case 4:  break;
			case 5:  if (service[3].matches("empty")){
				   Boolean result = new ShoppingCartDao().emptyShoppingCart(service[4]);
					if( result){
						response.getWriter().append("[]");
					} else {
						err = new ErrorObject(400,"CRT_01","cart_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				}; 
				if (service[3].matches("removeProduct")){
					   Boolean result = new ShoppingCartDao().removeItemShoppingCart(Integer.parseInt(service[4]));
						if( result){
							response.getWriter().append("{\"Message\":\"Successfully removed item \"}");
						} else {
							err = new ErrorObject(400,"CRT_05","item_id");
							response.getWriter().append(gson.toJson(err.getError()));
						}
					};	break;
			    default:err = new ErrorObject(400,"URL_01","cart_id");
			response.getWriter().append(gson.toJson(err.getError()));break;
			}	response.flushBuffer();	
		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"CRT_01","cart_id");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"CRT_03",e.getField());
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}   catch (Exception e){
			err = new ErrorObject(400,"CRT_02","");
			response.getWriter().append(gson.toJson(err.getError()));
			logger.error("Error Message:", e);
			response.flushBuffer();
		}
	}

}
