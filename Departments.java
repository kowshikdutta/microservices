package com.turing.department;

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
 * Servlet implementation class Departments
 */
@WebServlet(description = "GET ALL DEPARTMENTS", urlPatterns = { "/departments/*" })
public class Departments extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Logger logger = Logger.getLogger(com.turing.department.Departments.class);
	Gson gson = new GsonBuilder().create();
	private ErrorObject err;
	
	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Departments() {
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
			List<Department> departments = new ArrayList<Department>();
			switch (service.length) {
			case 1:  break;
			case 2:  break;
			case 3:  departments = new DepartmentDao().getDepartments();
			response.getWriter().append(gson.toJson(departments));
			break;
			case 4:  
				if (service[3].matches("\\d+")){
					Department d = new DepartmentDao().getDepartment(Integer.parseInt(service[3]));
					if( d != null){
						response.getWriter().append(gson.toJson(d));
					} else {
						err = new ErrorObject(400,"DEP_02","Department");
						response.getWriter().append(gson.toJson(err.getError()));
					}}; break;			
			default: break;
			} response.flushBuffer();
		}  catch(NumberFormatException e){
			err = new ErrorObject(400,"DEP_02","department_id");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}  catch(DAOException e){
			err = new ErrorObject(400,"DEP_04","");
			response.getWriter().append(gson.toJson(err.getError()));
			response.flushBuffer();
		}   catch (Exception e){
			err = new ErrorObject(400,"DEP_03","");
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
