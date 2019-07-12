package com.turing.order;

public class OrderShortDetails {
	private int order_id;
	public int getOrder_id() {
		return order_id;
	}

	public void setOrder_id(int order_id) {
		this.order_id = order_id;
	}

	public String getTotal_amount() {
		return total_amount;
	}

	public void setTotal_amount(String total_amount) {
		this.total_amount = total_amount;
	}

	public String getCreated_on() {
		return created_on;
	}

	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}

	public String getShipped_on() {
		return shipped_on;
	}

	public void setShipped_on(String shipped_on) {
		this.shipped_on = shipped_on;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	private String total_amount;
	private String created_on;
	private String shipped_on;
	private String status;
	private String name;
	
	public OrderShortDetails(int order_id, String total_amount,
			String created_on, String shipped_on, String status, String name) {
		this.order_id = order_id;
		this.total_amount = total_amount;
		this.created_on = created_on;
		this.shipped_on = shipped_on;
		this.status = status;
		this.name = name;
	}
	
}
