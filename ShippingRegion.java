package com.turing.shipping;

public class ShippingRegion {
	private int shipping_region_id;
	private String shipping_region;
	
	public int getShipping_region_id() {
		return shipping_region_id;
	}

	public void setShipping_region_id(int shipping_region_id) {
		this.shipping_region_id = shipping_region_id;
	}

	public String getShipping_region() {
		return shipping_region;
	}

	public void setShipping_region(String shipping_region) {
		this.shipping_region = shipping_region;
	}

	
	
	public ShippingRegion(int shipping_region_id, String shipping_region) {
		this.shipping_region_id = shipping_region_id;
		this.shipping_region = shipping_region;
	}

	
	

}
