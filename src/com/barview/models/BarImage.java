package com.barview.models;

public class BarImage {
	private String barId;
	private String barImage;
	private byte[] barImageBytes;
	
	public BarImage() {}
	
	public BarImage(String barId, String barImage) {
		this.barId = barId;
		this.barImage = barImage;
	}
	
	public String getBarId() {
		return barId;
	}
	public void setBarId(String barId) {
		this.barId = barId;
	}
	public String getBarImage() {
		return barImage;
	}
	public void setBarImage(String barImage) {
		this.barImage = barImage;
	}

	public byte[] getBarImageBytes() {
		return barImageBytes;
	}

	public void setBarImageBytes(byte[] barImageBytes) {
		this.barImageBytes = barImageBytes;
	}
	
	
}
