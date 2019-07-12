package com.turing.order;

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
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.turing.exception.DAOException;
import com.turing.exception.ErrorObject;

/**
 * Servlet implementation class Orders
 */
@WebServlet(description = "Service for Order", urlPatterns = { "/orders/*" })
public class Orders extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(com.turing.order.Orders.class);   
	private GsonBuilder builder = new GsonBuilder();
	private Gson gson = builder.serializeNulls().create();
	private ErrorObject err;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Orders() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
         int userid;

		try {
			String[] service = request.getRequestURI().split("/");
			BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			userid = (int) request.getSession().getAttribute("userid");
			List<OrderItems> orderitems = new ArrayList<OrderItems>();
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:  break;
			case 4:  
				if (service[3].matches("\\d+")){
					List<OrderItems> items = new OrderDao().getOrder(Integer.parseInt(service[3]));
					int orderid = Integer.parseInt(service[3]);
					HashMap<String,Object> output = new HashMap<String,Object>();
					if(!items.isEmpty()){						
						output.put("order_id",orderid);
						output.put("order_items", items);
						response.getWriter().append(gson.toJson(output));
					} else {
						err = new ErrorObject(400,"ORD_01","order_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				};
				if (service[3].matches("inCustomer")){
					List<OrderInCustomer> items = new OrderDao().getCustomersOrder(Integer.parseInt(service[3]));
					if(!items.isEmpty()){						
						response.getWriter().append(gson.toJson(items));
					} else {
						err = new ErrorObject(400,"ORD_01","customer_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				}; 	break;
			case 5:  
				if (service[3].matches("shortDetail") && service[4].matches("\\d+")){
					OrderShortDetails items = new OrderDao().getOrderShortDetails(Integer.parseInt(service[4]));
					if( items != null){
						response.getWriter().append(gson.toJson(items));
					} else {
						err = new ErrorObject(400,"ORD_01","order_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				}; default: break;
			}
			
			response.flushBuffer();
			
		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"ORD_01","order_id");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"ORD_03",e.getField());
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}   catch (Exception e){
			err = new ErrorObject(400,"ORD_02","");
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
		String[] service = request.getRequestURI().split("/");
		BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
		int userid = (int) request.getSession().getAttribute("userid");
		int shipping_id,tax_id;
		String cart_id;
		String input;
		JsonParser parser;
		JsonElement element;
		JsonObject jsonObject;
		try {
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:   
				if((input = br.readLine()) != null){
					parser = new JsonParser();
					element = parser.parse(input); 
					jsonObject = element.getAsJsonObject();
					cart_id = jsonObject.get("cart_id").getAsString();
					shipping_id = jsonObject.get("shipping_id").getAsInt();
					tax_id = jsonObject.get("tax_id").getAsInt(); 
					int orderid = new OrderDao().createOrder(cart_id, userid, shipping_id, tax_id);
					Map<String,Integer> result= new HashMap<String,Integer>();
					if(orderid > 0){
						result.put("order_id", orderid);
						response.getWriter().append(gson.toJson(result));
						response.flushBuffer();
					} else {
						err = new ErrorObject(400,"ORD_01","order_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}			
				};	break;
			default:err = new ErrorObject(400,"URL_01","order_id");
			response.getWriter().append(gson.toJson(err.getError()));break;
			}	response.flushBuffer();	
		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"ORD_01","order_id");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"ORD_03",e.getField());
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}   catch (Exception e){
			err = new ErrorObject(400,"ORD_02","");
			response.getWriter().append(gson.toJson(err.getError()));
			logger.error("Error Message:", e);
			response.flushBuffer();
		}

	}
}
