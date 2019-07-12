package com.turing.order;

public class OrderItems {
	private int product_id;
	private String attributes;
	private String product_name;
	private int quantity;
	private String unit_cost;
	private String subtotal;
	
	public OrderItems(int product_id, String attributes,
			String product_name, int quantity, String unit_cost,
			String subtotal) {
		this.product_id = product_id;
		this.attributes = attributes;
		this.product_name = product_name;
		this.quantity = quantity;
		this.unit_cost = unit_cost;
		this.subtotal = subtotal;
	}


}
