package com.turing.review;

public class ReviewOutput {
	
	private String name;
	private String review;
	private int rating;
	private String created_on;
	
	public ReviewOutput(String name, String review, int rating,
			String created_on) {
		this.name = name;
		this.review = review;
		this.rating = rating;
		this.created_on = created_on;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public String getCreated_on() {
		return created_on;
	}
	public void setCreated_on(String created_on) {
		this.created_on = created_on;
	}
	

}
