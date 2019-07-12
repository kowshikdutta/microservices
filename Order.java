package com.turing.order;

import java.util.List;

public class Order {
	private int order_id;
	private String total_amount;
	private String created_on;
	private String shipped_on;
	private String status;
	private String name;
	private List<OrderItems> items;
	
	public Order(int order_id, String total_amount, String created_on,
			String shipped_on, String status, String name,
			List<OrderItems> items) {
		this.order_id = order_id;
		this.total_amount = total_amount;
		this.created_on = created_on;
		this.shipped_on = shipped_on;
		this.status = status;
		this.name = name;
		this.items = items;
	}
	

}
