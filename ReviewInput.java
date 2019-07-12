package com.turing.review;

public class ReviewInput {
	
	private int customer_id;
	private int product_id;
	private String review;
	private int rating;
	
	public ReviewInput(int customer_id, int product_id, String review,
			int rating) {
		this.customer_id = customer_id;
		this.product_id = product_id;
		this.review = review;
		this.rating = rating;
	}
	
	public ReviewInput() {
		// TODO Auto-generated constructor stub
		super();
	}

	public int getCustomer_id() {
		return customer_id;
	}

	public void setCustomer_id(int customer_id) {
		this.customer_id = customer_id;
	}

	public int getProduct_id() {
		return product_id;
	}

	public void setProduct_id(int product_id) {
		this.product_id = product_id;
	}

	public String getReview() {
		return review;
	}

	public void setReview(String review) {
		this.review = review;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	

	
	

}
