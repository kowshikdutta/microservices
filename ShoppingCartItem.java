package com.turing.shoppingcart;

public class ShoppingCartItem {	
	private int item_id;
	private String cart_id;
	private String name;
	private String attributes;
	private int product_id;
	private String image;
	private String price;
	private String discounted_price;
	private int quantity;
	private String subtotal;
	
	public int getItem_id() {
		return item_id;
	}
	public void setItem_id(int item_id) {
		this.item_id = item_id;
	}
	public String getCart_id() {
		return cart_id;
	}
	public void setCart_id(String cart_id) {
		this.cart_id = cart_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAttributes() {
		return attributes;
	}
	public void setAttributes(String attributes) {
		this.attributes = attributes;
	}
	public int getProduct_id() {
		return product_id;
	}
	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getDiscounted_price() {
		return discounted_price;
	}
	public void setDiscounted_price(String discounted_price) {
		this.discounted_price = discounted_price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getSubtotal() {
		return subtotal;
	}
	public void setSubtotal(String subtotal) {
		this.subtotal = subtotal;
	}
	
	public ShoppingCartItem(int item_id, String cart_id, String name,
			String attributes, int product_id, String image, String price,
			String discounted_price, int quantity, String subtotal) {
		this.item_id = item_id;
		this.cart_id = cart_id;
		this.name = name;
		this.attributes = attributes;
		this.product_id = product_id;
		this.image = image;
		this.price = price;
		this.discounted_price = discounted_price;
		this.quantity = quantity;
		this.subtotal = subtotal;
	}
	
}
