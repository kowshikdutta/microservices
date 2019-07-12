package com.turing.product;

import com.google.gson.annotations.Expose;

public class Product {

	private int product_id;
	private String name;
	private String description;
	private  double price;
	private double discounted_price;
	private String image;
	private String image_2;
	private String thumbnail;
	private int display;
	
	public Product(int product_id, String name, String description,
			double price, double discounted_price, String thumbnail) {
		this.product_id = product_id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.discounted_price = discounted_price;
		this.thumbnail = thumbnail;
	}
	
	public Product(int product_id, String name, String description, double price, double discounted_price, String image, String image_2,
			 String thumbnail, int display) {
		this.product_id = product_id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.discounted_price = discounted_price;
		this.thumbnail = thumbnail;
		this.image = image;
		this.image_2 = image_2;
		this.display = display;
	}
	
	public int getProduct_id() {
		return product_id;
	}


	public void setProduct_id(int product_id) {
		this.product_id = product_id;
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


	public double getPrice() {
		return price;
	}


	public void setPrice(double price) {
		this.price = price;
	}


	public double getDiscounted_price() {
		return discounted_price;
	}


	public void setDiscounted_price(double discounted_price) {
		this.discounted_price = discounted_price;
	}


	public String getThumbnail() {
		return thumbnail;
	}


	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}
	
	public ProductSubset getProductSubset(){
		return new ProductSubset(product_id,name,description,price,discounted_price,thumbnail);
	}
}

class ProductSubset{
	private int product_id;
	private String name;
	private String description;
	private  double price;
	private double discounted_price;
	private String thumbnail;
	
	public ProductSubset(int product_id, String name, String description,
			double price, double discounted_price, String thumbnail) {
		this.product_id = product_id;
		this.name = name;
		this.description = description;
		this.price = price;
		this.discounted_price = discounted_price;
		this.thumbnail = thumbnail;
	}
	
	
	
}
