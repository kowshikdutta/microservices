package com.turing.exception;

import java.util.HashMap;
import java.util.Map;

public class ErrorObject {
	private int status;
	private String code;
	private String message;
	private String field;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	
	
/*	public ErrorObject(int status, String code, String message, String field){
		super();
		this.status = status;
		this.code = code;
		this.message = message;
		this.field = field;
	}
*/
	
	public Map<String, ErrorObject> getError(){
		Map<String, ErrorObject> e = new HashMap<String,ErrorObject>();
		e.put("error",this);
		return e;
	}
	
	public ErrorObject(int status, String code, String field){
		super();
		Map<String, String> errortable = new HashMap<String, String>();
		this.status = status;
		this.code = code;
		this.field = field;
		fillErrorTable(errortable);
		message = errortable.get(code);
	}

	private void fillErrorTable(Map<String, String> errortable) {
		// TODO Auto-generated method stub
		errortable.put("AUT_01","Authorization code is empty");
		errortable.put("AUT_02","Access Unauthorized");
		errortable.put("AUT_03","Please enter a valid user token");
		errortable.put("AUT_04","User token expired. Please re-login");
		errortable.put("AUT_05","Missing or malformed user token in request header");
		errortable.put("USR_01","Email or password is invalid");
		errortable.put("USR_02","The email is invalid.");
		errortable.put("USR_03","The email already exists.");
		errortable.put("USR_04","The email doesn't exist.");
		errortable.put("USR_06","The Shipping Region ID is not number");
		errortable.put("ATT_01","Attribute not found.");
		errortable.put("ATT_02","Internal server error in processing Attribute.");
		errortable.put("ATT_03","Internal error in processing Attribute.");
		errortable.put("CAT_01","Don't exist category with this ID.");
		errortable.put("CAT_02","Internal server error in processing Category.");
		errortable.put("CAT_03","Internal error in processing Category.");
		errortable.put("DEP_01","The ID is not a number.");
		errortable.put("DEP_02","Don't exist department with this ID.");
		errortable.put("DEP_03","Internal server error in processing department.");
		errortable.put("DEP_04","Internal error in processing department.");
		errortable.put("CUS_01","Don't exist customer with this ID.");
		errortable.put("CUS_02","Internal server error in processing customer.");
		errortable.put("CUS_03","Internal error in processing customer.");
		errortable.put("CUS_04","Customer already exist with this email.");
		errortable.put("ORD_01","Order not found.");
		errortable.put("ORD_02","Internal server error in processing order.");
		errortable.put("ORD_03","Internal error in processing order.");
		errortable.put("PRD_01","Product not found.");
		errortable.put("PRD_02","Internal server error in processing product.");
		errortable.put("PRD_03","Internal error in processing product.");
		errortable.put("CRT_01","Cart not found.");
		errortable.put("CRT_02","Internal server error in processing shopping cart.");
		errortable.put("CRT_03","Internal error in processing shopping cart.");
		errortable.put("CRT_04","Unable to add item to Shopping Cart.");
		errortable.put("CRT_05","Item not found.");
		errortable.put("CRT_06","Unable to generate cart id.");
		errortable.put("SHP_01","Shipping not found.");
		errortable.put("SHP_02","Internal server error in processing shipping.");
		errortable.put("SHP_03","Internal error in processing shipping.");
		errortable.put("TAX_01","Tax not found.");
		errortable.put("TAX_02","Internal server error in processing tax.");
		errortable.put("TAX_03","Internal error in processing tax.");
		errortable.put("URL_01","Bad URL.");	
		errortable.put("STR_01","Issue with Stripe Payment");
	}
}
