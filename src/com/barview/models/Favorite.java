package com.barview.models;

public class Favorite {
	String barId;
	String barName;
	String address;
	
	public Favorite() {}
	
	public Favorite(String barId, String barName, String address) {
		this.barId = barId;
		this.barName = barName;
		this.address = address;
	}
	
	public String getBarId() {
		return barId;
	}
	public void setBarId(String barId) {
		this.barId = barId;
	}

	public String getBarName() {
		return barName;
	}

	public void setBarName(String barName) {
		this.barName = barName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
