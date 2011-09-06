package com.barview.mobile;

public class BarviewMobileUtility {
	private static BarviewMobileUser user;
	
	public static BarviewMobileUser getUser() {
		if(user == null)
			user = new BarviewMobileUser();
		
		return user;
	}
	
	public static boolean isLoggedIn() {
		return user != null && user.getToken() != null;
	}
}
