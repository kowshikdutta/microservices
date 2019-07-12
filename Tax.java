package com.turing.tax;

public class Tax {
	private int tax_id;
	private String tax_type;
	private String tax_percentage;
	
	public int getTax_id() {
		return tax_id;
	}

	public void setTax_id(int tax_id) {
		this.tax_id = tax_id;
	}

	public String getTax_type() {
		return tax_type;
	}

	public void setTax_type(String tax_type) {
		this.tax_type = tax_type;
	}

	public String getTax_percentage() {
		return tax_percentage;
	}

	public void setTax_percentage(String tax_percentage) {
		this.tax_percentage = tax_percentage;
	}

	
	
	public Tax(int tax_id, String tax_type, String tax_percentage) {
		this.tax_id = tax_id;
		this.tax_type = tax_type;
		this.tax_percentage = tax_percentage;
	}


}
