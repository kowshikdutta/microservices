package com.turing.attribute;

public class AttributeInProduct {
	
	private String attribute_name;
	private int attribute_value_id;
	private String attribute_value;
	
	public AttributeInProduct(String attribute_name, int attribute_value_id,
			String attribute_value) {
		this.attribute_name = attribute_name;
		this.attribute_value_id = attribute_value_id;
		this.attribute_value = attribute_value;
	}

	public String getAttribute_name() {
		return attribute_name;
	}

	public void setAttribute_name(String attribute_name) {
		this.attribute_name = attribute_name;
	}

	public int getAttribute_value_id() {
		return attribute_value_id;
	}

	public void setAttribute_value_id(int attribute_value_id) {
		this.attribute_value_id = attribute_value_id;
	}

	public String getAttribute_value() {
		return attribute_value;
	}

	public void setAttribute_value(String attribute_value) {
		this.attribute_value = attribute_value;
	}

	

}
