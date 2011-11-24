package com.barview.models;

public class Deal {
	private String barName;
	private String detail;
	
	public Deal() {}
	
	public Deal(String barName, String detail) {
		this.barName = barName;
		this.detail = detail;
	}
	
	public String getBarName() {
		return barName;
	}
	public void setBarName(String barName) {
		this.barName = barName;
	}
	public String getDetail() {
		return detail;
	}
	public void setDetail(String detail) {
		this.detail = detail;
	}
}
