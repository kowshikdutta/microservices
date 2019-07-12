package com.turing.tax;

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
 * Servlet implementation class TaxServlet
 */
@WebServlet("/tax/*")
public class TaxServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private Gson gson = new GsonBuilder().create();
	private ErrorObject err;
	private final Logger logger = Logger.getLogger(com.turing.tax.TaxServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TaxServlet() {
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
			List<Tax> alltax = new ArrayList<Tax>();
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:
				    alltax = new TaxDao().getAllTax();
					if( alltax != null){
						response.getWriter().append(gson.toJson(alltax));
					} else {
						err = new ErrorObject(400,"TAX_01","");
						response.getWriter().append(gson.toJson(err.getError()));
					}
					break;
			case 4:  if (service[3].matches("\\d+")){
				Tax t = new TaxDao().getTaxById(Integer.parseInt(service[3]));
				if( t != null){
					response.getWriter().append(gson.toJson(t));
				} else {
					err = new ErrorObject(400,"TAX_01","");
					response.getWriter().append(gson.toJson(err.getError()));
				}; 
			
		}; break;
			
			default: err = new ErrorObject(400,"URL_01","");
			         response.getWriter().append(gson.toJson(err.getError()));
			         break;
			} response.flushBuffer();
		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"TAX_01","tax_id");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"TAX_03",e.getField());
			logger.error("Error Message:", e);
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
			
		}   catch (Exception e){
			err = new ErrorObject(400,"TAX_02","");
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
