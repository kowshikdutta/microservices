package com.turing.security;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.turing.exception.ErrorObject;

/**
 * Servlet Filter implementation class SecurityManager
 */
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST, 
		DispatcherType.FORWARD, 
		DispatcherType.INCLUDE, 
		DispatcherType.ERROR
}
, urlPatterns = { "/securitymanager" })
public class SecurityManager implements Filter {
	private String errorcode;
	private Gson gson = new Gson();

	/**
	 * Default constructor. 
	 */
	public SecurityManager() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
        try{
		String[] uri = ((HttpServletRequest) request).getRequestURI().split("/");
		String method = ((HttpServletRequest) request).getMethod();
		String token = (String) ((HttpServletRequest) request).getSession().getAttribute("token");
		Date expirytime = (Date) ((HttpServletRequest) request).getSession().getAttribute("expiry");

		if(uri[2].matches("customers") && method.matches("POST")){
			chain.doFilter(request, response);
			return;
		} else {
			String[] requesttoken = ((HttpServletRequest) request).getHeader("USER-KEY").split(" ");
			if(requesttoken.equals(null) || requesttoken.length < 2){
				sendError(((HttpServletResponse) response), "AUT_03");
				return;
			}
			if(checkAuthentication(token,expirytime,requesttoken[1])){
				chain.doFilter(request, response);
			} else {
				sendError(((HttpServletResponse) response), errorcode);
				return;
			}
		}
	}catch(Exception e){
		sendError(((HttpServletResponse) response), "AUT_05");
		response.flushBuffer();
		return;
	}
}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}


public void sendError(HttpServletResponse response, String errorcode) throws IOException {
 ErrorObject errorResponse = new ErrorObject(400,errorcode,"url");
 response.setHeader("Content-Type","application/json");
 response.setStatus(200);
 response.getWriter().append(gson.toJson(errorResponse));
 response.flushBuffer();
}

public Boolean checkAuthentication(String token, Date expirytime, String requesttoken){
	Date currenttime = Calendar.getInstance().getTime();
	if(!token.matches(requesttoken)){
		this.errorcode = "AUT_03";
		return false;
	}
	
	if(expirytime.getTime() < currenttime.getTime()){
		this.errorcode = "AUT_04";
		return false;
	}
	return true;
}
}
