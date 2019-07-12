package com.turing.shipping;


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
import com.turing.exception.DAOException;
import com.turing.exception.ErrorObject;



/**
 * Servlet implementation class Shipping
 */
@WebServlet("/shipping/*")
public class Shipping extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new GsonBuilder().create();
	private ErrorObject err;
	private final Logger logger = Logger.getLogger(com.turing.shipping.Shipping.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Shipping() {
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
			List<ShippingRegion> shippingregions = new ArrayList<ShippingRegion>();
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:  break;
			case 4:  
				    shippingregions = new ShippingDao().getAllShippingRegion();
					if( shippingregions != null){
						response.getWriter().append(gson.toJson(shippingregions));
					} else {
						err = new ErrorObject(400,"SHP_01","shipping_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}
					break;
			case 5:  
				if (service[4].matches("\\d+")){
					ShippingInfo s = new ShippingDao().getShippingRegionInfo(Integer.parseInt(service[4]));
					if( s != null){
						response.getWriter().append(gson.toJson(s));
					} else {
						err = new ErrorObject(400,"SHP_01","shipping_id");
						response.getWriter().append(gson.toJson(err.getError()));
					}
				
			}; break;
			default: err = new ErrorObject(400,"URL_01","");
			         response.getWriter().append(gson.toJson(err.getError()));
			         break;
			} response.flushBuffer();
		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"SHP_01","shipping_id");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"SHP_03",e.getField());
			logger.error("Error Message:", e);
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}   catch (Exception e){
			err = new ErrorObject(400,"SHP_02","");
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
	}

}
