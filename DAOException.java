package com.turing.exception;

public class DAOException extends Exception{
	private String field;
	
	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public DAOException(String s){
		super(s);
	}
	
	public DAOException(String s, String field){
		super(s);
		this.field = field;
	}

}
