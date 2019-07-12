package com.turing.attribute;

public class AttributeValues {
	
	private int attribute_value_id;
	private String attribute_value;
	
	 public AttributeValues(int attribute_value_id, String attribute_value) {
			this.attribute_value_id = attribute_value_id;
			this.attribute_value = attribute_value;
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
