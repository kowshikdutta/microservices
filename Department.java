package com.turing.department;

public class Department {
	int departmentid;
	String name;
	String description;
	
	public Department(int departmentid, String name, String description) {
		this.departmentid = departmentid;
		this.name = name;
		this.description = description;
	}
	
	public int getDepartmentid() {
		return departmentid;
	}

	public void setDepartmentid(int departmentid) {
		this.departmentid = departmentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	
	
	
	

}
