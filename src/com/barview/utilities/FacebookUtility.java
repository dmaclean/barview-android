package com.barview.utilities;

import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.barview.constants.BarviewConstants;
import com.facebook.android.Facebook;

/**
 * Convenience class for initializing the Facebook object and getting profile attribute.
 * 
 * @author dmaclean
 *
 */
public class FacebookUtility {
	
	public static final String FB_ID 			= "id";
	public static final String FB_ACCESS_TOKEN	= "access_token";
	public static final String FB_EXPIRES		= "access_expires";
	public static final String FB_NAME			= "name";
	public static final String FB_FIRST_NAME	= "first_name";
	public static final String FB_LAST_NAME		= "last_name";
	public static final String FB_BIRTHDAY		= "birthday";
	public static final String FB_EMAIL			= "email";
	public static final String FB_GENDER		= "gender";
	public static final String FB_LOCATION		= "location";
	
	/**
	 * Singleton instance of the Facebook object.
	 */
	private static Facebook facebook;
	
	private static String fbUserId = null;
	
	private static JSONObject json;
	
	/**
	 * Singleton retrieval of Facebook object.
	 * 
	 * @return
	 */
	public static Facebook getFacebook() {
		if(facebook == null)
			facebook = new Facebook(getFacebookIdForRunMode());
		
		return facebook;
	}
	
	public static JSONObject getJSON() {
		return json;
	}
	
	/**
	 * Determines whether the user is logged into facebook.
	 * 
	 * @return		True, if the user is logged in.  False, otherwise.
	 */
	public static boolean isLoggedIn() {
		return (facebook != null && facebook.isSessionValid());
	}
	
	public static String getRESTUserId() {
		if(isLoggedIn()) {
			return "fb" + getAttribute(FB_ID);
		}
		
		return null;
	}
	
	public static String getUserId() {
		//{"id":"668512494","name":"Dan MacLean","first_name":"Dan","last_name":"MacLean","link":"http:\\/\\/www.facebook.com\\/daniel.maclean","username":"daniel.maclean","gender":"male","timezone":-4,"locale":"en_US","verified":true,"updated_time":"2011-05-18T20:13:23+0000"}
		if(facebook != null && facebook.isSessionValid()) {
			if(json == null) {
				try {
					String info = facebook.request("me");
					json = new JSONObject(info);
					fbUserId = json.getString("id");
				} catch (MalformedURLException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
			
			return fbUserId;
		}
		
		return null;
	}
	
	/**
	 * Retrieve an attribute of the logged in user's account
	 * 
	 * @param attribute		The attribute to fetch.
	 * @return				A string representation of the attribute.
	 */
	public static String getAttribute(String attribute) {
		
		try {
			// We're logged in, try to get the requested attribute.
			if(facebook != null && facebook.isSessionValid()) {
				
				// We haven't fetched the attributes from facebook yet.  Do that now.
				if(json == null) {
					String info = facebook.request("me");
					json = new JSONObject(info);
				}
				
				// Return to the caller the requested attribute.
				return json.getString(attribute);
			}
			// We're either not logged in or something went wrong initializing the Facebook object.  Either way
			// we can't get to the user attributes so return null.
			else
				return null;
		} catch (Exception e) {
			Log.e(FacebookUtility.class.getName(), e.getMessage());
			e.printStackTrace();
		}
		
		// Some exception occurred if we got here.
		return null;
	}
	
	public static String getFacebookIdForRunMode() {
		if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEV_MODE))
			return BarviewConstants.FACEBOOK_APP_ID_DEV;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.DEMO_MODE))
			return BarviewConstants.FACEBOOK_APP_ID_DEMO;
		else if(BarviewConstants.RUN_MODE.equals(BarviewConstants.TEST_MODE))
			return BarviewConstants.FACEBOOK_APP_ID_TEST;
		else
			return BarviewConstants.FACEBOOK_APP_ID_PROD;
	}
}
