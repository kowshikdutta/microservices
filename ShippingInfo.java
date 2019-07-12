package com.turing.shipping;

public class ShippingInfo {
	private int shipping_id;
	private String shipping_type;
	private String shipping_cost;
	private int shipping_region_id;
	
	public int getShipping_id() {
		return shipping_id;
	}

	public void setShipping_id(int shipping_id) {
		this.shipping_id = shipping_id;
	}

	public String getShipping_type() {
		return shipping_type;
	}

	public void setShipping_type(String shipping_type) {
		this.shipping_type = shipping_type;
	}

	public String getShipping_cost() {
		return shipping_cost;
	}

	public void setShipping_cost(String shipping_cost) {
		this.shipping_cost = shipping_cost;
	}

	public int getShipping_region_id() {
		return shipping_region_id;
	}

	public void setShipping_region_id(int shipping_region_id) {
		this.shipping_region_id = shipping_region_id;
	}
	
	public ShippingInfo(int shipping_id, String shipping_type,
			String shipping_cost, int shipping_region_id) {
		this.shipping_id = shipping_id;
		this.shipping_type = shipping_type;
		this.shipping_cost = shipping_cost;
		this.shipping_region_id = shipping_region_id;
	}

}
