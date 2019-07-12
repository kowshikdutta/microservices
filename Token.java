package com.turing.security;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

public class Token {
	
	 private int customerid;
	 private String token;
	 private Date expirytime;

	 public int getCustomerid() {
		return customerid;
	}

	public void setCustomerid(int customerid) {
		this.customerid = customerid;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getExpirytime() {
		return expirytime;
	}

	public void setExpirytime(Date expirytime) {
		this.expirytime = expirytime;
	}

 
	 public Token(int customerid) {
		this.customerid = customerid;
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = Calendar.getInstance().getTime();
		this.token = DigestUtils.sha256Hex(customerid + dateFormat.format(date));
		this.expirytime = new Date(date.getTime() + 10 * 60000);
	}
	
	
}
