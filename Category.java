package com.turing.category;

public class Category {
	
	private int category_id;
	private String name;
	private String description;
	private int department_id;
	
	public Category(int category_id, String name, String description,
			int department_id) {
		this.category_id = category_id;
		this.name = name;
		this.description = description;
		this.department_id = department_id;
	}

	
	public int getCategory_id() {
		return category_id;
	}
	public void setCategory_id(int category_id) {
		this.category_id = category_id;
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
	public int getDepartment_id() {
		return department_id;
	}
	public void setDepartment_id(int department_id) {
		this.department_id = department_id;
	}
	
	public CategoryInProduct getCategoryInProduct(){
		return new CategoryInProduct(category_id,department_id,name);
	}
	
	
	

}

class CategoryInProduct{
	private int category_id;
	private int department_id;
	private String name;
	
	public CategoryInProduct(int category_id, int department_id, String name) {
		this.category_id = category_id;
		this.department_id = department_id;
		this.name = name;
	}

	
	
}
