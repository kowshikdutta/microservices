package com.turing.attribute;

public class Attribute {
	private int attribute_id;
	private String name;
	
	public Attribute(int attribute_id, String name) {
		this.attribute_id = attribute_id;
		this.name = name;
	}
	
	
	public int getAttribute_id() {
		return attribute_id;
	}

	public void setAttribute_id(int attribute_id) {
		this.attribute_id = attribute_id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	
	
	
	

}
