package com.turing.order;

public class OrderInCustomer {
	private int order_id;
	private String total_amount;
	private String created_on;
	private String shipped_on;
	private String name;
	
	public OrderInCustomer(int order_id, String total_amount,
			String created_on, String shipped_on, String name) {
		this.order_id = order_id;
		this.total_amount = total_amount;
		this.created_on = created_on;
		this.shipped_on = shipped_on;
		this.name = name;
	}
	

}
